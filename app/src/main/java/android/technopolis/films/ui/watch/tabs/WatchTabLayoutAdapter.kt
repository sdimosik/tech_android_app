package android.technopolis.films.ui.watch.tabs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class WatchTabLayoutAdapter(
    fm: FragmentManager,
    behavior: Int
) : FragmentPagerAdapter(fm, behavior) {

    private val names = TabType.values()

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ListFragment(TabType.FILM)
            else -> ListFragment(TabType.SHOW)
        }
    }

    override fun getCount(): Int {
        return names.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return names[position].toString()
    }

}