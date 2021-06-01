package android.technopolis.films.api.model.media.shows

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class ShowCalendarItem(
    @SerialName("first_aired")
    val firstAired: LocalDate,
    val episode: Episode,
    val show: Show
)
