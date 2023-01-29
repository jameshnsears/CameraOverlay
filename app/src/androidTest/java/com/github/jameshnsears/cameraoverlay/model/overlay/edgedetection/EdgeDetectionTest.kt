package com.github.jameshnsears.cameraoverlay.model.overlay.edgedetection

import com.github.jameshnsears.cameraoverlay.model.edgedetection.Canny
import com.github.jameshnsears.cameraoverlay.model.edgedetection.EdgeDetectionUtils
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoResourcesUtility
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class EdgeDetectionTest: PhotoResourcesUtility() {
    init {
        System.loadLibrary("opencv_java4")
    }

    @Test
    fun canny() {
        val edgeDetectionCanny = Canny()
        val originalImageAsMat = getImageAsMat(edgeDetectionCanny, "reichstag.jpg")

        val blurredImage =
            edgeDetectionCanny.applyGaussianBlurFilterToReduceNoise(originalImageAsMat)

        val cannyImage = edgeDetectionCanny.applyCanny(blurredImage)

        val cannyBitmap = edgeDetectionCanny.convertMatToBitmap(cannyImage)
        assertNotNull(cannyBitmap)

        val transparentCannyBitmap = edgeDetectionCanny.makeBitmapTransparent(cannyBitmap)

        val expectedBitmap = edgeDetectionCanny
            .convertMatToBitmap(
                getImageAsMat(edgeDetectionCanny, "EdgeDetection/reichstag-conny.png"))

//        saveBitmap(transparentCannyBitmap, "reichstag-conny.png")

        assertTrue(transparentCannyBitmap.sameAs(expectedBitmap))
    }

    private fun getImageAsMat(edgeDetectionUtils: EdgeDetectionUtils, path: String) =
        edgeDetectionUtils.convertOriginalImageToBitmap(
            this.javaClass.classLoader.getResourceAsStream(path),
        )
}
