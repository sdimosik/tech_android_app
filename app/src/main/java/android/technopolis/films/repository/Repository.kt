package android.technopolis.films.repository

import android.technopolis.films.api.model.media.CalendarItem
import android.technopolis.films.api.model.media.HistoryItem
import android.technopolis.films.api.model.media.MediaType
import android.technopolis.films.api.model.media.RecommendationItem
import android.technopolis.films.api.model.stats.UserStats
import android.technopolis.films.api.model.media.Media
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface Repository {
    val moviesRecommendationsLoading: StateFlow<Boolean>
    val moviesRecommendations: Flow<MutableList<RecommendationItem>>
    val showsRecommendationsLoading: StateFlow<Boolean>
    val showsRecommendations: Flow<MutableList<RecommendationItem>>
    fun getRecommendations(type: MediaType, ignoreCollected: Boolean)

    /*============================================================================================*/

    val moviesWatchList: Flow<MutableList<Media>>
    val moviesWatchListLoading: StateFlow<Boolean>
    val showsWatchList: Flow<MutableList<Media>>
    val showsWatchListLoading: StateFlow<Boolean>
    fun getWatchList(type: MediaType)

    /*============================================================================================*/

    val moviesHistory: Flow<MutableList<HistoryItem>>
    val moviesHistoryLoading: StateFlow<Boolean>
    val showsHistory: Flow<MutableList<HistoryItem>>
    val showsHistoryLoading: StateFlow<Boolean>
    fun getWatchedHistory(type: MediaType)

    /*============================================================================================*/

    val statsLoading: StateFlow<Boolean>
    val stats: Flow<UserStats?>

    /**
     * @param id - user 'slug' of word 'me' if there is access_token in request headers
     */
    fun getStats(id: String)

    /*============================================================================================*/
    val moviesCalendarLoading: StateFlow<Boolean>
    val moviesCalendar: Flow<MutableList<CalendarItem>>
    val showsCalendarLoading: StateFlow<Boolean>
    val showsCalendar: Flow<MutableList<CalendarItem>>
    fun getMyCalendar(type: MediaType, startDate: String, days: Int)
}
