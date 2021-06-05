package android.technopolis.films.api.model.media.shows

import android.technopolis.films.api.model.media.MediaIds
import kotlinx.serialization.Serializable

@Serializable
data class Show(
    val title: String,
    val year: Int,
    val ids: MediaIds,
)