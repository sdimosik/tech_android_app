package android.technopolis.films.api

import android.content.SharedPreferences
import android.technopolis.films.api.model.auth.GetTokenRequest
import android.technopolis.films.api.model.auth.GetTokenResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.awaitResponse

/**
 * All this must be done before creating the MainRepository!
 *
 * Build flow:
 *  1) Set preferences
 *  2.1) Exception thrown -> User is not logged in -> (hasLogin() == false)
 *      a) take loginUrl and redirect user
 *      b) obtain code
 *      c) invoke getClient(code)
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
                            .addHeader("Authorization", "Bearer " + ApiConfig.token?.accessToken)
                            .addHeader("Content-type", contentType.toString())
                            .addHeader("trakt-api-version", "2")
                            .addHeader("trakt-api-key", ApiConfig.clientId)
                            .build()
                        chain.proceed(request)
                    }
                    .addInterceptor(logging)
                    .build()

                return Retrofit.Builder()
                    .baseUrl(ApiConfig.baseUrl)
                    .addConverterFactory(Json.asConverterFactory(contentType))
                    .client(client)
                    .build()
            }

        private val traktClient: TraktClient by lazy {
            api.create(TraktClient::class.java)
        }

        private val traktAuthClient: TraktAuthClient by lazy {
            api.create(TraktAuthClient::class.java)
        }

        private fun restoreToken(): GetTokenResponse? {
            var str: String? = ""
            if (preferences?.contains(TOKEN_NAME)!!) {
                str = preferences?.getString(TOKEN_NAME, "")
            }

            if (!str.equals("") && (str != null)) {
                val token = Json.decodeFromString<GetTokenResponse>(str)
                ApiConfig.token = token
                return token
            }

            return null
        }

        fun saveToken(token: GetTokenResponse) {
            val edit = preferences?.edit()
            val tokenInString = Json.encodeToString(token)
            edit?.putString(TOKEN_NAME, tokenInString)
            edit?.apply()
        }

        /**
         * @param code is the code taken from trakt.tv after redirect to uri
         */
        fun doLogin(code: String) {
            val request = GetTokenRequest(
                code,
                ApiConfig.clientId,
                ApiConfig.clientSecret,
                ApiConfig.redirectUrl,
                "authorization_code"
            )

            val launch = GlobalScope.launch(Dispatchers.IO) {
                val token = traktAuthClient.getToken(request).execute()
                if (token.isSuccessful) {
                    ApiConfig.token = token.body()
                    saveToken(ApiConfig.token!!)
                }
            }
        }

        fun getClient(): Trakt {
            if (!hasLogin()) {
                throw ExceptionInInitializerError("User is not logged in")
            }
            return Trakt(traktClient)
        }

        fun hasLogin(): Boolean {
            if (ApiConfig.token == null) {
                return false
            }

            return true
        }

        val loginUrl = String.format(ApiConfig.loginUrl, ApiConfig.clientId, ApiConfig.redirectUrl)

        const val TOKEN_NAME = "trakt_token"
    }
}