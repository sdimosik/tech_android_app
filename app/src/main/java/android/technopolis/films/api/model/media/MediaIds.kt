package android.technopolis.films.api.model.media

import kotlinx.serialization.Serializable

@Serializable
data class MediaIds(
    val trakt: String,
    val slug: String? = null,
    val tvdb: String? = null,
    val imdb: String? = null,
    val tmdb: String? = null,
)