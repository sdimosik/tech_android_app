package android.technopolis.films.api.trakt.model.media

import android.technopolis.films.api.trakt.model.media.movies.Movie
import android.technopolis.films.api.trakt.model.media.shows.Show
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HistoryItem(
    val id: Long,
    @SerialName("watched_at")
    val watchedAt: String, //LocalDateTime
    val action: String,
    override val type: MediaTypeResponse,
    override val movie: Movie? = null,
    override val show: Show? = null
) : Media
