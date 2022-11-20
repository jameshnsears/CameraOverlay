package com.github.jameshnsears.cameraoverlay.model.photo

import androidx.compose.ui.test.junit4.createComposeRule
import com.github.jameshnsears.cameraoverlay.model.photo.mediastore.MediaStoreMediator
import com.github.jameshnsears.cameraoverlay.view.photo.PhotoCard
import com.github.jameshnsears.cameraoverlay.view.theme.CameraOverlayTheme
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

class PhotoCardTest : PhotoResourcesUtility() {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun mediaStore() {
        if (MediaStoreMediator.picturesInMediaStore(context).size != 3) {
            copyImageResourcesToExternalStorage()
        }
        val picturesInMediaStore = MediaStoreMediator.picturesInMediaStore(context)

        composeTestRule.setContent {
            CameraOverlayTheme {
                PhotoCard(
                    PhotoCardData(
                        picturesInMediaStore[0].uri,
                        "date taken 0",
                        "gps co-ords 0",
                        "path 0"
                    ),
                )
            }
        }

        // TODO test

        return
    }

    @Test
    fun http() {
        val mockWebServer = mockWebServer()
        mockWebServer.start(8080)

        assertResponseMockWebServer()

        composeTestRule.setContent {
            CameraOverlayTheme {
                PhotoCard(
                    PhotoCardData(
                        "https://example.com/image.jpg",
                        "date taken 0",
                        "gps co-ords 0",
                        "path 0"
                    ),
                )
            }
        }

        // TODO test
//        composeTestRule.onNodeWithText("Continue").performClick()

        // TODO how to click on a specific image & identify it?

        mockWebServer.shutdown()
    }

    private fun mockWebServer(): MockWebServer {
        val mockWebServer = MockWebServer()

        val dispatcher: Dispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                when (request.path) {
                    "/resources/eiffel_tower" -> {
                        return MockResponse().setResponseCode(200)
                            .addHeader("Content-Type:image/jpeg")
                            .setBody(getBinaryFileAsBuffer("eiffel_tower.jpg"))
                    }
                }
                return MockResponse().setResponseCode(404)
            }
        }

        mockWebServer.dispatcher = dispatcher
        return mockWebServer
    }

    private fun getBinaryFileAsBuffer(filename: String): Buffer {
        val buffer = Buffer()
        buffer.write(this.javaClass.classLoader.getResourceAsStream(filename).readBytes())
        Timber.d("${buffer.size}")
        return buffer
    }

    private fun assertResponseMockWebServer() {
        // https://square.github.io/okhttp/recipes/
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://127.0.0.1:8080/resources/eiffel_tower")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful)
                fail("Unexpected code $response")

            assertEquals("330753", response.header(CONTENT_LENGTH, "-1"))
        }
    }
}
