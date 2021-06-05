package android.technopolis.films.api.model.users.stats

import kotlinx.serialization.Serializable

@Serializable
data class ShowsUserStats(
    val watched: Int,
    val collected: Int,
    val ratings: Int,
    val comments: Int
)
