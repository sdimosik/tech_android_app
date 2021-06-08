package android.technopolis.films.api.trakt.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetTokenRequest(
    val code: String,
    @SerialName("client_id")
    val clientId: String,
    @SerialName("client_secret")
    val clientSecret: String,
    @SerialName("redirect_uri")
    val redirectUri: String,
    @SerialName("grant_type")
    val grantType: String
)
