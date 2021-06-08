package android.technopolis.films.api.trakt.model.media

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WatchListItem(
    val rank: Int,
    val id: Long,
    @SerialName("listed_at")
    val listedAt: String, //LocalDateTime,
    val notes: String? = null,
    override val type: MediaTypeResponse,
    override val movie: CommonMediaItem? = null,
    override val show: CommonMediaItem? = null,
) : Media
