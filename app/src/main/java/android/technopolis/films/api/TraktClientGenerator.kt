package android.technopolis.films.api

import android.content.Context
import android.technopolis.films.api.model.auth.GetTokenRequest
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class TraktClientGenerator {
    companion object {
        private val api: Retrofit
            get() {
                val contentType = MediaType.get("application/json")

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

        /**
         * @param code is the code taken from trakt.tv after redirect to uri
         */
        fun getClient(context: Context, code: String): Trakt {
            val token = traktAuthClient.getToken(GetTokenRequest(
                code,
                ApiConfig.clientId,
                ApiConfig.clientSecret,
                ApiConfig.redirectUrl
            )).execute()

            if (token.isSuccessful) {
                ApiConfig.token = token.body()
            }

            return Trakt(context, traktClient)
        }

        val loginUrl = String.format(ApiConfig.loginUrl, ApiConfig.clientId, ApiConfig.redirectUrl)
    }
}