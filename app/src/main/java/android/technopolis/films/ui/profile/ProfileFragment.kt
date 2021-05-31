package android.technopolis.films.ui.profile

import android.os.Bundle
import android.technopolis.films.R
import android.technopolis.films.databinding.FragmentProfileBinding
import android.technopolis.films.ui.base.MainActivity
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
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var binding: FragmentProfileBinding? = null

    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ViewPagerAdapter(childFragmentManager, 0)
        val viewPager = binding?.viewPager!!
        viewPager.adapter = adapter

        val tabLayout = binding?.fragmentProfileTabLayout!!
        tabLayout.setupWithViewPager(viewPager)
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
}