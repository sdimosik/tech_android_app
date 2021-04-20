package android.technopolis.films.ui.profile.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.technopolis.films.R
import android.technopolis.films.databinding.FragmentFavoriteInProfileBinding
import android.technopolis.films.databinding.FragmentInfoInProfileBinding


class InfoInProfileFragment : Fragment(R.layout.fragment_info_in_profile) {

    private var _binding: FragmentInfoInProfileBinding? = null;
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoInProfileBinding.inflate(
            inflater, container, false
        )

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}