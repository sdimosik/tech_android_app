package android.technopolis.films.api.trakt.model.users.stats

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
