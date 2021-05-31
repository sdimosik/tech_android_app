package android.technopolis.films.ui.profile.favorite

import android.os.Bundle
import android.technopolis.films.R
import android.technopolis.films.databinding.FragmentFavoriteInProfileBinding
import android.technopolis.films.ui.profile.ProfileViewModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch

class FavoriteInProfileFragment : Fragment(R.layout.fragment_favorite_in_profile) {

    private var binding: FragmentFavoriteInProfileBinding? = null
    private val filmsAdapter: FavoriteInProfileAdapter by lazy { FavoriteInProfileAdapter() }
    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteInProfileBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding?.favoriteRv?.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object :
                DividerItemDecoration(activity, LinearLayout.VERTICAL) {})
            adapter = filmsAdapter
        }

        fetchingData()
    }

    private fun fetchingData() {
        lifecycleScope.launch {
            val list = profileViewModel.observeFavoriteList()
            list.also {
                filmsAdapter.differ.submitList(list.value)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}