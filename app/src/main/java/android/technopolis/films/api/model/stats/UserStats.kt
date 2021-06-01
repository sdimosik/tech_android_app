package android.technopolis.films.api.model.stats

import android.technopolis.films.api.model.stats.MoviesUserStats
import android.technopolis.films.api.model.stats.NetworkUserStats
import android.technopolis.films.api.model.stats.RatingUserStats
import android.technopolis.films.api.model.stats.SeasonsUserStats
import android.technopolis.films.api.model.stats.ShowsUserStats
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
