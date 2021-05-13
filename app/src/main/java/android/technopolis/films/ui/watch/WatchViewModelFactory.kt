package android.technopolis.films.ui.watch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WatchViewModelFactory(
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WatchViewModel() as T
    }
}