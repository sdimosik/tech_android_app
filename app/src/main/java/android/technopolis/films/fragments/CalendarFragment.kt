package android.technopolis.films.fragments

import android.os.Bundle
import android.technopolis.films.R
import android.technopolis.films.viewmodels.CalendarViewModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class CalendarFragment : Fragment() {
    private lateinit var mCalendarViewModel: CalendarViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mCalendarViewModel =
            ViewModelProvider(this).get(CalendarViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_calendar, container, false)
        val textView: TextView = root.findViewById(R.id.text_calendar)
        mCalendarViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}