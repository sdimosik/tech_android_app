package android.technopolis.films.api.tmdb.model

import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    val id: Int?,
    val backdrops: List<Poster>?,
    val posters: List<Poster>?,
    val logos: List<Poster>?,
)