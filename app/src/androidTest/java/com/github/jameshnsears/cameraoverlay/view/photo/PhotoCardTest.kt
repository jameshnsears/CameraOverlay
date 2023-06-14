package com.github.jameshnsears.cameraoverlay.view.photo

import android.location.Location
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoCollectionEnum
import com.github.jameshnsears.cameraoverlay.model.photo.card.PhotoCardData
import com.github.jameshnsears.cameraoverlay.utility.ScreenshotTestUtility
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PhotoCardTest : ScreenshotTestUtility() {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        confirmEnvironmentCompatible()
    }

    @Test
    fun photoCard() {
        val location = Location("")
        location.longitude = 52.5186
        location.latitude = 13.3763

        // PhotoCardViewModel
        // .getLocation
        // .calculateDistance
        // .getImageType

        val photoCardData = PhotoCardData(
            PhotoCollectionEnum.MediaStore,
            "JPEG",
            "file://a/b",
            "1/1/1970",
            doubleArrayOf(1.0, 2.0)
        )

        composeTestRule.setContent {
            PhotoCard(rememberNavController(),  photoCardData)
        }

        composeTestRule
            .onNodeWithContentDescription("GPS EXIF")
            .assertExists()

        composeTestRule
            .onNodeWithText(
                "1.0, 2.0"
            )
            .assertIsDisplayed()
    }
}
