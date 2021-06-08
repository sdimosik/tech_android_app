package android.technopolis.films.api.trakt.model.users.stats

import kotlinx.serialization.Serializable

@Serializable
data class UserStats(
    val movies: MoviesUserStats,
    val shows: ShowsUserStats,
    val seasons: SeasonsUserStats,
    val episodes: MoviesUserStats,
    val network: NetworkUserStats,
    val rating: RatingUserStats
)
