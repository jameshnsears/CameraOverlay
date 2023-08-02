package com.github.jameshnsears.cameraoverlay.model.edgedetection

import com.github.jameshnsears.cameraoverlay.utility.CommonTestUtility
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class EdgeDetectionTest : CommonTestUtility() {
    init {
        System.loadLibrary("opencv_java4")
    }

    @Test
    fun confirmCannyWorking() {
        val edgeDetectionCanny = Canny()
        val originalImageAsMat = getImageAsMatFromResources(
            edgeDetectionCanny,
            "MediaStore/reichstag.jpeg"
        )

        val blurredImage =
            edgeDetectionCanny.applyGaussianBlurFilterToReduceNoise(originalImageAsMat)

        val cannyImage = edgeDetectionCanny.applyCanny(blurredImage)

        val cannyBitmap = edgeDetectionCanny.convertMatToBitmap(cannyImage)
        assertNotNull(cannyBitmap)

        val transparentCannyBitmap = edgeDetectionCanny.makeBitmapTransparent(cannyBitmap)

        val expectedBitmap = edgeDetectionCanny
            .convertMatToBitmap(
                getImageAsMatFromResources(edgeDetectionCanny, "EdgeDetection/reichstag.png")
            )

        // TODO modify threshold values

        assertTrue(transparentCannyBitmap.sameAs(expectedBitmap))
    }

    private fun getImageAsMatFromResources(edgeDetectionUtils: EdgeDetectionUtils, path: String) =
        edgeDetectionUtils.convertOriginalImageToBitmap(
            this.javaClass.classLoader!!.getResourceAsStream(path)
        )
}
