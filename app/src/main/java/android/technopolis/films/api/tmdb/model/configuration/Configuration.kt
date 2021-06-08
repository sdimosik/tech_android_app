package android.technopolis.films.api.tmdb.model.configuration

import kotlinx.serialization.Serializable

@Serializable
data class Configuration(
    val images: ImagesConfig,
    val changeKeys: List<String>,
)
