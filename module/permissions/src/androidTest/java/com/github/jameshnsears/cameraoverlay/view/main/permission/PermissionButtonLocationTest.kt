package com.github.jameshnsears.cameraoverlay.view.main.permission

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.jameshnsears.cameraoverlay.common.EmulatorCompatibilityHelper
import com.github.jameshnsears.cameraoverlay.permissions.BuildConfig
import com.github.jameshnsears.cameraoverlay.permissions.R
import com.github.jameshnsears.cameraoverlay.viewmodel.permission.ViewModelPermission
import junit.framework.TestCase.fail
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
class PermissionButtonLocationTest : PermissionButtonTest() {
    private lateinit var buttonTextLocation: String

    @Before
    fun before() {
        composeTestRule.setContent {
            PermissionButtonLocation(ViewModelPermission(composeTestRule.activity.application))
        }

        buttonTextLocation = context.resources.getString(R.string.permissions_location)
    }

    @Test
    fun allow() {
        if (!EmulatorCompatibilityHelper.canTestButRunInEmulatorQ()) {
            fail()
        }

        if (BuildConfig.GITHUB_ACTION) {
            return
        }

        composeTestRule.onNodeWithText(buttonTextLocation).performClick()

        denyPermissionInDialog()

        composeTestRule.onNodeWithText(buttonTextLocation).assertIsEnabled()

        composeTestRule.onNodeWithText(buttonTextLocation).performClick()

        pressButton(text = "Permissions")

        pressButton(text = "Location")

        grantPermissionInDialogLocation()

        pressBack()
        pressBack()
        pressBack()

        composeTestRule.onNodeWithText(buttonTextLocation).assertIsNotEnabled()
    }
}
