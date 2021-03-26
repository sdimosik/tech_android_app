package android.technopolis.films.ui.watch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WatchViewModel : ViewModel() {
    private val mText = MutableLiveData<String>().apply {
        value = "This is watch Fragment"
    }
    val text: LiveData<String> = mText
}