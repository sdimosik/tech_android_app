package android.technopolis.films.ui.watch.tabs

import android.os.Bundle
import android.technopolis.films.R
import android.technopolis.films.databinding.FragmentWatchBinding
import android.technopolis.films.ui.watch.rvMediaHolder.Media
import android.technopolis.films.ui.watch.rvMediaHolder.MediaAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListFragment() : Fragment() {
    private var _binding: FragmentWatchBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvMediaList: RecyclerView

    var tabType: TabType = TabType.FILM

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

        rvMediaList = binding.root.findViewById(R.id.media_list_recycler_view)
        val adapter = MediaAdapter(
            listOf(
                Media(),
                Media(),
                Media(),
                Media(),
                Media(),
                Media(),
                Media(),
            )
        )
        rvMediaList.adapter = adapter
        rvMediaList.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val ARG_LIST_TYPE = "LIST_TYPE"

        fun newInstance(listType: TabType): ListFragment {
            val fragment = ListFragment()
            val args = Bundle()
            args.putSerializable(ARG_LIST_TYPE, listType)
            fragment.arguments = args
            return fragment
        }
    }
}