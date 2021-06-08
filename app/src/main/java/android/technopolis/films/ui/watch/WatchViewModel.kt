package android.technopolis.films.ui.watch

import android.os.Bundle
import android.technopolis.films.api.trakt.model.media.Media
import android.technopolis.films.api.trakt.model.media.MediaType
import android.technopolis.films.repository.MainRepository
import android.technopolis.films.repository.Repository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WatchViewModel : ViewModel() {
    private var _tabArgs = Bundle()
    val tabArgs get() = _tabArgs

    private var _networkState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    var networkState = _networkState.asStateFlow()

    private val repository: Repository = MainRepository()

    fun getMoreData(type: MediaType) {
        if(networkState.value) {
            repository.getWatchList(type)
        }
    }

    fun observeListStatus(type: MediaType): StateFlow<Boolean> {
        return when (type) {
            MediaType.movies -> {
                repository.moviesWatchListLoading.asStateFlow()
            }
            MediaType.shows -> {
                repository.showsWatchListLoading.asStateFlow()
            }
        }
    }

    fun observeList(type: MediaType): Flow<MutableList<Media>> {
        val list = when (type) {
            MediaType.movies -> {
                repository.moviesWatchList
            }
            MediaType.shows -> {
                repository.showsWatchList
            }
        }
        if (list.value!!.isEmpty() && networkState.value) {
            repository.getWatchList(type)
        }
        return list.asFlow()
    }

    fun onPageChanged(savedState: Bundle) {
        tabArgs.putAll(savedState)
    }

    fun getTabProperty(name: String): Any? {
        return tabArgs.get(name)
    }

    fun updateList(type: MediaType) {
        if(networkState.value) {
            repository.updateWatchList(type)
        }
    }

    fun setNetworkState(state: Boolean) {
        _networkState.value = state
    }
}