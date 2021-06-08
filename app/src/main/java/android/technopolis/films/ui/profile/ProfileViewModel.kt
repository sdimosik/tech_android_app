package android.technopolis.films.ui.profile

import android.technopolis.films.api.trakt.model.users.settings.UserSettings
import android.technopolis.films.api.trakt.model.users.stats.UserStats
import android.technopolis.films.repository.MainRepository
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

class ProfileViewModel : ViewModel() {
    private val repository = MainRepository()

    val stats: Flow<UserStats?> = repository.stats
    val settings: Flow<UserSettings> = repository.userSettings

    private var positionViewPager: Int? = 0

    private var isLoadProfile = false

    fun isLoadProfile() = isLoadProfile

    fun saveStateViewPager(savedState: Int?) {
        positionViewPager = savedState
    }

    fun getStateViewPager(): Int? {
        return positionViewPager
    }

    fun updateUserSetting() {
        repository.getUserSettings()
        isLoadProfile = true
    }

    fun getUserSetting() = repository.userSettings
}
