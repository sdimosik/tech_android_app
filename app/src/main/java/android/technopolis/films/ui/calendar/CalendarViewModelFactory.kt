package android.technopolis.films.ui.calendar

import android.technopolis.films.repository.MainRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CalendarViewModelFactory(
    private val mainRepository: MainRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CalendarViewModel(mainRepository) as T
    }
}