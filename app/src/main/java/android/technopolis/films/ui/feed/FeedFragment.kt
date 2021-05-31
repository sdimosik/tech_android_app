package android.technopolis.films.ui.feed

import android.os.Bundle
import android.technopolis.films.R
import android.technopolis.films.databinding.FragmentFeedBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class FeedFragment : Fragment(R.layout.fragment_feed) {

    private var binding: FragmentFeedBinding? = null
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}