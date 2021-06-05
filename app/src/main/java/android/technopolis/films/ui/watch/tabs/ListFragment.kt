package android.technopolis.films.ui.watch.tabs

import android.os.Bundle
import android.os.Parcelable
import android.technopolis.films.api.model.media.MediaType
import android.technopolis.films.databinding.FragmentListBinding
import android.technopolis.films.ui.watch.WatchViewModel
import android.technopolis.films.ui.watch.rvMediaHolder.MediaAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ListFragment : Fragment() {
    private var binding: FragmentListBinding? = null

    private val viewModel: WatchViewModel by activityViewModels()

    private val listAdapter: MediaAdapter by lazy { MediaAdapter() }
    private var tabType: MediaType? = null
    private var ARGS_TAG: String? = null

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
        val mediaListRecyclerView = binding?.mediaListRecyclerView

        mediaListRecyclerView?.layoutManager?.onRestoreInstanceState(
            viewModel.getTabProperty(ARGS_TAG!!) as Parcelable?
        )

        mediaListRecyclerView?.addOnScrollListener(onScrollListener)

        binding?.swipeRefreshLayout?.setOnRefreshListener(OnRefreshListener())
    }

    override fun onDestroyView() {
        super.onDestroyView()

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

    private fun setupRecyclerView() {
        binding?.mediaListRecyclerView?.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object : DividerItemDecoration(activity, HORIZONTAL) {})
            adapter = listAdapter
        }

        val medias = viewModel.observeList(tabType!!)
        medias.onEach {
            listAdapter.submitList(it)
        }.launchIn(lifecycleScope)

        val status = viewModel.observeListStatus(tabType!!)
        status.onEach {
            if (it) {
                Log.d("INFO","${this.javaClass}: setupRecyclerView() LOADING")
            } else {
                listAdapter.notifyDataSetChanged()
            }
        }.launchIn(lifecycleScope)
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
                viewModel.getMoreData(tabType!!)
            }
        }
    }

    inner class OnRefreshListener : SwipeRefreshLayout.OnRefreshListener {
        override fun onRefresh() {
            MainScope().launch(Dispatchers.Unconfined) {
                binding?.swipeRefreshLayout?.isRefreshing = true
                viewModel.updateList(tabType!!)
                var prev = true
                viewModel.observeListStatus(tabType!!).onEach {
                    println(it)
                    if (it) {
                        prev = !prev
                    }
                    if (it == prev) {
                        binding?.swipeRefreshLayout?.isRefreshing = false
                        cancel()
                    }
                }.launchIn(this)
            }
        }
    }

    companion object {
        const val TAB_TYPE_TAG = "TAB_TYPE"

        fun newInstance(type: MediaType): ListFragment {
            val listFragment = ListFragment()
            val state = Bundle()
            state.putSerializable(TAB_TYPE_TAG, type)
            listFragment.arguments = state
            return listFragment
        }
    }
}
