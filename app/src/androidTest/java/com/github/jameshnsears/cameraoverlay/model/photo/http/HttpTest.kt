package com.github.jameshnsears.cameraoverlay.model.photo.http

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoCardData
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoCollection
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoImageType
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoResourcesUtility
import com.github.jameshnsears.cameraoverlay.view.photo.PhotoCard
import com.github.jameshnsears.cameraoverlay.view.theme.AppTheme
import com.google.common.net.HttpHeaders.CONTENT_LENGTH
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.Buffer
import org.junit.Rule
import org.junit.Test
import timber.log.Timber

class HttpTest : PhotoResourcesUtility() {
    @get:Rule
    val composeTestRule = createComposeRule()

//    @Test
//    fun mediaStore() {
//        if (MediaStoreMediator.picturesInMediaStore(context).size != 3) {
//            copyImageResourcesToExternalStorage()
//        }
//
//        assertEquals(3, MediaStoreMediator.picturesInMediaStore(context).size)
//    }

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
