package android.technopolis.films.ui.watch

import android.technopolis.films.repository.MainRepository
import android.technopolis.films.ui.profile.ProfileViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WatchViewModelFactory (
    private val mainRepository: MainRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WatchViewModel(mainRepository) as T
    }
}