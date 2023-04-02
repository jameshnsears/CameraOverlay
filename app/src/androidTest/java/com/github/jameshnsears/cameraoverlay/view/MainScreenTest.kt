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
        launch(Dispatchers.IO) {
            composeTestRule.onNodeWithText(
                context.resources.getString(R.string.select_photo)
            )
                .performScrollTo()

            if (!BuildConfig.GITHUB_ACTION) {
                takeScreenshot(composeTestRule)

                try {
                    assertTrue(
                        getActualScreenshot()!!.sameAs(
                            getExpectedScreenshot("View/e6330-mainScreen.bmp")
                        )
                    )
                } catch (e: AssertionFailedError) {
                    assertTrue(
                        getActualScreenshot()!!.sameAs(
                            getExpectedScreenshot("View/lenovo-mainScreen.bmp")
                        )
                    )
                }
            }
        }.join()
    }

    @Test
    fun permissionScreen() = runTest {
        launch(Dispatchers.IO) {
            composeTestRule.onNodeWithContentDescription(
                "?"
            ).performClick()

            if (!BuildConfig.GITHUB_ACTION) {
                takeScreenshot(composeTestRule)
                try {
                    assertTrue(
                        getActualScreenshot()!!.sameAs(
                            getExpectedScreenshot("View/e6330-permissionScreen.bmp")
                        )
                    )
                } catch (e: AssertionFailedError) {
                    assertTrue(
                        getActualScreenshot()!!.sameAs(
                            getExpectedScreenshot("View/lenovo-permissionScreen.bmp")
                        )
                    )
                }
            }
        }.join()
    }
}
