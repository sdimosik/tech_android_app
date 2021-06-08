package android.technopolis.films.ui.calendar

import android.technopolis.films.repository.MainRepository
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CalendarViewModel : ViewModel() {
    private val mainRepository = MainRepository()

    private val _text = MutableStateFlow("This is calendar Fragment")
    val text: StateFlow<String> = _text.asStateFlow()

}