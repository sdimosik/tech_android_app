package android.technopolis.films.ui.watch.tabs

import android.os.Bundle
import android.technopolis.films.R
import android.technopolis.films.databinding.FragmentListBinding
import android.technopolis.films.ui.watch.WatchViewModel
import android.technopolis.films.ui.watch.rvMediaHolder.MediaAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

class ListFragment(private val tabType: TabType) : Fragment(R.layout.fragment_list) {
    private var binding: FragmentListBinding? = null

    private val viewModel: WatchViewModel by activityViewModels()

    private lateinit var listAdapter: MediaAdapter
    private var ARGS_TAG = "${tabType}_SCROLL_OFFSET"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        binding?.mediaListRecyclerView!!.offsetChildrenVertical(
            viewModel.tabArgs.getInt(ARGS_TAG, 0)
        )
    }

    private fun setupRecyclerView() {
        listAdapter = MediaAdapter()

        viewModel.medias.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            listAdapter.submitList(it)
        })

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

        viewModel.tabArgs.putInt(
            ARGS_TAG,
            binding?.mediaListRecyclerView!!.computeVerticalScrollOffset()
        )

        binding = null
    }
}

enum class TabType {
    FILM,
    SHOW
}