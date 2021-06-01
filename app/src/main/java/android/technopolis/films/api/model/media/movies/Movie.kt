package android.technopolis.films.api.model.media.movies

import android.technopolis.films.api.model.media.MediaIds
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val title: String,
    val year: Int,
    val ids: MediaIds,
)
