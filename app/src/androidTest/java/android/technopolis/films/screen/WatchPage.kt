package android.technopolis.films.screen

import android.technopolis.films.R
import android.technopolis.films.ui.watch.WatchFragment
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.text.KTextView

object WatchPage : KScreen<WatchPage>() {
    override val layoutId: Int = R.layout.fragment_list
    override val viewClass: Class<*> = WatchFragment::class.java

    val text = KTextView {
        withId(R.id.media_list_empty_list)
        isFirst()
    }
}