package android.technopolis.films.ui.profile

import android.technopolis.films.repository.MainRepository
import androidx.lifecycle.ViewModel

class ProfileViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

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
        mainRepository.getUserSettings()
        isLoadProfile = true
    }

    fun getUserSetting() = mainRepository.userSettings
}
