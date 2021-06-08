package android.technopolis.films.ui.profile

import android.graphics.Color
import android.os.Bundle
import android.technopolis.films.R
import android.technopolis.films.api.trakt.model.users.settings.UserSettings
import android.technopolis.films.utils.Utils.isOnline
import android.technopolis.films.databinding.FragmentProfileBinding
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProfileFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private var binding: FragmentProfileBinding? = null
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private lateinit var swipeLayout: SwipeRefreshLayout

    private lateinit var settings: UserSettings
    private lateinit var noConnectionToast: Toast

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        swipeLayout = binding?.swipeContainer!!
        swipeLayout.setOnRefreshListener(this)
        swipeLayout.setColorSchemeColors(Color.BLUE)

        noConnectionToast =
            Toast.makeText(activity, getString(R.string.no_connection), Toast.LENGTH_SHORT)
        noConnectionToast.setGravity(Gravity.CENTER, 0, 0)

        swipeLayout.post {
            if (!profileViewModel.isLoadProfile()) {
                if (isOnline(requireContext())) {
                    swipeLayout.isRefreshing = true
                    profileViewModel.updateUserSetting()
                } else {
                    noConnectionToast.show()
                    swipeLayout.isRefreshing = false
                }
            }
        }

        profileViewModel.observeSettings().onEach {
            if(isOnline(requireContext())) {
                loadPicture(binding!!.imageProfile, it.user?.images?.avatar?.full!!)
            }
        }.launchIn(lifecycleScope)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeDataCallBack()
    }

    private fun subscribeDataCallBack() {
        profileViewModel.getUserSetting().onEach {
            binding?.nameProfile?.text = it.user?.username
            binding?.fullName?.text = it.user?.name
            binding?.location?.text = it.user?.location

            binding?.facebook?.setTextColor(if (it.connections?.facebook!!) Color.GREEN else Color.RED)
            binding?.twitter?.setTextColor(if (it.connections?.twitter!!) Color.GREEN else Color.RED)
            binding?.google?.setTextColor(if (it.connections?.google!!) Color.GREEN else Color.RED)
            binding?.tumblr?.setTextColor(if (it.connections?.tumblr!!) Color.GREEN else Color.RED)
            binding?.medium?.setTextColor(if (it.connections?.medium!!) Color.GREEN else Color.RED)
            binding?.slack?.setTextColor(if (it.connections?.slack!!) Color.GREEN else Color.RED)
            binding?.apple?.setTextColor(if (it.connections?.apple!!) Color.GREEN else Color.RED)

            swipeLayout.isRefreshing = false
        }.launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        noConnectionToast.cancel()
        binding = null
    }

    override fun onRefresh() {
        if (!isOnline(requireContext())) {
            swipeLayout.isRefreshing = false
            noConnectionToast.show()
            return
        }
        swipeLayout.isRefreshing = true
        profileViewModel.updateUserSetting()
    }

    companion object {
        fun loadPicture(imageView: ImageView, url: String) {
            Picasso.get().load(url).into(imageView)
        }
    }
}
