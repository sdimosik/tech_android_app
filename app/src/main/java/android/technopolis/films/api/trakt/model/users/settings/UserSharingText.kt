package android.technopolis.films.api.trakt.model.users.settings

import kotlinx.serialization.Serializable

@Serializable
data class UserSharingText(
    val watching: String?,
    val watched: String?,
    val rated: String?,
)
