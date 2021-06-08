package android.technopolis.films.ui.watch.tabs

import android.content.res.Resources
import android.os.Bundle
import android.os.Parcelable
import android.technopolis.films.R
import android.technopolis.films.api.model.media.Media
import android.technopolis.films.api.model.media.MediaType
import android.technopolis.films.databinding.FragmentListBinding
import android.technopolis.films.ui.base.MainActivity
import android.technopolis.films.ui.watch.WatchViewModel
import android.technopolis.films.ui.watch.rvMediaHolder.MediaAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ListFragment : Fragment() {
    private var binding: FragmentListBinding? = null

    private val viewModel: WatchViewModel by activityViewModels()

    private val listAdapter: MediaAdapter by lazy { MediaAdapter() }
    private var tabType: MediaType? = null
    private var ARGS_TAG: String? = null
    private lateinit var networkState: StateFlow<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabType = arguments?.getSerializable(TAB_TYPE_TAG) as MediaType
        ARGS_TAG = "${tabType}_SCROLL_OFFSET"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding?.root!!
    }

    private val onScrollListener: OnScrollListener by lazy { OnScrollListener() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        val navView = (activity as MainActivity).findViewById<View>(R.id.nav_view)
        setUpSnackBars(navView)

        networkState = viewModel.networkState
        observeNetworkStatus()

        val medias = viewModel.observeList(tabType!!)
        observeMediaList(medias)

        val status = viewModel.observeListStatus(tabType!!)
        observeDownloadingStatus(status)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        badConnectionSandbar.dismiss()
        noConnectionSandbar.dismiss()

        binding?.mediaListRecyclerView?.removeOnScrollListener(onScrollListener)
        binding = null
    }

    private fun getState(): Bundle {
        val state = Bundle()
        state.putParcelable(
            ARGS_TAG,
            binding?.mediaListRecyclerView?.layoutManager?.onSaveInstanceState()
        )
        return state
    }

    private lateinit var badConnectionSandbar: Snackbar
    private lateinit var noConnectionSandbar: Snackbar

    private fun setupRecyclerView() {
        binding?.mediaListRecyclerView?.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object : DividerItemDecoration(activity, HORIZONTAL) {})
            adapter = listAdapter
        }

        val mediaListRecyclerView = binding?.mediaListRecyclerView

        mediaListRecyclerView?.layoutManager?.onRestoreInstanceState(
            viewModel.getTabProperty(ARGS_TAG!!) as Parcelable?
        )

        mediaListRecyclerView?.addOnScrollListener(onScrollListener)

        val swipeRefreshLayout = binding?.swipeRefreshLayout
        swipeRefreshLayout?.setOnRefreshListener(OnRefreshListener())
        swipeRefreshLayout?.setColorSchemeColors(resources.getColor(R.color.purple_500))
    }

    private fun setUpSnackBars(navView: View?) {
        noConnectionSandbar = Snackbar.make(binding?.swipeRefreshLayout!!,
            resources.getString(R.string.no_connection),
            Snackbar.LENGTH_INDEFINITE)
            .setAnchorView(navView)
            .setBackgroundTint(resources.getColor(R.color.bad_connection_snackar_background))
            .setTextColor(resources.getColor(R.color.bad_connection_snackar_text))

        badConnectionSandbar = Snackbar.make(binding?.swipeRefreshLayout!!,
            resources.getString(R.string.bad_connection),
            Snackbar.LENGTH_INDEFINITE)
            .setAnchorView(navView)
            .setBackgroundTint(resources.getColor(R.color.bad_connection_snackar_background))
            .setTextColor(resources.getColor(R.color.bad_connection_snackar_text))
    }

    private fun observeNetworkStatus() {
        if (!networkState.value) {
            noConnectionSandbar.show()
        }
        networkState.onEach {
            if (!it) {
                noConnectionSandbar.show()
            } else if (noConnectionSandbar.isShown) {
                noConnectionSandbar.dismiss()
            }
        }.launchIn(MainScope())
    }

    private fun observeMediaList(medias: Flow<MutableList<Media>>) {
        medias.onEach {
            listAdapter.submitList(it)
        }.launchIn(MainScope())
    }

    private fun observeDownloadingStatus(status: StateFlow<Boolean>) {
        MainScope().launch {
            status.onEach {
                if (it) {
//                    launch {
//                        delay(TIME_TO_BAD_CONNECTION_NOTIFICATION)
//                        if (status.value) {
//                            badConnectionSandbar.show()
//                        } else {
//                            badConnectionSandbar.dismiss()
//                            cancel()
//                        }
//                    }
                } else {
                    listAdapter.notifyDataSetChanged()
                    showList(listAdapter.currentList.isEmpty())
//                    badConnectionSandbar.dismiss()
                }
            }.launchIn(this)
        }
    }

    private fun showList(isEmpty: Boolean) {
        if (isEmpty) {
            binding?.mediaListRecyclerView?.visibility = View.GONE
            binding?.mediaListEmptyList?.visibility = View.VISIBLE
        } else {
            binding?.mediaListRecyclerView?.visibility = View.VISIBLE
            binding?.mediaListEmptyList?.visibility = View.GONE
        }
    }

    inner class OnScrollListener : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            viewModel.onPageChanged(getState())
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            val pos = linearLayoutManager.findLastCompletelyVisibleItemPosition()
            val last = recyclerView.adapter!!.itemCount
            if ((pos == last - 1) && (last != 0)) {
                getMoreData()
            }
        }
    }

    private fun getMoreData() {
        if (networkState.value) {
            viewModel.getMoreData(tabType!!)
        }
    }

    private fun updateData() {
        if (networkState.value) {
            viewModel.updateList(tabType!!)
        }
    }

    inner class OnRefreshListener : SwipeRefreshLayout.OnRefreshListener {
        override fun onRefresh() {
            MainScope().launch(Dispatchers.Unconfined) {
                val swipeRefreshLayout = binding?.swipeRefreshLayout
                swipeRefreshLayout?.isRefreshing = true
                updateData()
                if (!networkState.value) {
                    swipeRefreshLayout?.isRefreshing = false
                    cancel()
                }
                var prev = true
                viewModel.observeListStatus(tabType!!).onEach {
                    if (it) {
                        prev = !prev
                    }
                    if (it == prev) {
                        swipeRefreshLayout?.isRefreshing = false
                        cancel()
                    }
                }.launchIn(this)
            }
        }
    }

    companion object {
        const val TAB_TYPE_TAG = "TAB_TYPE"
        const val TIME_TO_BAD_CONNECTION_NOTIFICATION = 10_000L

        fun newInstance(type: MediaType): ListFragment {
            val listFragment = ListFragment()
            val state = Bundle()
            state.putSerializable(TAB_TYPE_TAG, type)
            listFragment.arguments = state
            return listFragment
        }
    }
}
