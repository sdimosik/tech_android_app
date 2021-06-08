package android.technopolis.films.ui.watch.tabs

import android.content.Context
import android.technopolis.films.api.trakt.model.media.MediaType
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class WatchTabLayoutAdapter(
    fm: FragmentManager,
    behavior: Int,
    private val context: Context,
) : FragmentPagerAdapter(fm, behavior) {

    private val names = MediaType.values()

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ListFragment.newInstance(MediaType.movies)
            else -> ListFragment.newInstance(MediaType.shows)
        }
    }

    override fun getCount(): Int {
        return ListFragment.TITLES.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(ListFragment.TITLES[position])
    }
}