package android.technopolis.films.api.model.media.movies

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class MovieCalendarItem(
    val released: String, //LocalDate,
    val movie: Movie
)
