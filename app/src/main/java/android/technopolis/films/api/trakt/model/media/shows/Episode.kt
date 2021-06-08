package android.technopolis.films.api.trakt.model.media.shows

import android.technopolis.films.api.trakt.model.media.MediaIds
import kotlinx.serialization.Serializable

@Serializable
data class Episode(
    val season: Int,
    val number: Int,
    val title: String,
    val ids: MediaIds
)

