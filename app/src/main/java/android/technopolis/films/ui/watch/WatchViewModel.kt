package android.technopolis.films.ui.watch

import android.os.Bundle
import android.technopolis.films.api.model.media.Media
import android.technopolis.films.api.model.media.MediaType
import android.technopolis.films.repository.MainRepository
import android.technopolis.films.repository.Repository
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WatchViewModel : ViewModel() {
    private var _tabArgs = Bundle()
    val tabArgs get() = _tabArgs

    private val _text = MutableStateFlow("This is watch Fragment")
    val text: StateFlow<String> = _text.asStateFlow()

    private val repository: Repository by lazy {
        val mainRepository = MainRepository()
        mainRepository.getWatchList(MediaType.movies)
        mainRepository.getWatchList(MediaType.shows)
        mainRepository
    }

    fun getMoreData(type: MediaType) {
        Log.d("INFO","${this.javaClass}: getMoreData(): $type")
        repository.getWatchList(type)
    }

    fun observeListStatus(type: MediaType): StateFlow<Boolean> {
        return when (type) {
            MediaType.movies -> {
                repository.moviesWatchListLoading
            }
            MediaType.shows -> {
                repository.showsWatchListLoading
            }
        }
    }

    fun observeList(type: MediaType): Flow<MutableList<Media>> {
        return when (type) {
            MediaType.movies -> {
                repository.moviesWatchList
            }
            MediaType.shows -> {
                repository.showsWatchList
            }
        }
    }


    fun onPageChanged(savedState: Bundle) {
        tabArgs.putAll(savedState)
    }

    fun getTabProperty(name: String): Any? {
        return tabArgs.get(name)
    }

    fun updateList(type: MediaType) {
        repository.updateWatchList(type)
    }
}