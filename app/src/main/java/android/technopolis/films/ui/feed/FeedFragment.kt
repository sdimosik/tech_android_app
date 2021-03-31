package android.technopolis.films.ui.feed

import android.os.Bundle
import android.technopolis.films.R
import android.technopolis.films.databinding.FragmentFeedBinding
import android.technopolis.films.ui.base.MainActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FeedFragment : Fragment(R.layout.fragment_feed) {

    private var _binding: FragmentFeedBinding? = null;
    private val binding get() = _binding!!

    private lateinit var feedViewModel: FeedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFeedBinding.inflate(
            inflater, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        feedViewModel = (activity as MainActivity).feedViewModel

        feedViewModel.text
            .onEach { text ->
                binding.textFeed.text = text
            }
            .launchIn(lifecycleScope)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}