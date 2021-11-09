package android.technopolis.films

import android.technopolis.films.screen.FeedPage
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
                FeedPage.watchButton {
                    isVisible()
                    click()
                }
            }

            step("Check empty list note") {
                WatchPage {
                    films {
                        isVisible()
                    }
                }
            }
        }
}