package android.technopolis.films.api.model.users.settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserAccountInfo(
    val timezone: String,
    @SerialName("date_format")
    val dateFormat: String,
    @SerialName("time_24hr")
    val time24hr: String,
    @SerialName("cover_image")
    val coverImage: String,
)