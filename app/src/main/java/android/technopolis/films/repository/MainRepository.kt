package android.technopolis.films.repository

import android.technopolis.films.api.trakt.Trakt
import android.technopolis.films.api.trakt.TraktClientGenerator
import android.technopolis.films.api.trakt.model.media.CalendarItem
import android.technopolis.films.api.trakt.model.users.settings.UserSettings
import android.technopolis.films.api.trakt.model.users.stats.UserStats
import android.technopolis.films.api.trakt.model.media.CommonMediaItem
import android.technopolis.films.api.trakt.model.media.HistoryItem
import android.technopolis.films.api.trakt.model.media.Media
import android.technopolis.films.api.trakt.model.media.MediaType
import android.technopolis.films.api.trakt.model.media.SortType
import android.technopolis.films.db.UserSettingDatabase
import android.technopolis.films.model.SettingForUser
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.sync.withLock
import okhttp3.Dispatcher


class MainRepository(private val db: UserSettingDatabase) : Repository {

    /*____________________________________________________________________________________________*/

    private fun addProfileSetting(settingForUser: SettingForUser) =
        db.getUserSettingDAO().addUserSetting(settingForUser)

    fun getProfileSetting() = db.getUserSettingDAO().getUserSetting()

    private val client: Trakt = TraktClientGenerator.getClient()
    private val config = Config()

    override val moviesRecommendationsLoading: StateFlow<Boolean> =
        config.getConfig(MediaType.movies).recommendationsLoading.asStateFlow()
    override val moviesRecommendations: Flow<MutableList<CommonMediaItem>> =
        config.getConfig(MediaType.movies).recommendations.asFlow()

    /*____________________________________________________________________________________________*/

    override val showsRecommendationsLoading: StateFlow<Boolean> =
        config.getConfig(MediaType.shows).recommendationsLoading.asStateFlow()
    override val showsRecommendations: Flow<MutableList<CommonMediaItem>> =
        config.getConfig(MediaType.shows).recommendations.asFlow()

    override fun getRecommendations(type: MediaType, ignoreCollected: Boolean) {
        getRecommendations(type, ignoreCollected, config.getConfig(type))
    }

    private fun getRecommendations(
        type: MediaType,
        ignoreCollected: Boolean,
        config: ConfigImpl,
    ) {
        MainScope().launch() {
            config.recommendationsLoading.value = true
            val recommendations = client.getRecommendations(
                type,
                RECOMMENDATIONS_LIMIT,
                ignoreCollected,
            )

            config.recommendations.postValue(recommendations)

            config.recommendationsLoading.value = false
        }
    }

    /*============================================================================================*/

    override val moviesWatchListLoading: MutableStateFlow<Boolean> =
        config.getConfig(MediaType.movies).watchListLoading
    override val moviesWatchList: MutableLiveData<MutableList<Media>> =
        config.getConfig(MediaType.movies).watchList

    /*____________________________________________________________________________________________*/

    override val showsWatchListLoading: MutableStateFlow<Boolean> =
        config.getConfig(MediaType.shows).watchListLoading
    override val showsWatchList: MutableLiveData<MutableList<Media>> =
        config.getConfig(MediaType.shows).watchList

    /*____________________________________________________________________________________________*/

    override fun getWatchList(type: MediaType) {
        MainScope().launch {
            val c = config.getConfig(type)
            if (c.watchListLoadingMutex.isLocked || c.watchListLoading.value) {
                cancel()
            }

            c.watchListLoadingMutex.withLock {
                getWatchList(type, c, "me")
            }
        }

    }

    override fun updateWatchList(type: MediaType) {
        MainScope().launch {
            val c = config.getConfig(type)
            if (c.watchListLoadingMutex.isLocked) {
                c.watchListCancel.value = true
                cancel()
            }

            c.watchListLoadingMutex.withLock {
                c.isWatchListEnded.value = false
                c.currentWatchListPage.value = 0
                c.watchList.value = mutableListOf()
                getWatchList(type, c, "me")
                c.watchListCancel.value = false
            }
        }
    }

    private fun getWatchList(
        type: MediaType,
        config: ConfigImpl,
        id: String,
    ) {
        MainScope().launch {
            println("getWatchList(): try to get data")
            if (config.isWatchListEnded.value) {
                cancel()
            }
            setUpCancellation(config, this)
            config.currentWatchListPage.value += 1

            config.watchListLoading.value = true
            val watchList = client.getWatchList(
                id,
                type,
                SortType.added,
                config.currentWatchListPage.value,
                LIMIT_ON_PAGE
            )

            if (watchList.size < LIMIT_ON_PAGE) {
                config.isWatchListEnded.value = true
            }

            config.watchList.value!!.apply {
                addAll(size, watchList)
            }
            println("getWatchList(): data loaded")
            config.watchListLoading.value = false
        }
    }

    private fun setUpCancellation(config: ConfigImpl, coroutineScope: CoroutineScope) {
        //todo повесить на этот метод отмену загрузки по истечению задержки
        config.watchListCancel.asStateFlow().onEach {
            if (it) {
                coroutineScope.cancel()
            }
        }.launchIn(coroutineScope)
    }
    /*============================================================================================*/

    override val moviesHistoryLoading: StateFlow<Boolean> =
        config.getConfig(MediaType.movies).historyLoading.asStateFlow()
    override val moviesHistory: Flow<MutableList<HistoryItem>> =
        config.getConfig(MediaType.movies).history.asFlow()

    /*____________________________________________________________________________________________*/

    override val showsHistoryLoading: StateFlow<Boolean> =
        config.getConfig(MediaType.shows).historyLoading.asStateFlow()
    override val showsHistory: Flow<MutableList<HistoryItem>> =
        config.getConfig(MediaType.shows).history.asFlow()


    override fun getWatchedHistory(type: MediaType) {
        getWatchedHistory(type, "me", config.getConfig(type))
    }

    private fun getWatchedHistory(
        type: MediaType,
        id: String,
        config: ConfigImpl,
    ) {
        if (config.isHistoryEnded.value) {
            return
        }

        config.currentHistoryPage.value += 1
        MainScope().launch {
            config.historyLoading.value = true
            val watchHistory = client.getWatchedHistory(
                id,
                type,
                config.currentHistoryPage.value,
                LIMIT_ON_PAGE,
                null,
                null,
                null
            )

            if (watchHistory.size < LIMIT_ON_PAGE) {
                config.isHistoryEnded.value = true
            }

            config.history.value!!.apply {
                addAll(size, watchHistory)
            }
        }
        config.historyLoading.value = false
    }

    /*============================================================================================*/

    private var _statsLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val statsLoading: StateFlow<Boolean> = _statsLoading.asStateFlow()

    private val _stats = MutableLiveData<UserStats?>()
    override val stats: Flow<UserStats?> = _stats.asFlow()

    override fun getStats(id: String) {
        MainScope().launch {
            _statsLoading.value = true
            _stats.value = client.getStats(id)
            _statsLoading.value = false
        }
    }
    /*============================================================================================*/

    override val moviesCalendarLoading: StateFlow<Boolean> =
        config.getConfig(MediaType.movies).calendarLoading.asStateFlow()
    override val moviesCalendar: Flow<MutableList<CalendarItem>> =
        config.getConfig(MediaType.movies).calendar.asFlow()

    /*____________________________________________________________________________________________*/

    override val showsCalendarLoading: StateFlow<Boolean> =
        config.getConfig(MediaType.shows).calendarLoading.asStateFlow()
    override val showsCalendar: Flow<MutableList<CalendarItem>> =
        config.getConfig(MediaType.shows).calendar.asFlow()

    override fun getMyCalendar(
        type: MediaType,
        startDate: String,
        days: Int,
    ) {
        getMyCalendar(type, startDate, days, config.getConfig(type))
    }

    private fun getMyCalendar(
        type: MediaType,
        startDate: String,
        days: Int,
        config: ConfigImpl,
    ) {
        MainScope().launch {
            config.calendarLoading.value = true
            val calendar = client.getMyCalendar(type, startDate, days)
            config.calendar.value!!.apply {
                addAll(size, calendar)
            }
            config.calendarLoading.value = false
        }
    }

    /*============================================================================================*/

    private var _userSettingsLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val userSettingsLoading: StateFlow<Boolean> = _userSettingsLoading.asStateFlow()

    private val _userSettings = MutableLiveData<UserSettings>()
    override val userSettings: Flow<UserSettings> = _userSettings.asFlow()

    override fun getUserSettings() {
        GlobalScope.launch(Dispatchers.IO) {
            _userSettingsLoading.value = true
            val userSettings = client.getUserSettings()
            val settingForUser = SettingForUser(
                0,
                userSettings
            )
            addProfileSetting(settingForUser)
            _userSettings.postValue(userSettings)
            _userSettingsLoading.value = false
        }
    }

    /*============================================================================================*/

    companion object {
        private const val LIMIT_ON_PAGE = 15
        private const val RECOMMENDATIONS_LIMIT = 50
    }
}
