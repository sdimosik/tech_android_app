package android.technopolis.films.screen

import android.technopolis.films.R
import android.technopolis.films.ui.base.MainActivity
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KBaseView
import io.github.kakaocup.kakao.text.KButton

object MainScreen : KScreen<MainScreen>() {

    override val layoutId: Int = R.layout.activity_main
    override val viewClass: Class<*> = MainActivity::class.java

    val feedButton = KButton { withId(R.id.navigation_feed) }
    val watchButton = KButton { withId(R.id.navigation_watch) }
}