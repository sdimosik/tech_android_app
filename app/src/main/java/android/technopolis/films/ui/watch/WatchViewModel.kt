package android.technopolis.films.ui.watch

import android.os.Bundle
import android.technopolis.films.repository.MainRepository
import android.technopolis.films.repository.Repository
import android.technopolis.films.ui.watch.rvMediaHolder.Media
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WatchViewModel: ViewModel() {
    private var _tabArgs = Bundle()
    val tabArgs get() = _tabArgs

    private val _text = MutableStateFlow("This is watch Fragment")
    val text: StateFlow<String> = _text.asStateFlow()

    private val _repository: Repository = MainRepository()
    val repository: Repository get() = _repository

    val medias: MutableLiveData<List<Media>> = MutableLiveData(
        listOf(
            Media(1),
            Media(1),
            Media(1),
            Media(1),
            Media(1),
            Media(1),
            Media(1),
        )
    )
}