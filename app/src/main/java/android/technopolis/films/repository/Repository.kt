package android.technopolis.films.repository

import android.technopolis.films.api.model.media.CalendarItem
import android.technopolis.films.api.model.media.HistoryItem
import android.technopolis.films.api.model.media.MediaType
import android.technopolis.films.api.model.media.RecommendationItem
import android.technopolis.films.api.model.stats.UserStats
import android.technopolis.films.api.model.media.Media
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow

interface Repository {
    val moviesRecommendationsLoading: MutableStateFlow<Boolean>
    val moviesRecommendations: MutableLiveData<MutableList<RecommendationItem>>
    val showsRecommendationsLoading: MutableStateFlow<Boolean>
    val showsRecommendations: MutableLiveData<MutableList<RecommendationItem>>
    fun getRecommendations(type: MediaType, ignoreCollected: Boolean)

    /*============================================================================================*/

    val moviesWatchList: MutableLiveData<MutableList<Media>>
    val moviesWatchListLoading: MutableStateFlow<Boolean>
    val showsWatchList: MutableLiveData<MutableList<Media>>
    val showsWatchListLoading: MutableStateFlow<Boolean>
    fun getWatchList(type: MediaType)

    /*============================================================================================*/

    val moviesHistory: MutableLiveData<MutableList<HistoryItem>>
    val moviesHistoryLoading: MutableStateFlow<Boolean>
    val showsHistory: MutableLiveData<MutableList<HistoryItem>>
    val showsHistoryLoading: MutableStateFlow<Boolean>
    fun getWatchedHistory(type: MediaType)

    /*============================================================================================*/

    val statsLoading: MutableStateFlow<Boolean>
    val stats: MutableLiveData<UserStats?>

    /**
     * @param id - user 'slug' of word 'me' if there is access_token in request headers
     */
    fun getStats(id: String)

    /*============================================================================================*/
    val moviesCalendarLoading: MutableStateFlow<Boolean>
    val moviesCalendar: MutableLiveData<MutableList<CalendarItem>>
    val showsCalendarLoading: MutableStateFlow<Boolean>
    val showsCalendar: MutableLiveData<MutableList<CalendarItem>>
    fun getMyCalendar(type: MediaType, startDate: String, days: Int)
}
