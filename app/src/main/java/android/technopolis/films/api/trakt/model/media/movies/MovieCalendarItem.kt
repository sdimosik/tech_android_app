package android.technopolis.films.api.trakt.model.media.movies

import kotlinx.serialization.Serializable

@Serializable
data class MovieCalendarItem(
    val released: String, //LocalDate,
    val movie: Movie
)
