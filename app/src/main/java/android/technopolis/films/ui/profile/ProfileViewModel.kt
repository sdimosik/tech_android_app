package android.technopolis.films.ui.profile

import android.technopolis.films.repository.MainRepository
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _text = MutableStateFlow("This is profile Fragment")
    val text: StateFlow<String> = _text.asStateFlow()
}