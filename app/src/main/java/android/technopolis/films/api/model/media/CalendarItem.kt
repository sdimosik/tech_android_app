package android.technopolis.films.api.model.media

import android.technopolis.films.api.model.media.movies.MovieCalendarItem
import android.technopolis.films.api.model.media.shows.ShowCalendarItem
import kotlinx.serialization.Serializable

@Serializable
data class CalendarItem(
    val movie: MovieCalendarItem? = null,
    val show: ShowCalendarItem? = null
)
