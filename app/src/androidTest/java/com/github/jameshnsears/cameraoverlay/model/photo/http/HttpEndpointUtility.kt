package com.github.jameshnsears.cameraoverlay.model.photo.http

import com.google.common.net.HttpHeaders
import junit.framework.TestCase
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.Buffer
import timber.log.Timber

class HttpEndpointUtility {
    private var mockWebServer = mockWebServer()

    public fun start() {
        mockWebServer.start(8080)
        assertResponseMockWebServer()
    }

    public fun shutdown() {
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
            if (!response.isSuccessful) {
                TestCase.fail("Unexpected code $response")
            }

            TestCase.assertEquals("330753", response.header(HttpHeaders.CONTENT_LENGTH, "-1"))
        }
    }
}