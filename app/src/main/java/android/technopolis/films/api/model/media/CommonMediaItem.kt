package android.technopolis.films.api.model.media

import android.technopolis.films.api.model.media.movies.Movie
import android.technopolis.films.api.model.media.shows.Show
import kotlinx.serialization.Serializable

@Serializable
data class CommonMediaItem(
    override val type: MediaTypeResponse,
    override val movie: Movie?,
    override val show: Show?,
) : Media
