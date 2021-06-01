package android.technopolis.films.api

import android.technopolis.films.api.model.auth.GetTokenRequest
import android.technopolis.films.api.model.auth.GetTokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface TraktAuthClient {
    /**
     * Needed to get access_token and refresh_token from code
     */
    @POST("/oauth/token")
    fun getToken(@Body request: GetTokenRequest): Call<GetTokenResponse>
}