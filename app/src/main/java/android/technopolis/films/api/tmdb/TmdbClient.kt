package android.technopolis.films.api.tmdb

import android.technopolis.films.api.tmdb.model.configuration.Configuration
import android.technopolis.films.api.tmdb.model.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbClient {
    @GET("{type}/{media_id}/images")
    suspend fun getImages(
        @Path("type") type: String,
        @Path("media_id") mediaId: String,
        @Query("language") language: String?,
    ): Response<ImageResponse>

    @GET("configuration")
    suspend fun getConfiguration(): Response<Configuration>
}