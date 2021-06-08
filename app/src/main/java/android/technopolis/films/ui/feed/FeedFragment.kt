package android.technopolis.films.ui.feed

import android.content.Context
import android.os.Bundle
import android.technopolis.films.R
import android.technopolis.films.databinding.FragmentFeedBinding
import android.technopolis.films.ui.feed.film.FeedFilmFragment
import android.technopolis.films.ui.feed.show.FeedShowFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.activityViewModels
import androidx.viewpager.widget.ViewPager

class FeedFragment : Fragment() {

    private var binding: FragmentFeedBinding? = null
    private val feedViewModel: FeedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFeedBinding.inflate(
            inflater, container, false
        )

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = context?.let { ViewPagerAdapter(childFragmentManager, 0, feedViewModel, it) }
        val viewPager = binding?.viewPager!!
        viewPager.adapter = adapter
        viewPager.currentItem = feedViewModel.getStateViewPager()!!

        val tabLayout = binding?.tabLayout!!
        tabLayout.setupWithViewPager(viewPager)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                feedViewModel.saveStateViewPager(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })

        // TODO Search View
        binding?.searchView?.visibility = View.GONE
        /*binding?.recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val searchView = binding?.searchView!!
                val animShow = AnimationUtils.loadAnimation(searchView.context, R.anim.view_show);
                val animHide = AnimationUtils.loadAnimation(searchView.context, R.anim.view_hide);
                if (dy > 10 && searchView.visibility == View.VISIBLE) {
                    searchView.visibility = View.GONE
                } else if (dy < -10 && searchView.visibility != View.VISIBLE) {
                    searchView.visibility = View.VISIBLE
                }
            }
        })*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    class ViewPagerAdapter(
        fm: FragmentManager,
        behavior: Int,
        private val viewModel: FeedViewModel,
        private val context: Context
    ) : FragmentPagerAdapter(fm, behavior) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> FeedFilmFragment(viewModel)
                else -> FeedShowFragment(viewModel)
            }
        }

        override fun getPageTitle(position: Int): CharSequence {
            return context.resources.getString(INFO_TITLES[position])
        }

        override fun getCount(): Int {
            return INFO_TITLES.size
        }

        companion object {
            private val INFO_TITLES = arrayOf(
                R.string.feed__tab_layout__film_tab_name,
                R.string.feed__tab_layout__show_tab_name
            )
        }
    }
}
