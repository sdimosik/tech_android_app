package android.technopolis.films.ui.watch

import android.technopolis.films.repository.Repository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WatchViewModelFactory(
    private val mainRepository: Repository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WatchViewModel(mainRepository) as T
    }
}