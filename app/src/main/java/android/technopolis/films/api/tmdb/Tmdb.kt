package android.technopolis.films.api.tmdb

import android.technopolis.films.api.tmdb.model.ImageResponse
import android.technopolis.films.api.trakt.model.media.MediaType

class Tmdb(private val client: TmdbClient) {
    private fun parseMediaType(type: MediaType): String {
        return when (type) {
            MediaType.movies -> "movie"
            MediaType.shows -> "tv"
        }
    }

    suspend fun getMediaInfo(type: MediaType, id: String, language: String?): ImageResponse? {
        val parseMediaType = parseMediaType(type)
        val images = client.getImages(parseMediaType, id, language)

        if (!images.isSuccessful) {
            return null
        }

        return images.body()!!
    }

    suspend fun getImageUrl(type: MediaType, id: String, language: String?): String {
        val mediaInfo = getMediaInfo(type, id, language)
        return when (type) {
            MediaType.movies -> getMovieUrl(mediaInfo, language)
            MediaType.shows -> getShowUrl(mediaInfo, language)
        }
    }

    private fun getMovieUrl(mediaInfo: ImageResponse?, language: String?): String {
        return TmdbApiConfig.imageUrl + "w185" + mediaInfo?.posters?.get(0)?.filePath
    }

    private fun getShowUrl(mediaInfo: ImageResponse?, language: String?): String {
        return TmdbApiConfig.imageUrl + "w185" + mediaInfo?.backdrops?.get(0)?.filePath
    }
}
