package android.technopolis.films.ui.watch

import android.os.Bundle
import android.technopolis.films.R
import android.technopolis.films.databinding.FragmentWatchBinding
import android.technopolis.films.ui.base.MainActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class WatchFragment : Fragment(R.layout.fragment_watch) {

    private var _binding: FragmentWatchBinding? = null
    private val binding get() = _binding!!

    private lateinit var watchViewModel: WatchViewModel
    private lateinit var rvMediaList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWatchBinding.inflate(
            inflater, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        watchViewModel = (activity as MainActivity).watchViewModel

        rvMediaList = binding.root.findViewById(R.id.media_list_recycler_view)
        val adapter = MediaAdapter(
            listOf(
                Media(),
                Media(),
                Media(),
                Media(),
                Media(),
            )
        )
        rvMediaList.adapter = adapter
        rvMediaList.layoutManager = LinearLayoutManager(binding.root.context)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}