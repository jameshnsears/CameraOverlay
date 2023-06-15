package com.github.jameshnsears.cameraoverlay.view.photo

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoCollectionEnum
import com.github.jameshnsears.cameraoverlay.model.photo.card.PhotoCardData
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoImageTypeEnum
import com.github.jameshnsears.cameraoverlay.utility.ScreenshotTestUtility
import junit.framework.TestCase.fail
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
        fail("pass in a valid imageUri, so I see nice pic' also use a complete whenTaken value")

        val photoCardData = PhotoCardData(
            PhotoCollectionEnum.MediaStore,
            PhotoImageTypeEnum.JPEG,
            "file://a/b",
            "1/1/1970",
            doubleArrayOf(52.5186, 13.3763)
        )

        composeTestRule.setContent {
            PhotoCard(photoCardData)
        }

        composeTestRule
            .onNodeWithContentDescription("GPS EXIF")
            .assertExists()

        composeTestRule
            .onNodeWithText(
                photoCardData.whereTaken.contentToString()
            )
            .assertIsDisplayed()
    }
}
