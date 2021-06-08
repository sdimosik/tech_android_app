package android.technopolis.films.api.tmdb

import android.technopolis.films.BuildConfig

class TmdbApiConfig {
    companion object {
        const val baseUrl: String = BuildConfig.TMDB_BASE_API_URL
        const val imageUrl: String = BuildConfig.TMDB_IMAGE_API_URL
        const val accessToken: String = BuildConfig.TMDB_ACCESS_TOKEN
    }
}