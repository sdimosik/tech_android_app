package android.technopolis.films.ui.watch

import android.os.Build
import android.os.Bundle
import android.technopolis.films.databinding.FragmentWatchBinding
import android.technopolis.films.ui.watch.tabs.WatchTabLayoutAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class WatchFragment : Fragment() {
    private var binding: FragmentWatchBinding? = null

    private val watchViewModel: WatchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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
        tabLayout.setupWithViewPager(viewPager)
    }

    private val onScrollChangeListener: (View, Int, Int, Int, Int) -> Unit = { _, _, _, _, _ ->
        val viewPager = binding?.fragmentWatchViewPager!!
        val state = Bundle()
        state.putInt(ARGS_TAG, viewPager.currentItem)

        watchViewModel.onPageChanged(state)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val ARGS_TAG = "CURRENT_ITEM"
    }
}