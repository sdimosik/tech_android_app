package android.technopolis.films.api.tmdb

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class TmdbClientGenerator {
    companion object {
        private val contentType = "application/json".toMediaType()
        private val httpClient by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request()
                        .newBuilder()
                        .addHeader("Authorization",
                            "Bearer " + TmdbApiConfig.accessToken)
                        .addHeader("Content-type", contentType.toString())
                        .build()
                    chain.proceed(request)
                }
                .addNetworkInterceptor(logging)
                .build()
        }

        private val infoApi: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(TmdbApiConfig.baseUrl)
                .addConverterFactory(Json.asConverterFactory(contentType))
                .client(httpClient)
                .build()
        }

        fun getClient(): Tmdb{
            return Tmdb(infoApi.create(TmdbClient::class.java))
        }

//        private val imageApi: Retrofit by lazy {
//            Retrofit.Builder()
//                .baseUrl(TmdbApiConfig.imageUrl)
//                .addConverterFactory(Json.asConverterFactory(contentType))
//                .client(httpClient)
//                .build()
//        }
//
//        fun createImageClient(): TmdbImageClient {
//            return imageApi.create(TmdbImageClient::class.java)
//        }


    }
}