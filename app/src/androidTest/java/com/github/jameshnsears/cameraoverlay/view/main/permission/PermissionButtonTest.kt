package com.github.jameshnsears.cameraoverlay.view.main.permission

import android.content.Context
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.test.platform.app.InstrumentationRegistry
import com.github.jameshnsears.cameraoverlay.BuildConfig
import com.github.jameshnsears.cameraoverlay.view.MainActivity
import org.junit.Rule

// https://stackoverflow.com/questions/66546962/jetpack-compose-how-do-i-refresh-a-screen-when-app-returns-to-foreground
// https://issuetracker.google.com/issues/201900572
open class PermissionButtonTest {
    protected val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    protected fun waitForButtonToDisplay(displayOverlayButtonText: String) {
        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithText(displayOverlayButtonText).fetchSemanticsNodes(false)
                .isNotEmpty()
        }
    }

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    fun allowPermission(buttonText: String) {
        if (BuildConfig.GITHUB_ACTION) {
            return
        }

        composeTestRule.onNodeWithText(buttonText).performClick()

        grantPermissionInDialog()

        composeTestRule.onRoot(useUnmergedTree = true).printToLog("CameraOverlay") // logcat

        waitForButtonToDisplay(buttonText)

        composeTestRule.onNodeWithText(buttonText).assertIsNotEnabled()
    }

    fun denyPermission(buttonText: String) {
        composeTestRule.onNodeWithText(buttonText).performClick()
        denyPermissionInDialog()
        composeTestRule.onNodeWithText(buttonText).assertIsEnabled()
    }
}
