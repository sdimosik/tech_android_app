package android.technopolis.films.ui.watch.tabs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class WatchTabLayoutAdapter(
    fm: FragmentManager,
    behavior: Int
) :
    FragmentPagerAdapter(fm, behavior) {

    private val names = arrayOf(
        "Films",
        "Shows"
//        R.string.watch__tab_layout__film_tab_name,
//        R.string.watch__tab_layout__show_tab_name
    )

    override fun getItem(position: Int): Fragment {
        val obj = ListFragment()
        return when (position) {
            0 -> ListFragment.newInstance(TabType.FILM)
            else -> ListFragment.newInstance(TabType.SHOW)
        }
    }

    override fun getCount(): Int {
        return names.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return names[position]
    }
}