package com.github.jameshnsears.cameraoverlay.view.main.permission

import android.provider.Settings
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onNodeWithText
import com.github.jameshnsears.cameraoverlay.BuildConfig
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.viewmodel.main.ViewModelMainScreen
import io.mockk.every
import io.mockk.spyk
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class PermissionAreaButtonDisplayOverlayTest : PermissionButtonTest() {
    private lateinit var displayOverlayButtonText: String

    @Before
    fun setup() {
        displayOverlayButtonText =
            context.resources.getString(R.string.main_display_overlay)
    }

    @Test
    fun byDefaultEmulatorAllowsPermission() {
        if (BuildConfig.GITHUB_ACTION) {
            return
        }

        assertTrue(Settings.canDrawOverlays(context))

        composeTestRule.setContent {
            PermissionButtonDisplayOverlay(ViewModelMainScreen(composeTestRule.activity.application))
        }

        waitForButtonToDisplay(displayOverlayButtonText)

        composeTestRule.onNodeWithText(displayOverlayButtonText).assertIsNotEnabled()
    }

    @Test
    fun permissionNotGranted() {
        if (BuildConfig.GITHUB_ACTION) {
            return
        }

        // pretend permission not been granted
        val viewModelMainScreenSpy = spyk(ViewModelMainScreen(composeTestRule.activity.application))
        every { viewModelMainScreenSpy.isPermissionAllowedOverlay() } returns false

        composeTestRule.setContent {
            PermissionButtonDisplayOverlay(viewModelMainScreenSpy)
        }

        waitForButtonToDisplay(displayOverlayButtonText)

        composeTestRule.onNodeWithText(displayOverlayButtonText).assertIsEnabled()

//        composeTestRule.onNodeWithText(displayOverlayButtonText).performClick()
//
//        // pretend permission been granted
//        every { viewModelMainScreenSpy.isPermissionGrantedDisplayOverlay() } returns true
//
//        pressDrawOverOtherApps()
//
//        waitForButtonToDisplay(displayOverlayButtonText)
//
//        composeTestRule.onNodeWithText(displayOverlayButtonText).assertIsNotEnabled()
    }
}