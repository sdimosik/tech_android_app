package android.technopolis.films.api.trakt.model.media

import kotlinx.serialization.Serializable

@Serializable
data class CommonMediaItem(
    val title: String,
    val year: Int,
    val ids: MediaIds,
)
