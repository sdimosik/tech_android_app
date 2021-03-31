package android.technopolis.films.ui.history

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

class HistoryFragment : Fragment(R.layout.fragment_history) {

    private var _binding: FragmentFeedBinding? = null;
    private val binding get() = _binding!!

    private lateinit var historyViewModel: HistoryViewModel

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

        historyViewModel = (activity as MainActivity).historyViewModel

        historyViewModel.text
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