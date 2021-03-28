package android.technopolis.films.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalendarViewModel : ViewModel() {
    private val mText = MutableLiveData<String>().apply {
        value = "This is calendar Fragment"
    }
    val text: LiveData<String> = mText
}