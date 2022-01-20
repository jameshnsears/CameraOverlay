package com.github.jameshnsears.cameraoverlay.view.main.permission

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import com.github.jameshnsears.cameraoverlay.BuildConfig
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.viewmodel.main.ViewModelMainScreen
import org.junit.Before
import org.junit.Test

class PermissionButtonLocationTest : PermissionButtonTest() {
    private lateinit var buttonTextLocation: String

    @Before
    fun before() {
        composeTestRule.setContent {
            PermissionButtonLocation(ViewModelMainScreen(composeTestRule.activity.application))
        }

        buttonTextLocation = context.resources.getString(R.string.main_show_distance)
    }

    @Test
    fun allow() {
        if (BuildConfig.GITHUB_ACTION) {
            return
        }

        composeTestRule.onNodeWithText(buttonTextLocation).performClick()

        grantPermissionInDialogLocation()

        composeTestRule.onRoot(useUnmergedTree = true).printToLog("CameraOverlay") // logcat

        waitForButtonToDisplay(buttonTextLocation)

        composeTestRule.onNodeWithText(buttonTextLocation).assertIsNotEnabled()
    }

    @Test
    fun deny() {
        if (BuildConfig.GITHUB_ACTION) {
            return
        }

        denyPermissionDialog(buttonTextLocation)

        denyPermissionAppInfo(buttonTextLocation, "Location")
    }
}
