package android.technopolis.films.api.model.users.settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserSettings(
    val user: UserInfo?,
    val account: UserAccountInfo?,
    val connections: UserConnections?,
    @SerialName("sharing_text")
    val sharingText: UserSharingText?,
)
