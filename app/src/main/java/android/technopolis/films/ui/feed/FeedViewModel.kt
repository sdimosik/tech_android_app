package android.technopolis.films.ui.feed

import android.os.Parcelable
import android.technopolis.films.api.model.media.MediaType
import android.technopolis.films.repository.MainRepository
import androidx.lifecycle.ViewModel

class FeedViewModel : ViewModel() {
    private val mainRepository = MainRepository()

    private var positionMovie: Parcelable? = null
    private var isLoadMovie = false

    private var positionShow: Parcelable? = null
    private var isLoadShow = false

    private var positionViewPager: Int? = 0

    fun saveStateMovie(savedState: Parcelable?) {
        positionMovie = savedState
    }

    fun getStateMovie(): Parcelable? {
        return positionMovie
    }

    fun saveStateShow(savedState: Parcelable?) {
        positionShow = savedState
    }

    fun getStateShow(): Parcelable? {
        return positionShow
    }

    fun saveStateViewPager(savedState: Int?) {
        positionViewPager = savedState
    }

    fun getStateViewPager(): Int? {
        return positionViewPager
    }

    fun updateRecommendationsMovies() {
        mainRepository.getRecommendations(MediaType.movies, false)
        isLoadMovie = true
    }

    fun isLoadMovie() = isLoadMovie

    fun moviesRecommendations() = mainRepository.moviesRecommendations

    fun updateRecommendationsShows() {
        mainRepository.getRecommendations(MediaType.shows, false)
        isLoadShow = true
    }

    fun isLoadShow() = isLoadShow

    fun showsRecommendations() = mainRepository.showsRecommendations

    // TODO SearchView
    /*fun filterList(term: String, adapter: FeedAdapter) {
        if (term.isNotEmpty()) {
            val list = adapter.originalList.filter { it.name?.contains(term, true)!! }
            adapter.filterList = list
            adapter.notifyDataSetChanged()

        } else {
            adapter.filterList = adapter.originalList
            adapter.notifyDataSetChanged()
        }
    }

    fun getOnQueryTextChange(adapter: FeedAdapter): SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(term: String?): Boolean {
                if (term != null) {
                    filterList(term, adapter)
                }
                return false
            }

            override fun onQueryTextSubmit(term: String?): Boolean {
                if (term != null) {
                    filterList(term, adapter)
                }
                return false
            }
        }*/
}
