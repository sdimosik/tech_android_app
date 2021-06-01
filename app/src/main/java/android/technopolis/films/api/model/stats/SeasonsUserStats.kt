package android.technopolis.films.api.model.stats

import kotlinx.serialization.Serializable

@Serializable
data class SeasonsUserStats(
    val ratings: Int,
    val comments: Int
)
