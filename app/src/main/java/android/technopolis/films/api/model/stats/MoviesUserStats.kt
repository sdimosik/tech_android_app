package android.technopolis.films.api.model.stats

import kotlinx.serialization.Serializable

@Serializable
data class MoviesUserStats(
    val plays: Int,
    val watched: Int,
    val minutes: Int,
    val collected: Int,
    val ratings: Int,
    val comments: Int
)
