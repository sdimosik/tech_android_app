package android.technopolis.films.ui.feed.film

import android.os.Bundle
import android.technopolis.films.R
import android.technopolis.films.utils.Utils.isOnline
import android.technopolis.films.databinding.FragmentFeedFilmBinding
import android.technopolis.films.ui.feed.FeedAdapter
import android.technopolis.films.ui.feed.FeedViewModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class FeedFilmFragment(viewModel: FeedViewModel) : Fragment(),
    SwipeRefreshLayout.OnRefreshListener {

    private var binding: FragmentFeedFilmBinding? = null
    private val feedAdapter: FeedAdapter by lazy { FeedAdapter() }
    private val feedViewModel = viewModel
    private var recyclerViewLayoutManager = LinearLayoutManager(activity)
    private lateinit var swipeLayout: SwipeRefreshLayout

    private lateinit var noConnectionToast: Toast

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFeedFilmBinding.inflate(
            inflater, container, false
        )

        swipeLayout = binding?.swipeContainer!!
        swipeLayout.setOnRefreshListener(this)
        swipeLayout.setColorSchemeColors(resources.getColor(R.color.purple_500))

        noConnectionToast =
            Toast.makeText(activity, getString(R.string.no_connection), Toast.LENGTH_SHORT)

        swipeLayout.post {
            if (!feedViewModel.isLoadMovie()) {
                swipeLayout.isRefreshing = true
                if (isOnline(requireContext())) {
                    feedViewModel.updateRecommendationsMovies()
                } else {
                    noConnectionToast.show()
                    swipeLayout.isRefreshing = false
                }
            }
        }

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        subscribeDataCallBack()
    }

    private fun setUpRecyclerView() {
        binding?.recyclerView?.apply {
            layoutManager = recyclerViewLayoutManager
            setHasFixedSize(true)
            addItemDecoration(object :
                DividerItemDecoration(activity, LinearLayout.HORIZONTAL) {})
            adapter = feedAdapter
        }
    }

    private fun subscribeDataCallBack() {
        feedViewModel.moviesRecommendations().onEach {
            feedAdapter.differ.submitList(it)
            swipeLayout.isRefreshing = false
        }.launchIn(MainScope())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        noConnectionToast.cancel()
        binding = null
    }

    override fun onRefresh() {
        if (!isOnline(requireContext())) {
            swipeLayout.isRefreshing = false
            noConnectionToast.show()
            return
        }
        swipeLayout.isRefreshing = true
        feedViewModel.updateRecommendationsMovies()
    }

    override fun onPause() {
        val state = recyclerViewLayoutManager.onSaveInstanceState()
        feedViewModel.saveStateMovie(state)
        super.onPause()
    }

    override fun onResume() {
        recyclerViewLayoutManager.onRestoreInstanceState(feedViewModel.getStateMovie())
        super.onResume()
    }
}
