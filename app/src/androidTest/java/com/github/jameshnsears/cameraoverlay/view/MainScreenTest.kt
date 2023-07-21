package com.github.jameshnsears.cameraoverlay.view

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.github.jameshnsears.cameraoverlay.BuildConfig
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.utility.ScreenshotTestUtility
import junit.framework.AssertionFailedError
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class MainScreenTest : ScreenshotTestUtility() {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun mainScreen() = runTest {
        var actualScreenshotFilename = "mainScreen.bmp"

        launch(Dispatchers.IO) {
            composeTestRule.onNodeWithText(
                context.resources.getString(R.string.select_photo)
            ).performScrollTo()

            testScreenshot(actualScreenshotFilename,
                "View/$actualScreenshotFilename"
            )
        }.join()
    }

    @Test
    fun permissionScreen() = runTest {
        var actualScreenshotFilename = "permissionScreen.bmp"

        launch(Dispatchers.IO) {
            composeTestRule.onNodeWithContentDescription(
                "?"
            ).performClick()

            testScreenshot(actualScreenshotFilename,
                "View/$actualScreenshotFilename"
            )
        }.join()
    }

    private fun testScreenshot(
        actualScreenshotFilename: String,
        expectedScreenshot: String) {
        if (!BuildConfig.GITHUB_ACTION) {
            takeScreenshot(composeTestRule)

            // just in case there is a problem, to aid manual comparison
            saveScreenshotToInternalStorage(
                "actual-$actualScreenshotFilename",
                getActualScreenshot()!!
            )

            assertTrue(
                getActualScreenshot()!!.sameAs(
                    getExpectedScreenshot(expectedScreenshot)
                )
            )
        }
    }
}
