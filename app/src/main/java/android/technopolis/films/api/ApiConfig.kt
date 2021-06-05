package android.technopolis.films.api

import android.technopolis.films.BuildConfig
import android.technopolis.films.api.model.auth.GetTokenResponse

class ApiConfig {
    companion object {
        const val baseUrl: String = BuildConfig.TRAKT_BASE_API_URL
        const val clientId: String = BuildConfig.TRAKT_CLIENT_ID
        const val clientSecret: String = BuildConfig.TRAKT_CLIENT_SECRET
        const val redirectUrl: String = BuildConfig.TRAKT_REDIRECT_URL
        const val loginUrl: String = BuildConfig.TRAKT_LOGIN_URL
        var token: GetTokenResponse? = null
    }
}
