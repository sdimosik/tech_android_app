package android.technopolis.films.ui.feed.show

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.technopolis.films.R
import android.technopolis.films.utils.Utils.isOnline
import android.technopolis.films.databinding.FragmentFeedShowBinding
import android.technopolis.films.ui.feed.FeedAdapter
import android.technopolis.films.ui.feed.FeedViewModel
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FeedShowFragment(viewModel: FeedViewModel) : Fragment(),
    SwipeRefreshLayout.OnRefreshListener {

    private var binding: FragmentFeedShowBinding? = null
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
        binding = FragmentFeedShowBinding.inflate(
            inflater, container, false
        )
        noConnectionToast =
            Toast.makeText(activity, getString(R.string.no_connection), Toast.LENGTH_SHORT)
        noConnectionToast.setGravity(Gravity.CENTER, 0, 0)
        swipeLayout = binding?.swipeContainer!!
        swipeLayout.setOnRefreshListener(this)
        swipeLayout.setColorSchemeColors(resources.getColor(R.color.purple_500))

        swipeLayout.post {
            if (!feedViewModel.isLoadShow()) {
                swipeLayout.isRefreshing = true
                if (isOnline(requireContext())) {
                    feedViewModel.updateRecommendationsShows()
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
        feedViewModel.showsRecommendations().onEach {
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
        feedViewModel.updateRecommendationsShows()
    }

    override fun onPause() {
        val state = recyclerViewLayoutManager.onSaveInstanceState()
        feedViewModel.saveStateShow(state)
        super.onPause()
    }

    override fun onResume() {
        recyclerViewLayoutManager.onRestoreInstanceState(feedViewModel.getStateShow())
        super.onResume()
    }
}
