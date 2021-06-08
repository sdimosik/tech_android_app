package android.technopolis.films.ui.history

import android.technopolis.films.repository.MainRepository
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HistoryViewModel : ViewModel() {
    private val mainRepository = MainRepository()

    private val _text = MutableStateFlow("This is history Fragment")
    val text: StateFlow<String> = _text.asStateFlow()
}