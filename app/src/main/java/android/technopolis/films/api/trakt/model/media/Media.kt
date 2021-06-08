package android.technopolis.films.api.trakt.model.media

interface Media {
    val type: MediaTypeResponse
    val movie: CommonMediaItem?
    val show: CommonMediaItem?

    fun equalsTo(other: Media): Boolean {
        if (this.type != other.type) {
            return false
        }

        return when (type) {
            MediaTypeResponse.movie -> this.movie?.ids?.trakt == other.movie?.ids?.trakt
            MediaTypeResponse.show -> this.show?.ids?.trakt == other.show?.ids?.trakt
        }
    }

    fun get(): CommonMediaItem? {
        return when (type) {
            MediaTypeResponse.movie -> movie
            MediaTypeResponse.show -> show
        }
    }
}