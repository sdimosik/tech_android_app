package android.technopolis.films.api.trakt.model.users.stats

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RatingUserStats(
    val total: Int,
    val distribution: Distribution
)

@Serializable
data class Distribution (
    @SerialName("1")
    val one: Int,
    @SerialName("2")
    val two: Int,
    @SerialName("3")
    val three: Int,
    @SerialName("4")
    val four: Int,
    @SerialName("5")
    val five: Int,
    @SerialName("6")
    val siz: Int,
    @SerialName("7")
    val seven: Int,
    @SerialName("8")
    val eight: Int,
    @SerialName("9")
    val nine: Int,
    @SerialName("10")
    val ten: Int
)
