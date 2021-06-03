package android.technopolis.films.ui.watch

import android.os.Bundle
import android.technopolis.films.api.model.media.Media
import android.technopolis.films.repository.MainRepository
import android.technopolis.films.repository.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WatchViewModel : ViewModel() {
    private var _tabArgs = Bundle()
    val tabArgs get() = _tabArgs

    private val _text = MutableStateFlow("This is watch Fragment")
    val text: StateFlow<String> = _text.asStateFlow()

    private val repository: Repository = MainRepository()

    private val _medias: MutableLiveData<MutableList<Media>> = repository.moviesWatchList
    val medias: MutableLiveData<MutableList<Media>> = _medias

    fun onPageChanged(savedState: Bundle) {
        tabArgs.putAll(savedState)
    }

    fun getTabProperty(name: String): Any? {
        return tabArgs.get(name)
    }
}