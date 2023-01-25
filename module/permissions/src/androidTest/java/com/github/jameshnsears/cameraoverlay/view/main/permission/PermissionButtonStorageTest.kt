package com.github.jameshnsears.cameraoverlay.view.main.permission

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
import junit.framework.TestCase
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class PermissionButtonStorageTest : PermissionButtonTest() {
    private lateinit var buttonTextStorage: String

    @Before
    fun before() {
        composeTestRule.setContent {
            PermissionButtonStorage(ViewModelPermission(composeTestRule.activity.application))
        }

        buttonTextStorage = context.resources.getString(R.string.permissions_files_and_media)
    }

    @Test
    fun allow() {
        if (!EmulatorCompatibilityHelper.canTestButRunInEmulatorQ()) {
            TestCase.fail()
        }

        if (BuildConfig.GITHUB_ACTION) {
            return
        }

        /*
        press "Access photos"

        press Deny

        assert Access photos button not disabled

        press "Access photos"

        App Info dialog
        press "Permissions"
        press "Storage"
        press "Allow"
        press Back
        press Back
        press Back

        assert Access photos button is disabled

         */
        composeTestRule.onNodeWithText(buttonTextStorage).performClick()

        grantPermissionInDialogStorage()

        // logcat
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("CameraOverlay")

        waitForButtonToDisplay(buttonTextStorage)

        composeTestRule.onNodeWithText(buttonTextStorage).assertIsNotEnabled()
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
//        denyPermissionDialog(buttonTextStorage)
//
//        denyPermissionAppInfo(
//            buttonTextStorage,
//            "Storage"
//        )
//    }
}
