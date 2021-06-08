package android.technopolis.films.ui.feed.film

import android.os.Bundle
import android.technopolis.films.R
import android.technopolis.films.databinding.FragmentFeedFilmBinding
import android.technopolis.films.ui.feed.FeedAdapter
import android.technopolis.films.ui.feed.FeedViewModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class FeedFilmFragment(viewModel: FeedViewModel) : Fragment(),
    SwipeRefreshLayout.OnRefreshListener {

    private var binding: FragmentFeedFilmBinding? = null
    private val feedAdapter: FeedAdapter by lazy { FeedAdapter() }
    private val feedViewModel = viewModel
    private var recyclerViewLayoutManager = LinearLayoutManager(activity)
    private lateinit var swipeLayout: SwipeRefreshLayout

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

        swipeLayout.post {
            if (!feedViewModel.isLoadMovie()) {
                swipeLayout.isRefreshing = true
                feedViewModel.updateRecommendationsMovies()
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
        }.launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onRefresh() {
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