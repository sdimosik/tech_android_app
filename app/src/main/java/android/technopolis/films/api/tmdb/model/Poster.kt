package android.technopolis.films.api.tmdb.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Poster(
    @SerialName("aspect_ratio")
    val aspectRatio: Int?,
    @SerialName("file_path")
    val filePath: String?,
    val height: Int?,
    @SerialName("iso_639_1")
    val iso6391: String?,
    @SerialName("vote_average")
    val voteAverage: Int?,
    @SerialName("vote_count")
    val voteCount: Int?,
    val width: Int?,
)
