package com.github.jameshnsears.cameraoverlay.view.main.permission

import android.provider.Settings
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.github.jameshnsears.cameraoverlay.model.permission.PermissionArea
import com.github.jameshnsears.cameraoverlay.model.permission.PermissionMediatorImpl
import com.github.jameshnsears.cameraoverlay.permissions.BuildConfig
import com.github.jameshnsears.cameraoverlay.permissions.R
import com.github.jameshnsears.cameraoverlay.viewmodel.permission.ViewModelPermission
import io.mockk.every
import io.mockk.spyk
import io.mockk.unmockkAll
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class PermissionButtonOverlayTest : PermissionButtonTest() {
    private lateinit var buttonTextOverlay: String

    @Before
    fun before() {
        buttonTextOverlay =
            context.resources.getString(R.string.permissions_display_overlay)
    }

    @Test
    // be default running in IDE grants this permission!
    fun allow() {
        if (BuildConfig.GITHUB_ACTION) {
            return
        }

        if (Settings.canDrawOverlays(context)) {
            composeTestRule.setContent {
                PermissionButtonOverlay(ViewModelPermission(composeTestRule.activity.application))
            }

            waitForButtonToDisplay(buttonTextOverlay)

            composeTestRule.onNodeWithText(buttonTextOverlay).assertIsNotEnabled()
        }
    }

    @Test
    fun deny() {
        if (BuildConfig.GITHUB_ACTION) {
            return
        }

        // enable the button
        val viewModelMainScreen = ViewModelPermission(composeTestRule.activity.application)
        val permissionMediatorImplSpy =
            spyk(PermissionMediatorImpl(composeTestRule.activity.application))
        every { permissionMediatorImplSpy.permissionAllowed(PermissionArea.OVERLAY) } returns false
        viewModelMainScreen.permissionMediator = permissionMediatorImplSpy

        composeTestRule.setContent {
            PermissionButtonOverlay(viewModelMainScreen)
        }

        waitForButtonToDisplay(buttonTextOverlay)

        composeTestRule.onNodeWithText(buttonTextOverlay).assertIsEnabled()

        unmockkAll()

        composeTestRule.onNodeWithText(buttonTextOverlay).performClick()

        // disable the permission
        pressDrawOverOtherApps()

        waitForButtonToDisplay(buttonTextOverlay)

        composeTestRule.onNodeWithText(buttonTextOverlay).assertIsEnabled()
    }
}
