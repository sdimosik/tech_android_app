package android.technopolis.films.ui.history

import android.technopolis.films.repository.MainRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HistoryViewModelFactory(
    private val mainRepository: MainRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HistoryViewModel(mainRepository) as T
    }
}