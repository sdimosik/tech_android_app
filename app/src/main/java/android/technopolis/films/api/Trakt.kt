package android.technopolis.films.api

import android.technopolis.films.api.model.media.CalendarItem
import android.technopolis.films.api.model.media.HistoryItem
import android.technopolis.films.api.model.media.MediaType
import android.technopolis.films.api.model.media.RecommendationItem
import android.technopolis.films.api.model.media.SortType
import android.technopolis.films.api.model.media.WatchListItem
import android.technopolis.films.api.model.users.settings.UserSettings
import android.technopolis.films.api.model.users.stats.UserStats
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Response

class Trakt(
    traktClient: MutableStateFlow<TraktClient?>,
) {
    private var client: MutableStateFlow<TraktClient?> = traktClient
        get() {
            updateToken()
            return field
        }

    private val timeToEnd: Long
        get() = ApiConfig.token!!.run {
            expiresIn + createdAt - System.currentTimeMillis() / 1000
        }

    private fun updateToken() {
        if (timeToEnd > 0) {
            return
        }
        TraktClientGenerator.updateToken()
    }

    suspend fun getRecommendations(
        type: MediaType,
        limit: Int,
        ignoreCollected: Boolean,
    ): Response<MutableList<RecommendationItem>> {
        return client.value!!.getRecommendations(type.name, limit, ignoreCollected)
    }

    suspend fun getWatchList(
        id: String,
        type: MediaType,
        sort: SortType,
        page: Int,
        limit: Int,
    ): Response<MutableList<WatchListItem>> {
        return client.value!!.getWatchList(id, type.name, sort.name, page, limit)
    }

    suspend fun getWatchedHistory(
        id: String,
        type: MediaType,
        page: Int,
        limit: Int,
        itemId: Int?,
        startAt: String?,
        endAt: String?,
    ): Response<MutableList<HistoryItem>> {
        return client.value!!.getWatchedHistory(id, type.name, page, limit, itemId, startAt, endAt)
    }

    suspend fun getStats(id: String): Response<UserStats> {
        return client.value!!.getStats(id)
    }

    suspend fun getMyCalendar(
        type: MediaType,
        startDate: String,
        days: Int,
    ): Response<MutableList<CalendarItem>> {
        return client.value!!.getMyCalendar(type.name, startDate, days)
    }

    suspend fun getUserSettings(): Response<UserSettings> {
        return client.value!!.getUserSettings()
    }
}
