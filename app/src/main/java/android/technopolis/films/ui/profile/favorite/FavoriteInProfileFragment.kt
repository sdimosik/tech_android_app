package android.technopolis.films.ui.profile.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.technopolis.films.R
import android.technopolis.films.databinding.FragmentFavoriteInProfileBinding
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

class FavoriteInProfileFragment : Fragment(R.layout.fragment_favorite_in_profile) {

    private var _binding: FragmentFavoriteInProfileBinding? = null;
    private val binding get() = _binding!!
    private lateinit var filmsAdapter: FavoriteInProfileAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteInProfileBinding.inflate(
            inflater, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        filmsAdapter = FavoriteInProfileAdapter()

        binding.favoriteRv.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object :
                DividerItemDecoration(activity, LinearLayout.VERTICAL) {})
            adapter = filmsAdapter
        }

        fetchingData()
    }

    private fun fetchingData() {
        val list: List<FavoriteInProfileItemModel> = listOf(
            FavoriteInProfileItemModel(1),
            FavoriteInProfileItemModel(2),
            FavoriteInProfileItemModel(3),
            FavoriteInProfileItemModel(4),
            FavoriteInProfileItemModel(5),
            FavoriteInProfileItemModel(6),
            FavoriteInProfileItemModel(7),
            FavoriteInProfileItemModel(8),
            FavoriteInProfileItemModel(9),
            FavoriteInProfileItemModel(10),
            FavoriteInProfileItemModel(11),
            FavoriteInProfileItemModel(12),
            FavoriteInProfileItemModel(13),
            FavoriteInProfileItemModel(14),
            FavoriteInProfileItemModel(15),
            FavoriteInProfileItemModel(16),
            FavoriteInProfileItemModel(17),
            FavoriteInProfileItemModel(18))

        filmsAdapter.differ.submitList(list)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}