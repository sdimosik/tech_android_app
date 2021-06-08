package android.technopolis.films.api.trakt.model.media

import android.technopolis.films.api.tmdb.TmdbClientGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class CommonMediaItem(
    val title: String,
    val year: Int,
    val ids: MediaIds,

    var mediaUrl: String? = null,
) {
    suspend fun getUrl(type: MediaType): String? {
        /* todo CAUTION govnocode*/
        val id: Int = ids.tmdb!!
//        mediaUrl = TmdbClientGenerator.getClient().getImageUrl(type, id.toString(), "")
        return mediaUrl
    }
}
