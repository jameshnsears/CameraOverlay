package com.github.jameshnsears.cameraoverlay.model.photo.http

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.request.ImageRequest
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoCardData
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoCardUtility
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoCollection
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoImageType
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoResourcesUtility
import com.github.jameshnsears.cameraoverlay.view.photo.PhotoCard
import com.github.jameshnsears.cameraoverlay.view.theme.AppTheme
import com.google.common.net.HttpHeaders
import junit.framework.TestCase
import junit.framework.TestCase.fail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Rule
import org.junit.Test

class HttpEndpointTest : PhotoResourcesUtility() {
    @get:Rule
    val composeTestRule = createComposeRule()

    suspend fun useCoilToGetImage() {
        val request = ImageRequest.Builder(context)
            .data("https://www.example.com/image.jpg")
            .build()

        val imageLoader = ImageLoader.Builder(context)
            .crossfade(true)
            .build()

        val drawable = imageLoader.execute(request).drawable
    }

    @Test
    suspend fun createPhotoCardViaHttp() {
        val httpEndpointMock = HttpEndpointMock()
        httpEndpointMock.start()

        val httpEndpointResponse = httpEndpointMock.assertResponseMockWebServer("http://127.0.0.1:8080/resources/eiffel_tower")
        TestCase.assertEquals(
            "330753",
            httpEndpointResponse.header(HttpHeaders.CONTENT_LENGTH, "-1")
        )

        // coil
        runBlocking {
//            withContext(Dispatchers.IO) {
            launch {
                createPhotoCardViaHttp()
            }
        }

        val photoCardUtility = PhotoCardUtility()

        composeTestRule.setContent {
            AppTheme {

                // TODO:
                // retrieve image via http
                // determine image type - i.e. JPEG
                //

                PhotoCard(
                    rememberNavController(),
                    PhotoCardData(
                        PhotoCollection.HttpEndpoint,
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

        httpEndpointMock.shutdown()

        fail("wip")
    }
}
