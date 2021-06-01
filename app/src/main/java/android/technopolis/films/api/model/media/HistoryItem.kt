package android.technopolis.films.api.model.media

import android.technopolis.films.api.model.media.movies.Movie
import android.technopolis.films.api.model.media.shows.Show
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class HistoryItem(
    val id: Long,
    @SerialName("watched_at")
    val watchedAt: String, //LocalDateTime
    val action: String,
    override val type: MediaType,
    override val movie: Movie? = null,
    override val show: Show? = null
) : Media
