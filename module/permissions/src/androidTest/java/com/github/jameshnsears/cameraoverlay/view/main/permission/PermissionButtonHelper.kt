package com.github.jameshnsears.cameraoverlay.view.main.permission

import android.content.Context
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.github.jameshnsears.cameraoverlay.view.MainActivityTest
import org.junit.Rule

open class PermissionButtonHelper {
    protected val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    protected open fun waitForButtonToDisplay(displayOverlayButtonText: String) {
        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithText(displayOverlayButtonText).fetchSemanticsNodes(false)
                .isNotEmpty()
        }
    }

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivityTest>()
}
