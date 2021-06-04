package android.technopolis.films.repository

import android.technopolis.films.api.model.media.CalendarItem
import android.technopolis.films.api.model.media.CommonMediaItem
import android.technopolis.films.api.model.media.HistoryItem
import android.technopolis.films.api.model.media.MediaType
import android.technopolis.films.api.model.users.stats.UserStats
import android.technopolis.films.api.model.media.Media
import android.technopolis.films.api.model.users.settings.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface Repository {
    val moviesRecommendationsLoading: StateFlow<Boolean>
    val moviesRecommendations: Flow<MutableList<CommonMediaItem>>
    val showsRecommendationsLoading: StateFlow<Boolean>
    val showsRecommendations: Flow<MutableList<CommonMediaItem>>
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
    val userSettingsLoading: StateFlow<Boolean>
    val userSettings: Flow<UserSettings>
    fun getUserSettings()
}
