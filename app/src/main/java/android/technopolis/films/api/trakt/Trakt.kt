package android.technopolis.films.api.trakt

import android.technopolis.films.api.trakt.model.media.CalendarItem
import android.technopolis.films.api.trakt.model.media.CommonMediaItem
import android.technopolis.films.api.trakt.model.media.HistoryItem
import android.technopolis.films.api.trakt.model.media.MediaType
import android.technopolis.films.api.trakt.model.media.SortType
import android.technopolis.films.api.trakt.model.media.WatchListItem
import android.technopolis.films.api.trakt.model.users.settings.UserSettings
import android.technopolis.films.api.trakt.model.users.stats.UserStats
import kotlinx.coroutines.flow.MutableStateFlow

class Trakt(
    traktClient: MutableStateFlow<TraktClient?>,
) {
    private var client: MutableStateFlow<TraktClient?> = traktClient
        get() {
            updateToken()
            return field
        }

    private val timeToEnd: Long
        get() = TraktApiConfig.token!!.run {
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
    ): MutableList<CommonMediaItem> {
        val recommendations = client.value!!.getRecommendations(type.name, limit, ignoreCollected)

        if (!recommendations.isSuccessful) {
            println(recommendations.errorBody())
            return mutableListOf()
        }

        recommendations.body()?.forEach {
            it.getUrl(type)
        }

        return recommendations.body()!!
    }

    suspend fun getWatchList(
        id: String,
        type: MediaType,
        sort: SortType,
        page: Int,
        limit: Int,
    ): MutableList<WatchListItem> {
        val watchList = client.value!!.getWatchList(id, type.name, sort.name, page, limit)
        if (!watchList.isSuccessful) {
            println(watchList.errorBody())
            return mutableListOf()
        }
        watchList.body()?.forEach {
            it.get()?.getUrl(type)
        }
        return watchList.body()!!
    }

    suspend fun getWatchedHistory(
        id: String,
        type: MediaType,
        page: Int,
        limit: Int,
        itemId: Int?,
        startAt: String?,
        endAt: String?,
    ): MutableList<HistoryItem> {
        val watchedHistory =
            client.value!!.getWatchedHistory(id, type.name, page, limit, itemId, startAt, endAt)
        if (!watchedHistory.isSuccessful) {
            println(watchedHistory.errorBody())
            return mutableListOf()
        }
        return watchedHistory.body()!!
    }

    suspend fun getStats(id: String): UserStats {
        val stats = client.value!!.getStats(id)
        if (!stats.isSuccessful) {
            println(stats.errorBody())
        }
        return stats.body()!!
    }

    suspend fun getMyCalendar(
        type: MediaType,
        startDate: String,
        days: Int,
    ): MutableList<CalendarItem> {
        val myCalendar = client.value!!.getMyCalendar(type.name, startDate, days)
        if (!myCalendar.isSuccessful) {
            println(myCalendar.errorBody())
            return mutableListOf()
        }
        return myCalendar.body()!!
    }

    suspend fun getUserSettings(): UserSettings {
        val userSettings = client.value!!.getUserSettings()
        if (!userSettings.isSuccessful) {
            println(userSettings.errorBody())
        }
        return userSettings.body()!!
    }
}
