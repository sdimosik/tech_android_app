package android.technopolis.films.api.trakt.model.media

import android.technopolis.films.api.trakt.model.media.movies.MovieCalendarItem
import android.technopolis.films.api.trakt.model.media.shows.ShowCalendarItem
import kotlinx.serialization.Serializable

@Serializable
data class CalendarItem(
    val movie: MovieCalendarItem? = null,
    val show: ShowCalendarItem? = null
)
