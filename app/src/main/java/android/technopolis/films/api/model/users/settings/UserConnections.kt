package android.technopolis.films.api.model.users.settings

import kotlinx.serialization.Serializable

@Serializable
data class UserConnections(
    val facebook: Boolean?,
    val twitter: Boolean?,
    val google: Boolean?,
    val tumblr: Boolean?,
    val medium: Boolean?,
    val slack: Boolean?,
    val apple: Boolean?,
)
