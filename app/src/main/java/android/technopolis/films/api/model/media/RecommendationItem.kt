package android.technopolis.films.api.model.media

import android.technopolis.films.api.model.media.movies.Movie
import android.technopolis.films.api.model.media.shows.Show
import kotlinx.serialization.Serializable

@Serializable
data class RecommendationItem(
    val movie: Movie? = null,
    val show: Show? = null,
)
