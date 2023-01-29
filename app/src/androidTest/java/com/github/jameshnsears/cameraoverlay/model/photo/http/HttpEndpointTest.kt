package com.github.jameshnsears.cameraoverlay.model.photo.http

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoCardData
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoCollection
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoImageType
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoResourcesUtility
import com.github.jameshnsears.cameraoverlay.view.photo.PhotoCard
import com.github.jameshnsears.cameraoverlay.view.theme.AppTheme
import junit.framework.TestCase.fail
import org.junit.Rule
import org.junit.Test

class HttpEndpointTest : PhotoResourcesUtility() {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun createPhotoCardViaHttp() {
        val httpEndpointUtility = HttpEndpointUtility()
        httpEndpointUtility.start()

        composeTestRule.setContent {
            AppTheme {

                // TODO:
                // retrieve image via http
                // determine image type - i.e. JPEG
                //

                PhotoCard(
                    rememberNavController(),
                    PhotoCardData(
                        PhotoCollection.MediaStore,
                        PhotoImageType.JPEG,
                        "http://127.0.0.1:8080/resources/eiffel_tower",
                        "when taken 0",
                        "distance 0",
                        photoId = 0,
                    ),
                )
            }
        }

        // TODO test
//        composeTestRule.onNodeWithText("Continue").performClick()

        // TODO how to click on a specific image & identify it?

        httpEndpointUtility.shutdown()

        fail("wip")
    }
}
