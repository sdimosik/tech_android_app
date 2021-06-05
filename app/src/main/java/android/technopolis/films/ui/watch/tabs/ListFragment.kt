package android.technopolis.films.ui.watch.tabs

import android.os.Bundle
import android.os.Parcelable
import android.technopolis.films.databinding.FragmentListBinding
import android.technopolis.films.ui.watch.WatchViewModel
import android.technopolis.films.ui.watch.rvMediaHolder.MediaAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ListFragment : Fragment() {
    private var binding: FragmentListBinding? = null

    private val viewModel: WatchViewModel by activityViewModels()

    private lateinit var listAdapter: MediaAdapter
    private var tabType: TabType? = null
    private var ARGS_TAG: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabType = arguments?.getSerializable(TAB_TYPE_TAG) as TabType
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

    inner class OnScrollListener : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            viewModel.onPageChanged(getState())
        }
    }

    private var onScrollListener: OnScrollListener? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        binding?.mediaListRecyclerView?.layoutManager?.onRestoreInstanceState(
            viewModel.getTabProperty(ARGS_TAG!!) as Parcelable?
        )

        if (onScrollListener == null) {
            onScrollListener = OnScrollListener()
        }

        binding?.mediaListRecyclerView?.addOnScrollListener(onScrollListener!!)
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
        listAdapter = MediaAdapter()

        val medias = viewModel.medias
        medias.onEach {
            listAdapter.submitList(it)
        }.launchIn(lifecycleScope)

        binding?.let {
            with(it.mediaListRecyclerView) {
                layoutManager = LinearLayoutManager(activity)
                setHasFixedSize(true)
                addItemDecoration(object : DividerItemDecoration(activity, HORIZONTAL) {})
                adapter = listAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        if (onScrollListener != null) {
            binding?.mediaListRecyclerView?.removeOnScrollListener(onScrollListener!!)
            onScrollListener = null
        }
        binding = null
    }

    companion object {
        const val TAB_TYPE_TAG = "TAB_TYPE"

        fun newInstance(type: TabType): ListFragment {
            val listFragment = ListFragment()
            val state = Bundle()
            state.putSerializable(TAB_TYPE_TAG, type)
            listFragment.arguments = state
            return listFragment
        }
    }
}

enum class TabType {
    FILM,
    SHOW
}