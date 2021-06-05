package android.technopolis.films.api.model.users.stats

import kotlinx.serialization.Serializable

@Serializable
data class SeasonsUserStats(
    val ratings: Int,
    val comments: Int
)
