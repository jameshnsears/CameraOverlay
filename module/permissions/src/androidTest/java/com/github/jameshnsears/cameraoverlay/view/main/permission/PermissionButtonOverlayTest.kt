package com.github.jameshnsears.cameraoverlay.view.main.permission

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.jameshnsears.cameraoverlay.common.EmulatorCompatibilityHelper
import com.github.jameshnsears.cameraoverlay.permissions.BuildConfig
import com.github.jameshnsears.cameraoverlay.permissions.R
import com.github.jameshnsears.cameraoverlay.viewmodel.permission.ViewModelPermission
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PermissionButtonOverlayTest : PermissionButtonHelper() {
    private var buttonTextOverlay: String = context.resources.getString(R.string.permissions_display_overlay)

    @Test
    fun allow() {
        if (!EmulatorCompatibilityHelper.canTestButRunInEmulatorQ()) {
            TestCase.fail()
        }

        if (BuildConfig.GITHUB_ACTION) {
            return
        }

        composeTestRule.setContent {
            PermissionButtonOverlay(ViewModelPermission(composeTestRule.activity.application))
        }

        composeTestRule.onNodeWithText(buttonTextOverlay).performClick()
        pressBack()
        composeTestRule.onNodeWithText(buttonTextOverlay).assertIsEnabled()

        composeTestRule.onNodeWithText(buttonTextOverlay).performClick()

        grantDrawOverOtherApps()
    }
}
