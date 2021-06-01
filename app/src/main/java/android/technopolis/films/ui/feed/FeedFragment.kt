package android.technopolis.films.ui.feed

import android.os.Bundle
import android.technopolis.films.R
import android.technopolis.films.databinding.FragmentFeedBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import android.widget.SearchView
import android.widget.Toast

class FeedFragment : Fragment(R.layout.fragment_feed) {

    private var binding: FragmentFeedBinding? = null
    private val feedAdapter: FeedAdapter by lazy { FeedAdapter() }
    private val feedViewModel: FeedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFeedBinding.inflate(
            inflater, container, false
        )

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        val searchView = binding?.searchView!!
        searchView.setOnClickListener {
            Toast.makeText(activity, "CLICK", Toast.LENGTH_SHORT).show();
        }
    }

    private fun setUpRecyclerView() {
        binding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object :
                DividerItemDecoration(activity, LinearLayout.VERTICAL) {})
            adapter = feedAdapter
        }

        fetchingData()
    }

    private fun fetchingData() {
        lifecycleScope.launch {
            val list = feedViewModel.observeFavoriteList()
            list.also {
                feedAdapter.differ.submitList(list.value)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
