package android.technopolis.films.api.model.users.stats

import kotlinx.serialization.Serializable

@Serializable
data class NetworkUserStats(
    val friends: Int,
    val followers: Int,
    val following: Int
)
