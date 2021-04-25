package android.technopolis.films.ui.watch

import android.technopolis.films.repository.Repository
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WatchViewModel(
    private val mainRepository: Repository
) : ViewModel() {

    private val _text = MutableStateFlow("This is watch Fragment")
    val text: StateFlow<String> = _text.asStateFlow()
}