package com.github.jameshnsears.cameraoverlay.view.main.permission

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import com.github.jameshnsears.cameraoverlay.permissions.BuildConfig
import com.github.jameshnsears.cameraoverlay.permissions.R
import com.github.jameshnsears.cameraoverlay.viewmodel.permission.ViewModelPermission
import org.junit.Before
import org.junit.Test

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
        if (BuildConfig.GITHUB_ACTION) {
            return
        }

        composeTestRule.onNodeWithText(buttonTextStorage).performClick()

        grantPermissionInDialogStorage()

        // logcat
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("CameraOverlay")

        waitForButtonToDisplay(buttonTextStorage)

        composeTestRule.onNodeWithText(buttonTextStorage).assertIsNotEnabled()
    }

    @Test
    fun deny() {
        if (BuildConfig.GITHUB_ACTION) {
            return
        }

        denyPermissionDialog(buttonTextStorage)

        denyPermissionAppInfo(
            buttonTextStorage,
            context.resources.getString(R.string.permissions_files_and_media)
        )
    }
}
