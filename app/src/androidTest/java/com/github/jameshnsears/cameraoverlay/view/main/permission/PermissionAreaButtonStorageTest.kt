package com.github.jameshnsears.cameraoverlay.view.main.permission

import android.os.Build
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.github.jameshnsears.cameraoverlay.BuildConfig
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.viewmodel.main.ViewModelMainScreen
import org.junit.Before
import org.junit.Test

class PermissionAreaButtonStorageTest : PermissionButtonTest() {
    private lateinit var accessPhotosButtonText: String

    @Before
    fun before() {
        if (BuildConfig.GITHUB_ACTION) {
            return
        }

        composeTestRule.setContent {
            PermissionButtonStorage(ViewModelMainScreen(composeTestRule.activity.application))
        }

        accessPhotosButtonText = context.resources.getString(R.string.main_access_photos)
    }

    @Test
    fun allow() {
        allowPermission(accessPhotosButtonText)
    }

    @Test
    fun denyViaAppInfoDialog() {
        if (BuildConfig.GITHUB_ACTION) {
            return
        }

        denyPermission(accessPhotosButtonText)

        denyPermission(accessPhotosButtonText)

        composeTestRule.onNodeWithText(accessPhotosButtonText).performClick()

        pressButton(text = "Permissions")

        when (Build.VERSION.SDK_INT) {
            in 25..28 -> pressButton(text = "Storage")
            29 -> {
                pressButton(text = "Storage")
                pressButton(text = "Allow")
                pressBack()
            }
            in 30..31 -> {
                pressButton(text = "Files and media")
                pressButton(text = "Allow access to media only")
                pressBack()
            }
        }

        pressBack()
        pressBack()

        waitForButtonToDisplay(accessPhotosButtonText)

        composeTestRule.onNodeWithText(accessPhotosButtonText).assertIsNotEnabled()
    }
}
