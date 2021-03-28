package android.technopolis.films.viewmodels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FeedViewModel : ViewModel() {

    private val mText = MutableLiveData<String>().apply {
        value = "This is feed Fragment"
    }
    val text: LiveData<String> = mText
}