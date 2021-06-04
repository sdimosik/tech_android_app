package android.technopolis.films.repository

import android.technopolis.films.api.Trakt
import android.technopolis.films.api.TraktClientGenerator
import android.technopolis.films.api.model.media.CalendarItem
import android.technopolis.films.api.model.media.HistoryItem
import android.technopolis.films.api.model.media.MediaType
import android.technopolis.films.api.model.media.RecommendationItem
import android.technopolis.films.api.model.media.SortType
import android.technopolis.films.api.model.stats.UserStats
import android.technopolis.films.api.model.media.Media
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainRepository : Repository {
    private val client: Trakt = TraktClientGenerator.getClient()

    private var _moviesRecommendationsLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val moviesRecommendationsLoading: StateFlow<Boolean> =
        _moviesRecommendationsLoading.asStateFlow()

    private val _moviesRecommendations =
        MutableLiveData<MutableList<RecommendationItem>>(mutableListOf())
    override val moviesRecommendations: Flow<MutableList<RecommendationItem>> =
        _moviesRecommendations.asFlow()

    /*____________________________________________________________________________________________*/

    private var _showsRecommendationsLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val showsRecommendationsLoading: StateFlow<Boolean> =
        _showsRecommendationsLoading.asStateFlow()

    private val _showsRecommendations =
        MutableLiveData<MutableList<RecommendationItem>>(mutableListOf())
    override val showsRecommendations: Flow<MutableList<RecommendationItem>> =
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
        list: MutableLiveData<MutableList<RecommendationItem>>,
        loadingState: MutableStateFlow<Boolean>,
    ) {
        MainScope().launch(Dispatchers.IO) {
            loadingState.value = true
            val recommendations = client.getRecommendations(
                type,
                RECOMMENDATIONS_LIMIT,
                ignoreCollected,
            )

            if (recommendations.isSuccessful) {
                for (item in recommendations.body()!!) {
                    list.value!!.add(item)
                }
            } else {
                println(recommendations.errorBody())
            }
            loadingState.value = false
        }
    }
    /*============================================================================================*/

    private var _moviesWatchListLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val moviesWatchListLoading: StateFlow<Boolean> = _moviesWatchListLoading.asStateFlow()

    private val _moviesWatchList = MutableLiveData<MutableList<Media>>(mutableListOf())
    override val moviesWatchList: Flow<MutableList<Media>> = _moviesWatchList.asFlow()

    private var _currentMoviesWatchListPage = 0

    /*____________________________________________________________________________________________*/

    private var _showsWatchListLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val showsWatchListLoading: StateFlow<Boolean> = _showsWatchListLoading.asStateFlow()

    private val _showsWatchList = MutableLiveData<MutableList<Media>>(mutableListOf())
    override val showsWatchList: Flow<MutableList<Media>> = _showsWatchList.asFlow()
    private var _currentShowsWatchListPage = 0
    /*____________________________________________________________________________________________*/

    override fun getWatchList(type: MediaType) {
        when (type) {
            MediaType.movies -> getWatchList(type,
                "me",
                _currentMoviesWatchListPage,
                _moviesWatchList,
                _moviesWatchListLoading)

            MediaType.shows -> getWatchList(type,
                "me",
                _currentShowsWatchListPage,
                _showsWatchList,
                _showsWatchListLoading)
        }
    }

    private fun getWatchList(
        type: MediaType,
        id: String,
        currentPage: Int,
        list: MutableLiveData<MutableList<Media>>,
        loadingState: MutableStateFlow<Boolean>,
    ) {
        currentPage.inc()
        MainScope().launch(Dispatchers.IO) {
            loadingState.value = true
            val watchList = client.getWatchList(
                id,
                type,
                SortType.added,
                currentPage,
                LIMIT_ON_PAGE
            )

            if (watchList.isSuccessful) {
                for (item in watchList.body()!!) {
                    list.value!!.add(item)
                }
            } else {
                println(watchList.errorBody())
            }
            loadingState.value = false
        }
    }

    /*============================================================================================*/

    private var _moviesHistoryLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val moviesHistoryLoading: StateFlow<Boolean> = _moviesHistoryLoading.asStateFlow()

    private val _moviesHistory = MutableLiveData<MutableList<HistoryItem>>(mutableListOf())
    override val moviesHistory: Flow<MutableList<HistoryItem>> = _moviesHistory.asFlow()
    private var _currentMoviesHistoryPage = 0

    /*____________________________________________________________________________________________*/

    private var _showsHistoryLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val showsHistoryLoading: StateFlow<Boolean> = _showsHistoryLoading.asStateFlow()

    private val _showsHistory = MutableLiveData<MutableList<HistoryItem>>(mutableListOf())
    override val showsHistory: Flow<MutableList<HistoryItem>> = _showsHistory.asFlow()
    private var _currentShowsHistoryPage: Int = 0

    override fun getWatchedHistory(type: MediaType) {
        when (type) {
            MediaType.movies -> getWatchedHistory(type,
                "me",
                _currentMoviesHistoryPage,
                _moviesHistory,
                _moviesHistoryLoading)

            MediaType.shows -> getWatchedHistory(type,
                "me",
                _currentShowsHistoryPage,
                _showsHistory,
                _showsHistoryLoading)
        }
    }

    private fun getWatchedHistory(
        type: MediaType,
        id: String,
        currentPage: Int,
        list: MutableLiveData<MutableList<HistoryItem>>,
        loadingState: MutableStateFlow<Boolean>,
    ) {
        currentPage.inc()
        MainScope().launch(Dispatchers.IO) {
            loadingState.value = true
            val watchHistory =
                client.getWatchedHistory(id, type, currentPage, LIMIT_ON_PAGE, null, null, null)
            if (watchHistory.isSuccessful) {
                for (item in watchHistory.body()!!) {
                    list.value!!.add(item)
                }
            } else {
                println(watchHistory.errorBody())
            }
            loadingState.value = false
        }
    }
    /*============================================================================================*/

    private var _statsLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val statsLoading: StateFlow<Boolean> = _statsLoading.asStateFlow()

    private val _stats = MutableLiveData<UserStats?>()
    override val stats: Flow<UserStats?> = _stats.asFlow()

    override fun getStats(id: String) {
        MainScope().launch(Dispatchers.IO) {
            _statsLoading.value = true
            val stats = client.getStats(id)
            if (stats.isSuccessful) {
                _stats.value = stats.body()
            } else {
                println(stats.errorBody())
            }
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
            if (calendar.isSuccessful) {
                for (item in calendar.body()!!) {
                    list.value!!.add(item)
                }
            } else {
                println(calendar.errorBody())
            }
            loadingState.value = false
        }
    }

    /*============================================================================================*/

    companion object {
        private const val LIMIT_ON_PAGE = 15
        private const val RECOMMENDATIONS_LIMIT = 50
    }
}