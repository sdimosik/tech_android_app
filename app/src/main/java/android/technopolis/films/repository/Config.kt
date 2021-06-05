package android.technopolis.films.repository

import android.technopolis.films.api.model.media.MediaType

class Config {
    private val moviesConfig = ConfigImpl()
    private val showsConfig = ConfigImpl()

    fun getConfig(type: MediaType): ConfigImpl {
        return when (type) {
            MediaType.movies -> moviesConfig
            MediaType.shows -> showsConfig
        }
    }
}