package android.technopolis.films.ui.watch

import android.os.Bundle
import android.technopolis.films.R
import android.technopolis.films.databinding.FragmentWatchBinding
import android.technopolis.films.repository.Repository
import android.technopolis.films.ui.common.BaseFragment
import android.technopolis.films.ui.watch.tabs.WatchTabLayoutAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class WatchFragment : BaseFragment(R.layout.fragment_watch) {

    private var binding: FragmentWatchBinding? = null
    private lateinit var repository: Repository

    private lateinit var viewModel: WatchViewModel

    private var position: String = "POSITION"
    private var scrollDistance: String = "SCROLL_DISTANCE"
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWatchBinding.inflate(inflater, container, false)
        repository = mainActivity.repository

        viewModel = ViewModelProvider(
            this,
            WatchViewModelFactory(repository)
        ).get(WatchViewModel::class.java)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = WatchTabLayoutAdapter(childFragmentManager, 0)

        viewPager = binding?.fragmentWatchViewPager!!

        viewPager.adapter = adapter

        if (savedInstanceState != null) {
            restoreState(savedInstanceState)
        }

        tabLayout = binding?.fragmentWatchTabLayout!!
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        safeState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        if (savedInstanceState != null) {
            restoreState(savedInstanceState)
        }
    }

    override fun onStop() {
        super.onStop()
        safeState(null)
    }

    private fun safeState(outState: Bundle?) {
        (outState ?: arguments)?.run {
            putInt(position, viewPager.currentItem)
            putInt(scrollDistance, tabLayout.scrollY)
        }
    }

    private fun restoreState(savedInstanceState: Bundle) {
        viewPager.currentItem = savedInstanceState.getInt(position)
        tabLayout.setScrollPosition(
            savedInstanceState.getInt(scrollDistance),
            0f,
            true
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}