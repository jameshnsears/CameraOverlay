package com.github.jameshnsears.cameraoverlay.view.photo

import android.location.Location
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.github.jameshnsears.cameraoverlay.utility.ScreenshotTestUtility
import org.junit.Rule
import org.junit.Test

class PhotoSelectScreenTest : ScreenshotTestUtility() {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun myText() {
        val location = Location("")
        location.longitude = 52.5186
        location.latitude = 13.3763

        composeTestRule.setContent {
            MyText(location)
        }

        composeTestRule
            .onNodeWithContentDescription("Location Description")
            .assertExists()

        composeTestRule
            .onNodeWithText(
                "Location[ 13.376300,52.518600 hAcc=??? t=?!? et=?!? vAcc=??? sAcc=??? bAcc=???]"
            )
            .assertIsDisplayed()
    }
}
