package android.technopolis.films.fragments

import android.os.Bundle
import android.technopolis.films.R
import android.technopolis.films.viewmodels.WatchViewModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class WatchFragment : Fragment() {

    private lateinit var mWatchViewModel: WatchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mWatchViewModel =
            ViewModelProvider(this).get(WatchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_watch, container, false)
        val textView: TextView = root.findViewById(R.id.text_watch)
        mWatchViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}