package android.technopolis.films.api.trakt.model.media

import android.technopolis.films.api.trakt.model.media.movies.Movie
import android.technopolis.films.api.trakt.model.media.shows.Show

interface Media {
    val type: MediaTypeResponse
    val movie: Movie?
    val show: Show?

    fun equalsTo(other: Media): Boolean {
        if (this.type != other.type) {
            return false
        }

        return when (type) {
            MediaTypeResponse.movie -> this.movie?.ids?.trakt == other.movie?.ids?.trakt
            MediaTypeResponse.show -> this.show?.ids?.trakt == other.show?.ids?.trakt
        }
    }
}