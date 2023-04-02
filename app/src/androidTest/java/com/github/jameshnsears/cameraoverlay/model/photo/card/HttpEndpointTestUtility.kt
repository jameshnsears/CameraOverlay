package com.github.jameshnsears.cameraoverlay.model.photo.card

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.Buffer
import timber.log.Timber

class HttpEndpointTestUtility {
    // https://square.github.io/okhttp/recipes/
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
                val filePath = request.path?.substring(1)
                return MockResponse().setResponseCode(200)
                    .addHeader("Content-Type:image/jpeg")
                    .setBody(getBinaryFileAsBuffer(filePath.toString()))
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
}
