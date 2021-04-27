package android.technopolis.films.ui.watch

import android.os.Bundle
import android.technopolis.films.databinding.FragmentWatchBinding
import android.technopolis.films.ui.watch.tabs.WatchTabLayoutAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class WatchFragment : Fragment() {
    private var binding: FragmentWatchBinding? = null

    private val watchViewModel: WatchViewModel by activityViewModels()

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private val ARGS_TAG = "CURRENT_ITEM"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWatchBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = WatchTabLayoutAdapter(childFragmentManager, 0)

        viewPager = binding?.fragmentWatchViewPager!!

        viewPager.adapter = adapter
        viewPager.currentItem = watchViewModel.tabArgs.getInt(ARGS_TAG)

        tabLayout = binding?.fragmentWatchTabLayout!!
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        watchViewModel.tabArgs.putInt(ARGS_TAG, viewPager.currentItem)
        binding = null
    }
}