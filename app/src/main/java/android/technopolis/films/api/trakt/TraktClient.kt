package android.technopolis.films.api.trakt

import android.technopolis.films.api.trakt.model.auth.GetNewTokenRequest
import android.technopolis.films.api.trakt.model.auth.GetTokenRequest
import android.technopolis.films.api.trakt.model.auth.GetTokenResponse
import android.technopolis.films.api.trakt.model.media.CalendarItem
import android.technopolis.films.api.trakt.model.media.CommonMediaItem
import android.technopolis.films.api.trakt.model.media.HistoryItem
import android.technopolis.films.api.trakt.model.media.WatchListItem
import android.technopolis.films.api.trakt.model.users.settings.UserSettings
import android.technopolis.films.api.trakt.model.users.stats.UserStats
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TraktClient {
    /**
     * Needed to get access_token and refresh_token from code
     */
    @POST("/oauth/token")
    suspend fun getToken(@Body request: GetTokenRequest): Response<GetTokenResponse>

    /**
     * Needed to get new access_token from refresh_token
     */
    @POST("/oauth/token")
    suspend fun getNewToken(@Body request: GetNewTokenRequest): Response<GetTokenResponse>

    /**
     * @param limit default (null) = 10, max limit = 100
     * @param ignoreCollected true to filter out movies the user has already collected
     */
    @GET("/recommendations/{type}")
    suspend fun getRecommendations(
        @Path("type") mediaType: String,
        @Query("limit") limit: Int,
        @Query("ignore_collected") ignoreCollected: Boolean?,
    ): Response<MutableList<CommonMediaItem>>

    /**
     * @param id - user 'slug' of word 'me' if there is access_token in request headers
     */
    @GET("/users/{id}/watchlist/{type}/{sort}")
    suspend fun getWatchList(
        @Path("id") id: String,
        @Path("type") type: String,
        @Path("sort") sort: String,
        @Query("page") page: Int?,
        @Query("limit") limit: Int?,
    ): Response<MutableList<WatchListItem>>

    /**
     * @param id - user 'slug' of word 'me' if there is access_token in request headers
     */
    @GET("/users/{id}/history/{type}")
    suspend fun getWatchedHistory(
        @Path("id") id: String,
        @Path("type") mediaType: String,
        @Query("page") page: Int?,
        @Query("limit") limit: Int?,
        @Query("item_id") itemId: Int?,
        @Query("start_at") startAt: String?,
        @Query("end_at") endAt: String?,
    ): Response<MutableList<HistoryItem>>

    /**
     * @param id - user 'slug' of word 'me' if there is access_token in request headers
     */
    @GET("/users/{id}/stats")
    suspend fun getStats(
        @Path("id") id: String,
    ): Response<UserStats>

    /**
     * Type is only 'movies' and 'shows'.
     *
     * @param startDate in UTC
     * @param daysToDisplay since startDay. default = 7, max = 33
     */
    @GET("/calendars/my/{type}/{start_date}/{days}")
    suspend fun getMyCalendar(
        @Path("type") mediaType: String,
        @Path("start_date") startDate: String,
        @Path("days") daysToDisplay: Int,
    ): Response<MutableList<CalendarItem>>

    @GET("/users/settings")
    suspend fun getUserSettings(): Response<UserSettings>
}
