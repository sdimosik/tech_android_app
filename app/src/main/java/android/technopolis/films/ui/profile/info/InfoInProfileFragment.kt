package android.technopolis.films.ui.profile.info

import android.os.Bundle
import android.technopolis.films.R
import android.technopolis.films.databinding.FragmentInfoInProfileBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class InfoInProfileFragment : Fragment(R.layout.fragment_info_in_profile) {

    private var binding: FragmentInfoInProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoInProfileBinding.inflate(
            inflater, container, false
        )

        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}