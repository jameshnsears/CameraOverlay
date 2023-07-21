package com.github.jameshnsears.cameraoverlay.view

import android.Manifest
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.rule.GrantPermissionRule
import com.github.jameshnsears.cameraoverlay.BuildConfig
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.utility.ScreenshotTestUtility
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class MainScreenTest : ScreenshotTestUtility() {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_MEDIA_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.SYSTEM_ALERT_WINDOW
    )

    @Test
    fun mainScreen() = runTest {
        var expectedScreenshot = "MainScreenTest.mainScreen.bmp"

        launch(Dispatchers.IO) {
            composeTestRule.onNodeWithText(
                context.resources.getString(R.string.select_photo)
            ).performScrollTo()

            testScreenshot(
                "View/$expectedScreenshot"
            )
        }.join()
    }

    @Test
    fun permissionScreen() = runTest {
        var expectedScreenshot = "MainScreenTest.permissionScreen.bmp"

        launch(Dispatchers.IO) {
            composeTestRule.onNodeWithContentDescription(
                "?"
            ).performClick()

            testScreenshot(
                "View/$expectedScreenshot"
            )
        }.join()
    }

    private fun testScreenshot(
        expectedScreenshot: String
    ) {
        if (!BuildConfig.GITHUB_ACTION) {
            takeScreenshot(composeTestRule)

            assertTrue(
                getActualScreenshot()!!.sameAs(
                    getExpectedScreenshot(expectedScreenshot)
                )
            )
        }
    }
}
