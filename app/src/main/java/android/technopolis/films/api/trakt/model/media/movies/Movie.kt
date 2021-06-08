package android.technopolis.films.api.trakt.model.media.movies

import android.technopolis.films.api.trakt.model.media.MediaIds
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val title: String,
    val year: Int,
    val ids: MediaIds,
)
