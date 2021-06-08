package android.technopolis.films.api.trakt.model.users.settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserAccountInfo(
    @SerialName("timezone")
    val timezone: String?,
    @SerialName("date_format")
    val dateFormat: String?,
    @SerialName("time_24hr")
    val time24hr: Boolean?,
    @SerialName("cover_image")
    val coverImage: String?,
    val token: String?,
)