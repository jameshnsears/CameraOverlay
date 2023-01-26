package com.github.jameshnsears.cameraoverlay.view.main.permission

import android.provider.Settings
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.jameshnsears.cameraoverlay.common.EmulatorCompatibilityHelper
import com.github.jameshnsears.cameraoverlay.permissions.BuildConfig
import com.github.jameshnsears.cameraoverlay.permissions.R
import com.github.jameshnsears.cameraoverlay.viewmodel.permission.ViewModelPermission
import junit.framework.TestCase
import junit.framework.TestCase.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PermissionButtonOverlayTest : PermissionButtonHelper() {
    private lateinit var buttonTextOverlay: String

    @Before
    fun before() {
        buttonTextOverlay =
            context.resources.getString(R.string.permissions_display_overlay)
    }

    @Test
    // be default running in IDE grants this permission!
    fun allow() {
        if (!EmulatorCompatibilityHelper.canTestButRunInEmulatorQ()) {
            TestCase.fail()
        }

        if (BuildConfig.GITHUB_ACTION) {
            return
        }

        fail("wip")

        /*
        press "Create overlay"
        press Back

        press "Create overlay"
        press Back

        press "Create overlay"

        press "Allow display over other apps"
        press Back

        assert "Create overlay" button disabled

         */

        if (Settings.canDrawOverlays(context)) {
            composeTestRule.setContent {
                PermissionButtonOverlay(ViewModelPermission(composeTestRule.activity.application))
            }

            waitForButtonToDisplay(buttonTextOverlay)

            composeTestRule.onNodeWithText(buttonTextOverlay).assertIsNotEnabled()
        }
    }

//    @Test
//    fun deny() {
//        if (!EmulatorCompatibilityHelper.canTestButRunInEmulatorQ()) {
//            TestCase.fail()
//        }
//
//        if (BuildConfig.GITHUB_ACTION) {
//            return
//        }
//
//        // enable the button
//        val viewModelMainScreen = ViewModelPermission(composeTestRule.activity.application)
//        val permissionMediatorImplSpy =
//            spyk(PermissionMediatorImpl(composeTestRule.activity.application))
//        every { permissionMediatorImplSpy.permissionAllowed(PermissionArea.OVERLAY) } returns false
//        viewModelMainScreen.permissionMediator = permissionMediatorImplSpy
//
//        composeTestRule.setContent {
//            PermissionButtonOverlay(viewModelMainScreen)
//        }
//
//        waitForButtonToDisplay(buttonTextOverlay)
//
//        composeTestRule.onNodeWithText(buttonTextOverlay).assertIsEnabled()
//
//        unmockkAll()
//
//        composeTestRule.onNodeWithText(buttonTextOverlay).performClick()
//
//        pressDrawOverOtherApps()
//
//        waitForButtonToDisplay(buttonTextOverlay)
//
//        composeTestRule.onNodeWithText(buttonTextOverlay).assertIsEnabled()
//    }
}
