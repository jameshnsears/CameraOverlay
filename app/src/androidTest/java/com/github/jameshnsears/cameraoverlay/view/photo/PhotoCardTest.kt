package com.github.jameshnsears.cameraoverlay.view.photo

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.github.jameshnsears.cameraoverlay.model.photo.card.PhotoCardDataSample
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
    fun displayStandardCard() {
        // TODO

//        fail(
//            "pass in a valid imageUri in PhotoCardDataSample.photoCardDataSample01, " +
//                "so I see nice pic' also use a complete whenTaken value"
//        )
//
//        composeTestRule.setContent {
//            PhotoCard(PhotoCardDataSample.photoCardDataSample01)
//        }
//
//        composeTestRule
//            .onNodeWithContentDescription("GPS EXIF")
//            .assertExists()
//
//        composeTestRule
//            .onNodeWithText(
//                PhotoCardDataSample.photoCardDataSample01.whereTaken.contentToString()
//            )
//            .assertIsDisplayed()
    }

    @Test
    fun displayCardWithMissingExif() {
        // TODO
//        fail("todo - update PhotoCardDataSample")
    }
}
