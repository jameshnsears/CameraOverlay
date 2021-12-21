package com.github.jameshnsears.cameraoverlay.view.main.permission

import android.os.Build
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import com.github.jameshnsears.cameraoverlay.BuildConfig
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.view.MainActivity
import com.github.jameshnsears.cameraoverlay.viewmodel.MainScreenViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AccessPhotosPermissionButtonTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var accessPhotosButtonText: String

    @Before
    fun before() {
        if (BuildConfig.GITHUB_ACTION) {
            return
        }

        // https://stackoverflow.com/questions/66546962/jetpack-compose-how-do-i-refresh-a-screen-when-app-returns-to-foreground
        // https://issuetracker.google.com/issues/201900572

        composeTestRule.setContent {
            AccessPhotosPermissionButton(MainScreenViewModel(composeTestRule.activity.application))
        }

        val applicationContext = composeTestRule.activity.applicationContext
        accessPhotosButtonText = applicationContext.resources.getString(R.string.main_access_photos)
    }

    @Test
    fun allowPermission() {
        if (BuildConfig.GITHUB_ACTION) {
            return
        }

        composeTestRule.onNodeWithText(accessPhotosButtonText).performClick()

        grantPermissionInDialog()

        composeTestRule.onRoot(useUnmergedTree = true).printToLog("TIMBER") // logcat

        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithText(accessPhotosButtonText).fetchSemanticsNodes(false)
                .isNotEmpty()
        }

        composeTestRule.onNodeWithText(accessPhotosButtonText).assertIsNotEnabled()
    }

    @Test
    fun appInfo() {
        if (BuildConfig.GITHUB_ACTION) {
            return
        }

        denyPermission()

        denyPermission()

        composeTestRule.onNodeWithText(accessPhotosButtonText).performClick()

        pressButton(text = "Permissions")

        when (Build.VERSION.SDK_INT) {
            in 27..28 -> pressButton(text = "Storage")
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

        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithText(accessPhotosButtonText)
                .fetchSemanticsNodes(false).isNotEmpty()
        }

        composeTestRule.onNodeWithText(accessPhotosButtonText).assertIsNotEnabled()
    }

    private fun denyPermission() {
        composeTestRule.onNodeWithText(accessPhotosButtonText).performClick()
        denyPermissionInDialog()
        composeTestRule.onNodeWithText(accessPhotosButtonText).assertIsEnabled()
    }
}
