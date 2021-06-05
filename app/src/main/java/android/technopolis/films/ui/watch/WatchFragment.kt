package android.technopolis.films.ui.watch

import android.os.Build
import android.os.Bundle
import android.technopolis.films.databinding.FragmentWatchBinding
import android.technopolis.films.ui.watch.tabs.ListFragment
import android.technopolis.films.ui.watch.tabs.WatchTabLayoutAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.children
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class WatchFragment : Fragment() {
    private var binding: FragmentWatchBinding? = null

    private val watchViewModel: WatchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentWatchBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = WatchTabLayoutAdapter(childFragmentManager, 0)

        val viewPager = binding?.fragmentWatchViewPager!!

        viewPager.adapter = adapter
        viewPager.currentItem = (watchViewModel.getTabProperty(ARGS_TAG) ?: 0) as Int

        viewPager.setOnScrollChangeListener(View.OnScrollChangeListener(onScrollChangeListener))

        val tabLayout = binding?.fragmentWatchTabLayout!!
        tabLayout.addOnTabSelectedListener(OnTabSelectedListener())
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private val onScrollChangeListener: (View, Int, Int, Int, Int) -> Unit = { _, _, _, _, _ ->
        val viewPager = binding?.fragmentWatchViewPager!!
        val state = Bundle()
        state.putInt(ARGS_TAG, viewPager.currentItem)

        watchViewModel.onPageChanged(state)
    }

    inner class OnTabSelectedListener : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            return
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            return
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
            //todo *внимание, костыль*
            // это, наверное, должно работать по-другому
            val index = tab!!.position
            val layout = binding?.fragmentWatchViewPager?.get(index) as SwipeRefreshLayout
            for (i in 0 until layout.childCount) {
                val view = layout[i]
                if (view is RecyclerView) {
                    view.smoothScrollToPosition(0)
                }
            }
        }
    }

    companion object {
        const val ARGS_TAG = "CURRENT_ITEM"
    }
}