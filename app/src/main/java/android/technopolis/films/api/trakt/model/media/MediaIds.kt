package android.technopolis.films.api.trakt.model.media

import kotlinx.serialization.Serializable

@Serializable
data class MediaIds(
    val trakt: Int,
    val slug: String? = null,
    val tvdb: Int? = null,
    val imdb: String? = null,
    val tmdb: Int? = null,
    val tvrage: Int? = null,
)
