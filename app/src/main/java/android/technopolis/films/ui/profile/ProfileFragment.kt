package android.technopolis.films.ui.profile

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.technopolis.films.api.trakt.model.users.settings.UserSettings
import android.technopolis.films.databinding.FragmentProfileBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private var binding: FragmentProfileBinding? = null
    private val viewModel: ProfileViewModel by activityViewModels()
    private lateinit var swipeLayout: SwipeRefreshLayout

    private lateinit var settings: UserSettings

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        swipeLayout = binding?.swipeContainer!!
        swipeLayout.setOnRefreshListener(this)
        swipeLayout.setColorSchemeColors(Color.BLUE)

        swipeLayout.post {
            if (!viewModel.isLoadProfile()) {
                swipeLayout.isRefreshing = true
                viewModel.updateUserSetting()
            }
        }

        viewModel.settings.onEach {
            settings = it
            loadPicture(binding!!.imageProfile, settings.user?.images?.avatar?.full!!)
        }.launchIn(MainScope())

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeDataCallBack()
    }

    @SuppressLint("SetTextI18n")
    private fun subscribeDataCallBack() {
        viewModel.getUserSetting().onEach {
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
        binding = null
    }

    override fun onRefresh() {
        swipeLayout.isRefreshing = true
        viewModel.updateUserSetting()
    }

    companion object{
        fun loadPicture(imageView: ImageView, url: String){
            Picasso.get().load(url).into(imageView)
        }
    }
}
