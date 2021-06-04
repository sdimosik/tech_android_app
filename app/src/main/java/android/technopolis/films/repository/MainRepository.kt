package android.technopolis.films.repository

import android.technopolis.films.api.Trakt
import android.technopolis.films.api.TraktClientGenerator
import android.technopolis.films.api.model.media.CalendarItem
import android.technopolis.films.api.model.media.CommonMediaItem
import android.technopolis.films.api.model.media.HistoryItem
import android.technopolis.films.api.model.media.MediaType
import android.technopolis.films.api.model.media.SortType
import android.technopolis.films.api.model.users.stats.UserStats
import android.technopolis.films.api.model.media.Media
import android.technopolis.films.api.model.users.settings.UserSettings
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainRepository : Repository {
    private val client: Trakt = TraktClientGenerator.getClient()

    private var _moviesRecommendationsLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val moviesRecommendationsLoading: StateFlow<Boolean> =
        _moviesRecommendationsLoading.asStateFlow()

    private val _moviesRecommendations =
        MutableLiveData<MutableList<CommonMediaItem>>(mutableListOf())
    override val moviesRecommendations: Flow<MutableList<CommonMediaItem>> =
        _moviesRecommendations.asFlow()

    /*____________________________________________________________________________________________*/

    private var _showsRecommendationsLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val showsRecommendationsLoading: StateFlow<Boolean> =
        _showsRecommendationsLoading.asStateFlow()

    private val _showsRecommendations =
        MutableLiveData<MutableList<CommonMediaItem>>(mutableListOf())
    override val showsRecommendations: Flow<MutableList<CommonMediaItem>> =
        _showsRecommendations.asFlow()

    override fun getRecommendations(type: MediaType, ignoreCollected: Boolean) {
        when (type) {
            MediaType.movies -> getRecommendations(type,
                ignoreCollected,
                _moviesRecommendations,
                _moviesRecommendationsLoading
            )

            MediaType.shows -> getRecommendations(type,
                ignoreCollected,
                _showsRecommendations,
                _showsRecommendationsLoading
            )
        }
    }

    private fun getRecommendations(
        type: MediaType,
        ignoreCollected: Boolean,
        list: MutableLiveData<MutableList<CommonMediaItem>>,
        loadingState: MutableStateFlow<Boolean>,
    ) {
        MainScope().launch(Dispatchers.IO) {
            loadingState.value = true
            val recommendations = client.getRecommendations(
                type,
                RECOMMENDATIONS_LIMIT,
                ignoreCollected,
            )

            list.value!!.apply {
                addAll(size, recommendations)
            }

            loadingState.value = false
        }
    }
    /*============================================================================================*/

    private var _moviesWatchListLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val moviesWatchListLoading: StateFlow<Boolean> = _moviesWatchListLoading.asStateFlow()

    private val _moviesWatchList = MutableLiveData<MutableList<Media>>(mutableListOf())
    override val moviesWatchList: Flow<MutableList<Media>> = _moviesWatchList.asFlow()

    private var _currentMoviesWatchListPage = MutableStateFlow(0)
    private var _isMoviesWatchListEnded = MutableStateFlow(false)

    /*____________________________________________________________________________________________*/

    private var _showsWatchListLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val showsWatchListLoading: StateFlow<Boolean> = _showsWatchListLoading.asStateFlow()

    private val _showsWatchList = MutableLiveData<MutableList<Media>>(mutableListOf())
    override val showsWatchList: Flow<MutableList<Media>> = _showsWatchList.asFlow()
    private var _currentShowsWatchListPage = MutableStateFlow(0)
    private var _isShowsWatchListEnded = MutableStateFlow(false)

    /*____________________________________________________________________________________________*/

    override fun getWatchList(type: MediaType) {
        when (type) {
            MediaType.movies -> getWatchList(type,
                "me",
                _currentMoviesWatchListPage,
                _moviesWatchList,
                _moviesWatchListLoading,
                _isMoviesWatchListEnded)

            MediaType.shows -> getWatchList(type,
                "me",
                _currentShowsWatchListPage,
                _showsWatchList,
                _showsWatchListLoading,
                _isShowsWatchListEnded)
        }
    }

    private fun getWatchList(
        type: MediaType,
        id: String,
        currentPage: MutableStateFlow<Int>,
        list: MutableLiveData<MutableList<Media>>,
        loadingState: MutableStateFlow<Boolean>,
        isEnded: MutableStateFlow<Boolean>,
    ) {
        if (isEnded.value) {
            return
        }
        currentPage.value += 1
        MainScope().launch(Dispatchers.IO) {
            loadingState.value = true
            val watchList = client.getWatchList(
                id,
                type,
                SortType.added,
                currentPage.value,
                LIMIT_ON_PAGE
            )

            if (watchList.size < LIMIT_ON_PAGE) {
                isEnded.value = true
            }

            list.value!!.apply {
                addAll(size, watchList)
            }
            loadingState.value = false
        }
    }

    override fun updateWatchList(type: MediaType) {
        when (type) {
            MediaType.movies -> {
                _currentMoviesWatchListPage.value = 0
                _moviesWatchList.value = mutableListOf()
                getWatchList(type)
            }
            MediaType.shows -> {
                _currentShowsWatchListPage.value = 0
                _showsWatchList.value = mutableListOf()
                getWatchList(type)
            }
        }
    }
    /*============================================================================================*/

    private var _moviesHistoryLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val moviesHistoryLoading: StateFlow<Boolean> = _moviesHistoryLoading.asStateFlow()

    private val _moviesHistory = MutableLiveData<MutableList<HistoryItem>>(mutableListOf())
    override val moviesHistory: Flow<MutableList<HistoryItem>> = _moviesHistory.asFlow()
    private var _currentMoviesHistoryPage = MutableStateFlow(0)
    private var _isMoviesHistoryEnded = MutableStateFlow(false)

    /*____________________________________________________________________________________________*/

    private var _showsHistoryLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val showsHistoryLoading: StateFlow<Boolean> = _showsHistoryLoading.asStateFlow()

    private val _showsHistory = MutableLiveData<MutableList<HistoryItem>>(mutableListOf())
    override val showsHistory: Flow<MutableList<HistoryItem>> = _showsHistory.asFlow()
    private var _currentShowsHistoryPage = MutableStateFlow(0)
    private var _isShowsHistoryEnded = MutableStateFlow(false)

    override fun getWatchedHistory(type: MediaType) {
        when (type) {
            MediaType.movies -> getWatchedHistory(type,
                "me",
                _currentMoviesHistoryPage,
                _moviesHistory,
                _moviesHistoryLoading,
                _isMoviesHistoryEnded)

            MediaType.shows -> getWatchedHistory(type,
                "me",
                _currentShowsHistoryPage,
                _showsHistory,
                _showsHistoryLoading,
                _isShowsHistoryEnded)
        }
    }

    private fun getWatchedHistory(
        type: MediaType,
        id: String,
        currentPage: MutableStateFlow<Int>,
        list: MutableLiveData<MutableList<HistoryItem>>,
        loadingState: MutableStateFlow<Boolean>,
        isEnded: MutableStateFlow<Boolean>,
    ) {
        if (isEnded.value) {
            return
        }

        currentPage.value += 1
        MainScope().launch(Dispatchers.IO) {
            loadingState.value = true
            val watchHistory = client.getWatchedHistory(id,
                type,
                currentPage.value,
                LIMIT_ON_PAGE,
                null,
                null,
                null)

            if (watchHistory.size < LIMIT_ON_PAGE) {
                isEnded.value = true
            }

            list.value!!.apply {
                addAll(size, watchHistory)
            }
        }
        loadingState.value = false
    }

    /*============================================================================================*/

    private var _statsLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val statsLoading: StateFlow<Boolean> = _statsLoading.asStateFlow()

    private val _stats = MutableLiveData<UserStats?>()
    override val stats: Flow<UserStats?> = _stats.asFlow()

    override fun getStats(id: String) {
        MainScope().launch(Dispatchers.IO) {
            _statsLoading.value = true
            _stats.value = client.getStats(id)
            _statsLoading.value = false
        }
    }
    /*============================================================================================*/

    private var _moviesCalendarLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val moviesCalendarLoading: StateFlow<Boolean> = _moviesCalendarLoading.asStateFlow()

    private val _moviesCalendar = MutableLiveData<MutableList<CalendarItem>>(mutableListOf())
    override val moviesCalendar: Flow<MutableList<CalendarItem>> = _moviesCalendar.asFlow()

    /*____________________________________________________________________________________________*/

    private var _showsCalendarLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val showsCalendarLoading: StateFlow<Boolean> = _showsCalendarLoading.asStateFlow()

    private val _showsCalendar = MutableLiveData<MutableList<CalendarItem>>(mutableListOf())
    override val showsCalendar: Flow<MutableList<CalendarItem>> = _showsCalendar.asFlow()

    override fun getMyCalendar(
        type: MediaType,
        startDate: String,
        days: Int,
    ) {
        when (type) {
            MediaType.movies -> getMyCalendar(type,
                startDate,
                days,
                _moviesCalendar,
                _moviesCalendarLoading)

            MediaType.shows -> getMyCalendar(type,
                startDate,
                days,
                _showsCalendar,
                _showsCalendarLoading)
        }
    }

    private fun getMyCalendar(
        type: MediaType,
        startDate: String,
        days: Int,
        list: MutableLiveData<MutableList<CalendarItem>>,
        loadingState: MutableStateFlow<Boolean>,
    ) {
        MainScope().launch(Dispatchers.IO) {
            loadingState.value = true
            val calendar = client.getMyCalendar(type, startDate, days)
            list.value!!.apply {
                addAll(size, calendar)
            }
            loadingState.value = false
        }
    }

    /*============================================================================================*/

    private var _userSettingsLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val userSettingsLoading: StateFlow<Boolean> = _userSettingsLoading.asStateFlow()

    private val _userSettings = MutableLiveData<UserSettings>()
    override val userSettings: Flow<UserSettings> = _userSettings.asFlow()

    override fun getUserSettings() {
        MainScope().launch(Dispatchers.IO) {
            _userSettingsLoading.value = true
            _userSettings.value = client.getUserSettings()
            _userSettingsLoading.value = false
        }
    }

    /*============================================================================================*/

    companion object {
        private const val LIMIT_ON_PAGE = 15
        private const val RECOMMENDATIONS_LIMIT = 50
    }
}