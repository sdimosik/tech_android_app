package android.technopolis.films.ui.common

import android.technopolis.films.ui.base.MainActivity
import androidx.fragment.app.Fragment

abstract class BaseFragment(fragment: Int) : Fragment(fragment) {
    protected val mainActivity get() = activity as MainActivity
}