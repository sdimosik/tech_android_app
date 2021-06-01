package android.technopolis.films.api

import android.technopolis.films.api.model.auth.GetNewTokenRequest
import android.technopolis.films.api.model.auth.GetTokenResponse
import android.technopolis.films.api.model.media.CalendarItem
import android.technopolis.films.api.model.media.HistoryItem
import android.technopolis.films.api.model.media.RecommendationItem
import android.technopolis.films.api.model.media.WatchListItem
import android.technopolis.films.api.model.stats.UserStats
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.time.LocalDate

interface TraktClient {
    /**
     * Needed to get new access_token from refresh_token
     */
    @POST("/oauth/token")
    fun getNewToken(@Body request: GetNewTokenRequest): Call<GetTokenResponse>

    @GET("/recommendations/{type}")
    fun getRecommendations(
        @Path("type") mediaType: String,
    ): Call<List<RecommendationItem>>

    @GET("/users/{id}/watchlist/{type}/{sort}")
    fun getWatchList(
        @Path("id") id: Long,
        @Path("type") mediaType: String,
        @Path("sort") sort: String = "added",
    ): Call<List<WatchListItem>>

    @GET("/users/{id}/history/{type}")
    fun getWatchedHistory(
        @Path("id") id: Long,
        @Path("type") mediaType: String,
    ): Call<HistoryItem>

    @GET("/users/{id}/stats")
    fun getStats(
        @Path("id") id: Long,
    ): Call<UserStats>

    /**
     * Type is only 'movies' and 'shows'
     */
    @GET("/calendars/my/{type}/{start_date}/{days}")
    fun getMyShows(
        @Path("type") mediaType: String,
        @Path("start_date") startDate: LocalDate,
        @Path("days") days: Int,
    ): Call<List<CalendarItem>>
}
