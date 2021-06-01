package android.technopolis.films.api

import android.technopolis.films.api.model.auth.GetNewTokenRequest
import android.technopolis.films.api.model.media.CalendarItem
import android.technopolis.films.api.model.media.HistoryItem
import android.technopolis.films.api.model.media.MediaType
import android.technopolis.films.api.model.media.RecommendationItem
import android.technopolis.films.api.model.media.WatchListItem
import android.technopolis.films.api.model.stats.UserStats
import retrofit2.Call
import java.time.LocalDate

class Trakt(
    traktClient: TraktClient
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
        if (timeToEnd < 0) {
            return
        }

        val newToken = client.getNewToken(GetNewTokenRequest(
            ApiConfig.token!!.refreshToken,
            ApiConfig.clientId,
            ApiConfig.clientSecret,
            ApiConfig.redirectUrl
        )).execute()

        if (newToken.isSuccessful) {
            ApiConfig.token = newToken.body()
            TraktClientGenerator.saveToken(ApiConfig.token!!)
        }
    }

    fun getRecommendations(type: MediaType): Call<List<RecommendationItem>> {
        return client.getRecommendations(type.name)
    }

    fun getWatchList(id: Long, type: MediaType): Call<List<WatchListItem>> {
        return client.getWatchList(id, type.name)
    }

    fun getWatchedHistory(id: Long, type: MediaType): Call<HistoryItem> {
        return client.getWatchedHistory(id, type.name)
    }

    fun getStats(id: Long): Call<UserStats> {
        return client.getStats(id)
    }

    fun getMyShows(type: MediaType, startDate: LocalDate, days: Int): Call<List<CalendarItem>> {
        return client.getMyShows(type.name, startDate, days)
    }
}
