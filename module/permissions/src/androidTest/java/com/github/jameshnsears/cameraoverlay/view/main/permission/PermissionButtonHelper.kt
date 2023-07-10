package com.github.jameshnsears.cameraoverlay.view.main.permission

import android.content.Context
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.github.jameshnsears.cameraoverlay.common.EmulatorCompatibilityHelper
import com.github.jameshnsears.cameraoverlay.view.MainActivityTestHarness
import junit.framework.TestCase.fail
import org.junit.Before
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
    val composeTestRule = createAndroidComposeRule<MainActivityTestHarness>()

    @Before
    fun setUp() {
        if (!EmulatorCompatibilityHelper.canEmulatorSupportTest()) fail()
    }
}
