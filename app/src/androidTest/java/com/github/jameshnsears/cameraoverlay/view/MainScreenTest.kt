package com.github.jameshnsears.cameraoverlay.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.utility.TestUtility
import java.io.File
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class MainScreenTest : TestUtility() {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun mainScreen() = runTest {
        launch(Dispatchers.IO) {
            composeTestRule.onNodeWithText(
                context.resources.getString(R.string.select_photo)
            )
                .performScrollTo()

            takeScreenshot()

            assertTrue(
                getActualScreenshot()!!.sameAs(
                    getExpectedScreenshot("View/MainScreen.bmp")
                )
            )
        }.join()
    }

    @Test
    fun permissionScreen() = runTest {
        launch(Dispatchers.IO) {
            composeTestRule.onNodeWithContentDescription(
                "?"
            ).performClick()

            takeScreenshot()

            assertTrue(
                getActualScreenshot()!!.sameAs(
                    getExpectedScreenshot("View/PermissionScreen.bmp")
                )
            )
        }.join()
    }

    private fun getExpectedScreenshot(fileName: String): Bitmap? {
        return BitmapFactory.decodeStream(
            this::class.java.classLoader.getResourceAsStream(
                fileName
            )
        )
    }

    private fun getActualScreenshot(): Bitmap? {
        return BitmapFactory.decodeFile(
            File(
                context.filesDir,
                getTestName()
            ).absolutePath
        )
    }

    private fun takeScreenshot() {
        saveScreenshotToInternalStorage(
            getTestName(),
            composeTestRule.onRoot().captureToImage().asAndroidBitmap()
        )
    }
}
