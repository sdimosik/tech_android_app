package android.technopolis.films.api.trakt.model.users.settings

import kotlinx.serialization.Serializable

@Serializable
data class UserIds(
    val slug: String,
    val uuid: String,
)
