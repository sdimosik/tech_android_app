package android.technopolis.films.ui.profile

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.technopolis.films.R
import android.technopolis.films.databinding.FragmentProfileBinding
import android.technopolis.films.ui.profile.favorite.FavoriteInProfileFragment
import android.technopolis.films.ui.profile.info.InfoInProfileFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProfileFragment : Fragment(R.layout.fragment_profile),
    SwipeRefreshLayout.OnRefreshListener {

    private var binding: FragmentProfileBinding? = null
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private lateinit var swipeLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        swipeLayout = binding?.swipeContainer!!
        swipeLayout.setOnRefreshListener(this)
        swipeLayout.setColorSchemeColors(Color.BLUE)

        swipeLayout.post {
            if (!profileViewModel.isLoadProfile()) {
                swipeLayout.isRefreshing = true
                profileViewModel.updateUserSetting()
            }
        }

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeDataCallBack()

        /*val adapter = ViewPagerAdapter(childFragmentManager, 0)
        val viewPager = binding?.viewPager!!
        viewPager.adapter = adapter
        viewPager.currentItem = profileViewModel.getStateViewPager()!!

        val tabLayout = binding?.fragmentProfileTabLayout!!
        tabLayout.setupWithViewPager(viewPager)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                profileViewModel.saveStateViewPager(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })*/
    }

    @SuppressLint("SetTextI18n")
    private fun subscribeDataCallBack() {
        profileViewModel.getUserSetting().onEach {
            binding?.nameProfile?.text = it.user?.username
            binding?.someStat1?.text = it.user?.name
            binding?.location?.text = it.user?.location

            binding?.facebook?.setTextColor(if (it.connections?.facebook!!) Color.GREEN else Color.RED)
            binding?.twitter?.setTextColor(if (it.connections?.twitter!!) Color.GREEN else Color.RED)
            binding?.google?.setTextColor(if (it.connections?.google!!) Color.GREEN else Color.RED)
            binding?.tumblr?.setTextColor(if (it.connections?.tumblr!!) Color.GREEN else Color.RED)
            binding?.medium?.setTextColor(if (it.connections?.medium!!) Color.GREEN else Color.RED)
            binding?.slack?.setTextColor(if (it.connections?.slack!!) Color.GREEN else Color.RED)
            binding?.apple?.setTextColor(if (it.connections?.apple!!) Color.GREEN else Color.RED)

            binding?.someStat2?.text = "Watched: " + it.sharingText?.watched
            binding?.someStat3?.text = "Rated: " + it.sharingText?.rated

            swipeLayout.isRefreshing = false
        }.launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    class ViewPagerAdapter(
        fm: FragmentManager,
        behavior: Int
    ) : FragmentPagerAdapter(fm, behavior) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> FavoriteInProfileFragment()
                else -> InfoInProfileFragment()
            }
        }

        override fun getPageTitle(position: Int): CharSequence {
            return when (position) {
                0 -> "FAVORITE"
                else -> "INFO"
            }
        }

        override fun getCount(): Int {
            return INFO_TITLES.size
        }
    }

    companion object {
        private val INFO_TITLES = arrayOf(
            R.layout.fragment_favorite_in_profile,
            R.layout.fragment_info_in_profile
        )
    }

    override fun onRefresh() {
        swipeLayout.isRefreshing = true
        profileViewModel.updateUserSetting()
    }
}
