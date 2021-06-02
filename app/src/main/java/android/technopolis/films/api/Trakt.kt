package android.technopolis.films.api

import android.technopolis.films.api.model.media.CalendarItem
import android.technopolis.films.api.model.media.HistoryItem
import android.technopolis.films.api.model.media.MediaType
import android.technopolis.films.api.model.media.RecommendationItem
import android.technopolis.films.api.model.media.SortType
import android.technopolis.films.api.model.media.WatchListItem
import android.technopolis.films.api.model.stats.UserStats
import retrofit2.Response

class Trakt(
    traktClient: TraktClient,
) {
    private val client: TraktClient = traktClient
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
    ): Response<List<RecommendationItem>> {
        return client.getRecommendations(type.name, limit, ignoreCollected)
    }

    suspend fun getWatchList(
        id: String,
        type: MediaType,
        sort: SortType,
        page: Int,
        limit: Int,
    ): Response<MutableList<WatchListItem>> {
        return client.getWatchList(id, type.name, sort.name, page, limit)
    }

    suspend fun getWatchedHistory(
        id: String,
        type: MediaType,
        page: Int,
        limit: Int,
        itemId: Int,
        startAt: String,
        endAt: String,
    ): Response<HistoryItem> {
        return client.getWatchedHistory(id, type.name, page, limit, itemId, startAt, endAt)
    }

    suspend fun getStats(id: String): Response<UserStats> {
        return client.getStats(id)
    }

    suspend fun getMyShows(
        type: MediaType,
        startDate: String,
        days: Int,
    ): Response<List<CalendarItem>> {
        return client.getMyCalendar(type.name, startDate, days)
    }
}
