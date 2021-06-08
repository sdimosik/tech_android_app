package android.technopolis.films.repository

import android.technopolis.films.api.trakt.model.media.CalendarItem
import android.technopolis.films.api.trakt.model.media.CommonMediaItem
import android.technopolis.films.api.trakt.model.media.HistoryItem
import android.technopolis.films.api.trakt.model.media.Media
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.sync.Mutex

class ConfigImpl {
    /*============================================================================================*/
    var recommendationsLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val recommendations = MutableLiveData<MutableList<CommonMediaItem>>(mutableListOf())

    /*============================================================================================*/
    val watchList = MutableLiveData<MutableList<Media>>(mutableListOf())
    var currentWatchListPage = MutableStateFlow(0)
    var watchListLoading = MutableStateFlow(false)
    var isWatchListEnded = MutableStateFlow(false)

    val watchListLoadingMutex = Mutex()
    val watchListCancel = MutableStateFlow(false)

    /*============================================================================================*/
    var historyLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val history = MutableLiveData<MutableList<HistoryItem>>(mutableListOf())
    var currentHistoryPage = MutableStateFlow(0)
    var isHistoryEnded = MutableStateFlow(false)

    /*============================================================================================*/
    var calendarLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val calendar = MutableLiveData<MutableList<CalendarItem>>(mutableListOf())

    /*============================================================================================*/
}