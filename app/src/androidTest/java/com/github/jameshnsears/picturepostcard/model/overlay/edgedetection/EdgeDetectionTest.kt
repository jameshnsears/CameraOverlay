package com.github.jameshnsears.picturepostcard.model.overlay.edgedetection

import android.graphics.Bitmap
import org.junit.Test

class EdgeDetectionTest {
    init {
        System.loadLibrary("opencv_java3")
    }

    @Test
    fun cannyEdgeDetection() {
        val edgeDetectionCanny = Canny()
        val originalImageAsMat = getImageAsBitmap(edgeDetectionCanny)

        val blurredImage =
            edgeDetectionCanny.applyGaussianBlurFilterToReduceNoise(originalImageAsMat)

        val cannyImage = edgeDetectionCanny.applyCanny(blurredImage)

        val cannyBitmap = edgeDetectionCanny.convertMatToBitmap(cannyImage)

//        saveTransparentBitmap(
//            edgeDetectionCanny,
//            cannyBitmap,
//            "0-conny-Berlin_reichstag_west_panorama_2.png"
//        )

        // TODO save image and do a comparison using google example tool.
        return
    }

    private fun saveTransparentBitmap(
        edgeDetectionAlgorithm: EdgeDetectionUtils,
        bitmapToSave: Bitmap,
        filename: String
    ) {
        val transparentBitmap = edgeDetectionAlgorithm.makeBitmapTransparent(bitmapToSave)
        edgeDetectionAlgorithm.saveBitmap(
            transparentBitmap,
            filename
        )
    }

    private fun getImageAsBitmap(edgeDetectionUtils: EdgeDetectionUtils) =
        edgeDetectionUtils.convertOriginalImageToBitmap(
            this.javaClass.classLoader.getResourceAsStream("Berlin_reichstag_west_panorama_2.jpg")
        )
}
