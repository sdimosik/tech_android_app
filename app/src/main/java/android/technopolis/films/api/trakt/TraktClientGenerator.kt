package android.technopolis.films.api.trakt

import android.content.SharedPreferences
import android.technopolis.films.api.trakt.model.auth.GetNewTokenRequest
import android.technopolis.films.api.trakt.model.auth.GetTokenRequest
import android.technopolis.films.api.trakt.model.auth.GetTokenResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

/**
 * All this must be done before creating the MainRepository!
 *
 * Build flow:
 *  1) Set preferences
 *  2.1) Exception thrown -> User is not logged in -> (hasLogin() == false)
 *      a) take loginUrl and redirect user
 *      b) obtain code
 *      c) invoke doLogin(code)
 *  2.2) hasLogin() == true
 *      a) invoke getClient()
 *
 */
class TraktClientGenerator {
    companion object {
        var preferences: SharedPreferences? = null
            set(value) {
                field = value
                if (restoreToken() == null) {
                    throw ExceptionInInitializerError("User is not logged in")
                }
            }
            get() {
                if (field == null) {
                    throw ExceptionInInitializerError("Preferences are not provided")
                }
                return field
            }

        private val api: Retrofit
            get() {
                val contentType = "application/json".toMediaType()

                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)

                val client = OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val request = chain.request()
                            .newBuilder()
                            .addHeader("Authorization",
                                TraktApiConfig.token?.tokenType + " " + TraktApiConfig.token?.accessToken)
                            .addHeader("Content-type", contentType.toString())
                            .addHeader("trakt-api-version", "2")
                            .addHeader("trakt-api-key", TraktApiConfig.clientId)
                            .build()
                        chain.proceed(request)
                    }
                    .addNetworkInterceptor(logging)
                    .build()

                return Retrofit.Builder()
                    .baseUrl(TraktApiConfig.baseUrl)
                    .addConverterFactory(Json.asConverterFactory(contentType))
                    .client(client)
                    .build()
            }

        private var traktClient: MutableStateFlow<TraktClient?> = MutableStateFlow(null)

        private fun createClient(): TraktClient {
            return api.create(TraktClient::class.java)
        }

        private fun restoreToken(): GetTokenResponse? {
            var str: String? = ""
            if (preferences?.contains(TOKEN_NAME)!!) {
                str = preferences?.getString(TOKEN_NAME, "")
            }

            if (!str.equals("") && (str != null)) {
                val token = Json.decodeFromString<GetTokenResponse>(str)
                TraktApiConfig.token = token
                traktClient.value = createClient()
                return token
            }

            return null
        }

        private fun saveToken(token: GetTokenResponse) {
            TraktApiConfig.token = token
            val edit = preferences?.edit()
            val tokenInString = Json.encodeToString(token)
            edit?.putString(TOKEN_NAME, tokenInString)
            edit?.apply()
            traktClient.value = updateClient()
        }

        /**
         * @param code is the code taken from trakt.tv after redirect to uri
         */
        fun doLogin(code: String) {
            val request = GetTokenRequest(
                code,
                TraktApiConfig.clientId,
                TraktApiConfig.clientSecret,
                TraktApiConfig.redirectUrl,
                AUTHORIZATION_CODE
            )
            traktClient.value = createClient()
            GlobalScope.launch(Dispatchers.IO) {
                val token = traktClient.value!!.getToken(request)
                if (token.isSuccessful) {
                    saveToken(token.body()!!)
                }
            }
        }

        fun updateToken() {
            val request = GetNewTokenRequest(
                TraktApiConfig.token!!.refreshToken,
                TraktApiConfig.clientId,
                TraktApiConfig.clientSecret,
                TraktApiConfig.redirectUrl,
                REFRESH_TOKEN
            )

            if (traktClient.value == null) {
                traktClient.value = createClient()
            }

            runBlocking(Dispatchers.IO) {
                val token = traktClient.value!!.getNewToken(request)
                if (token.isSuccessful) {
                    saveToken(token.body()!!)
                }
            }
        }

        fun updateClient(): TraktClient {
            return createClient()
        }

        fun getClient(): Trakt {
            if (!hasLogin()) {
                throw ExceptionInInitializerError("User is not logged in")
            }

            return Trakt(traktClient)
        }

        fun hasLogin(): Boolean {
            if (TraktApiConfig.token == null) {
                return false
            }

            return true
        }

        val loginUrl = String.format(TraktApiConfig.loginUrl, TraktApiConfig.clientId, TraktApiConfig.redirectUrl)

        private const val TOKEN_NAME = "trakt_token"
        private const val AUTHORIZATION_CODE = "authorization_code"
        private const val REFRESH_TOKEN = "refresh_token"

    }
}