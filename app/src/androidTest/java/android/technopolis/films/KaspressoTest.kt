package android.technopolis.films

import android.technopolis.films.screen.MainScreen
import android.technopolis.films.screen.WatchPage
import android.technopolis.films.ui.base.MainActivity
import androidx.test.rule.ActivityTestRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test

class KaspressoTest : TestCase() {
    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, true, false)

    @Test
    fun test() =
        before {
            device.network.disable()
            activityTestRule.launchActivity(null)
        }.after {
            device.network.enable()
            activityTestRule.finishActivity()
        }.run {
            step("Go to Watch Page") {
                MainScreen.watchButton {
                    isVisible()
                    click()
                }
            }

            step("Check empty list note") {
                WatchPage {
                    text {
                        isVisible()
                    }
                }
            }
        }
}