package android.technopolis.films.ui.watch.tabs

import android.os.Bundle
import android.technopolis.films.R
import android.technopolis.films.databinding.FragmentListBinding
import android.technopolis.films.ui.watch.rvMediaHolder.Media
import android.technopolis.films.ui.watch.rvMediaHolder.MediaAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

class ListFragment(var tabType: TabType) : Fragment(R.layout.fragment_list) {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        with(binding.mediaListRecyclerView) {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object : DividerItemDecoration(activity, HORIZONTAL) {})
            adapter = MediaAdapter(fetchData())
        }
    }

    private fun fetchData(): List<Media> {
        return listOf(
            Media(),
            Media(),
            Media(),
            Media(),
            Media(),
            Media(),
            Media(),
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}