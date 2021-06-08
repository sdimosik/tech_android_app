package android.technopolis.films.ui.calendar

import android.os.Bundle
import android.technopolis.films.R
import android.technopolis.films.databinding.FragmentCalendarBinding
import android.technopolis.films.ui.base.MainActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null;
    private val binding get() = _binding!!

    private val calendarViewModel: CalendarViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentCalendarBinding.inflate(
            inflater, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendarViewModel.text
            .onEach { text ->
                binding.textCalendar.text = text
            }
            .launchIn(lifecycleScope)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}