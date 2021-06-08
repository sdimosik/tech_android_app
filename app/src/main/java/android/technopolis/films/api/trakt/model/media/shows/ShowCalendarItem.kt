package android.technopolis.films.api.trakt.model.media.shows

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShowCalendarItem(
    @SerialName("first_aired")
    val firstAired: String, //LocalDate,
    val episode: Episode,
    val show: Show
)
