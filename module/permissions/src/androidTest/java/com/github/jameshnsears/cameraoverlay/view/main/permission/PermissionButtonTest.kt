package com.github.jameshnsears.cameraoverlay.view.main.permission

import android.content.Context
import android.os.Build
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.github.jameshnsears.cameraoverlay.view.MainActivityTest
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class PermissionButtonTest {
    protected val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    protected fun waitForButtonToDisplay(displayOverlayButtonText: String) {
        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithText(displayOverlayButtonText).fetchSemanticsNodes(false)
                .isNotEmpty()
        }
    }

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivityTest>()

    fun denyPermissionDialog(buttonText: String) {
        composeTestRule.onNodeWithText(buttonText).performClick()
        denyPermissionInDialog()
        composeTestRule.onNodeWithText(buttonText).assertIsEnabled()
    }

    fun denyPermissionAppInfo(buttonText: String, buttonTestAppInfo: String) {
        composeTestRule.onNodeWithText(buttonText).performClick()

        pressButton(text = "Permissions")

        when (Build.VERSION.SDK_INT) {
            25 -> {
                pressBack()
                pressBack()
            }
            29 -> {
                pressButton(text = buttonTestAppInfo)
                pressButton(text = "Deny")
                pressBack()
                pressBack()
                pressBack()
            }
            30 -> {
                pressButton(text = buttonTestAppInfo)
                pressButton(text = "Deny")
                pressBack()
                pressBack()
                pressBack()
            }
            31 -> {
                pressButton(text = buttonTestAppInfo)
                pressButton(text = "Don’t allow")
                pressBack()
                pressBack()
                pressBack()
            }
        }

        waitForButtonToDisplay(buttonText)

        composeTestRule.onNodeWithText(buttonText).assertIsEnabled()
    }
}
