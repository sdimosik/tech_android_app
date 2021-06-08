package android.technopolis.films.api.trakt.model.users.settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val username: String?,
    val private: Boolean?,
    val name: String?,
    val vip: Boolean?,
    @SerialName("vip_ep")
    val vipEp: Boolean?,
    val ids: UserIds?,
    @SerialName("joined_at")
    val joinedAt: String?, //LocalDate
    val location: String?,
    val about: String?,
    val gender: String?,
    val age: Int?,
    val images: UserImages?,
    @SerialName("vip_og")
    val vipOg: Boolean?,
    @SerialName("vip_years")
    val vipYears: Int?,
    @SerialName("vip_cover_image")
    val vipCoverImage: String?,
)
