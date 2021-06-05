package android.technopolis.films.api.model.users.settings

import kotlinx.serialization.Serializable

@Serializable
data class UserIds(
    val slug: String,
    val uuid: String,
)
