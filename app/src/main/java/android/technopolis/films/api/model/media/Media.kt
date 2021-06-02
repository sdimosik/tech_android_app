package android.technopolis.films.api.model.media

import android.technopolis.films.api.model.media.movies.Movie
import android.technopolis.films.api.model.media.shows.Show
import kotlinx.serialization.Serializable

interface Media {
    val type: String
    val movie: Movie?
    val show: Show?
}