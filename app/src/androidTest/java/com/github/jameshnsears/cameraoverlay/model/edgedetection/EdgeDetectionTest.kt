package com.github.jameshnsears.cameraoverlay.model.edgedetection

import org.junit.Assert.assertNotNull
import org.junit.Test

class EdgeDetectionTest {
    init {
        System.loadLibrary("opencv_java3")
    }

    @Test
    fun canny() {
        val edgeDetectionCanny = Canny()
        val originalImageAsMat = getImageAsBitmap(edgeDetectionCanny)

        val blurredImage =
            edgeDetectionCanny.applyGaussianBlurFilterToReduceNoise(originalImageAsMat)

        val cannyImage = edgeDetectionCanny.applyCanny(blurredImage)

        val cannyBitmap = edgeDetectionCanny.convertMatToBitmap(cannyImage)

        assertNotNull(cannyBitmap)

        // TODO save image and do a comparison using google example tool.
        return
    }

    private fun getImageAsBitmap(edgeDetectionUtils: EdgeDetectionUtils) =
        edgeDetectionUtils.convertOriginalImageToBitmap(
            this.javaClass.classLoader.getResourceAsStream("reichstag.jpg")
        )
}
