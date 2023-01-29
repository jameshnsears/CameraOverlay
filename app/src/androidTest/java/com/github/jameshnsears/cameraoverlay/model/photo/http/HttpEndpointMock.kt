package com.github.jameshnsears.cameraoverlay.model.photo.http

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.Buffer
import timber.log.Timber

class HttpEndpointMock {
    private var mockWebServer = mockWebServer()

    fun start() {
        mockWebServer.start(8080)
    }

    fun shutdown() {
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

    fun assertResponseMockWebServer(url: String): Response {
        // https://square.github.io/okhttp/recipes/
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()
        return client.newCall(request).execute()
    }
}
