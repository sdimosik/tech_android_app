package android.technopolis.films.ui.feed

import android.technopolis.films.repository.MainRepository
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FeedViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _text = MutableStateFlow("This is feed Fragment")
    val text: StateFlow<String> = _text.asStateFlow()

    private val tempList = MutableStateFlow(
        listOf(
            FeedItemModel("1", "url", "qwe", "qweqweqweqwe"),
            FeedItemModel("2", "url", "aaaa", "asdadasdasdsdsd"),
            FeedItemModel("3", "url", "qwewqewew", "qweweqweqeqweqee"),
            FeedItemModel("4", "url", "we", "wewewqeqwe"),
            FeedItemModel("5", "url", "wewewqewqwe", "weqweqwe"),
            FeedItemModel("6", "url", "ghhhh", "hhhhh"),
            FeedItemModel("7", "url", "tr", "rtetert"),
            FeedItemModel("8", "url", "q", "poui"),
            FeedItemModel("9", "url", "mnb", "mnbmbmbnm"),
            FeedItemModel("10", "url", "nb", "xvxvxv"),
            FeedItemModel("11", "url", "sda", "fgftrertert"),
            FeedItemModel("12", "url", "tyuio", "wqewqe"),
            FeedItemModel("13", "url", "sdasds", "sd,nbv"),
            FeedItemModel("14", "url", "xczzcxc", ",m,bnbbnbn"),
            FeedItemModel("15", "url", "wqe11", "123125124"),
        )
    )

    fun observeFavoriteList(): StateFlow<List<FeedItemModel>> {
        return tempList
    }
}
