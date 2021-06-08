package android.technopolis.films.api.trakt.model.media.shows

import android.technopolis.films.api.trakt.model.media.MediaIds
import kotlinx.serialization.Serializable

@Serializable
data class Show(
    val title: String,
    val year: Int,
    val ids: MediaIds,
)