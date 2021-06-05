package android.technopolis.films.api.model.users.stats

import android.technopolis.films.api.model.users.stats.MoviesUserStats
import android.technopolis.films.api.model.users.stats.NetworkUserStats
import android.technopolis.films.api.model.users.stats.RatingUserStats
import android.technopolis.films.api.model.users.stats.SeasonsUserStats
import android.technopolis.films.api.model.users.stats.ShowsUserStats
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
