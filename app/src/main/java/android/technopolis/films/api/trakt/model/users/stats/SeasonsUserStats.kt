package android.technopolis.films.api.trakt.model.users.stats

import kotlinx.serialization.Serializable

@Serializable
data class SeasonsUserStats(
    val ratings: Int,
    val comments: Int
)
