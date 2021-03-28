package android.technopolis.films.viewmodels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HistoryViewModel : ViewModel() {

    private val mText = MutableLiveData<String>().apply {
        value = "This is history Fragment"
    }
    val text: LiveData<String> = mText
}