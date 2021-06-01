package android.technopolis.films.api

import android.content.Context
import android.technopolis.films.api.model.auth.GetNewTokenRequest
import android.technopolis.films.api.model.auth.GetTokenResponse
import android.technopolis.films.api.model.media.CalendarItem
import android.technopolis.films.api.model.media.HistoryItem
import android.technopolis.films.api.model.media.MediaType
import android.technopolis.films.api.model.media.RecommendationItem
import android.technopolis.films.api.model.media.WatchListItem
import android.technopolis.films.api.model.stats.UserStats
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import retrofit2.Call
import java.time.LocalDate

class Trakt(
    private val context: Context,
    private val client: TraktClient,
) {
    private val timeToEnd: Long
        get() = ApiConfig.token!!.run {
            expiresIn + createdAt - System.currentTimeMillis() / 1000
        }

    init {
        restoreToken(context)
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
            saveToken(newToken.body()!!)
        }
    }

    private fun saveToken(token: GetTokenResponse) {
        val preferences = context.getSharedPreferences(TRAKT_API_PREFERENCES, Context.MODE_PRIVATE)
        val edit = preferences.edit()
        val tokenInString = Json.encodeToString(token)
        edit.putString(TOKEN_NAME, tokenInString)
        edit.apply()
    }

    private fun restoreToken(context: Context): GetTokenResponse? {
        val sharedPreferences = context
            .getSharedPreferences(TRAKT_API_PREFERENCES, Context.MODE_PRIVATE)

        var str: String? = ""
        if (sharedPreferences.contains(TOKEN_NAME)) {
            str = sharedPreferences.getString(TOKEN_NAME, "")
        }

        if (!str.equals("") && (str != null)) {
            return Json.decodeFromString<GetTokenResponse>(str)
        }

        return null
    }

    fun getRecommendations(type: MediaType): Call<List<RecommendationItem>> {
        updateToken()
        return client.getRecommendations(type.name)
    }

    fun getWatchList(id: Long, type: MediaType): Call<List<WatchListItem>> {
        updateToken()
        return client.getWatchList(id, type.name)
    }

    fun getWatchedHistory(id: Long, type: MediaType): Call<HistoryItem> {
        updateToken()
        return client.getWatchedHistory(id, type.name)
    }

    fun getStats(id: Long): Call<UserStats> {
        updateToken()
        return client.getStats(id)
    }

    fun getMyShows(type: MediaType, startDate: LocalDate, days: Int): Call<List<CalendarItem>> {
        updateToken()
        return client.getMyShows(type.name, startDate, days)
    }

    companion object {
        private const val TOKEN_NAME = "trakt_token"
        private const val TRAKT_API_PREFERENCES = "trakt_api_preferences"
    }
}
