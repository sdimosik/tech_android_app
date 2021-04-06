package android.technopolis.films.ui.watch

import android.os.Bundle
import android.technopolis.films.R
import android.technopolis.films.databinding.FragmentWatchBinding
import android.technopolis.films.ui.base.MainActivity
import android.technopolis.films.ui.watch.tabs.WatchTabLayoutAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


class WatchFragment : Fragment(R.layout.fragment_watch) {

    private var _binding: FragmentWatchBinding? = null
    private val binding get() = _binding!!

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

        val mainActivity = activity as MainActivity

        val adapter = WatchTabLayoutAdapter(mainActivity.supportFragmentManager, 0)
        val viewPager = mainActivity.findViewById<ViewPager>(R.id.fragment_watch__view_pager)
        viewPager.adapter = adapter

        val tabLayout = mainActivity.findViewById<TabLayout>(R.id.fragment_watch__tab_layout)
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}