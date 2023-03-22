package com.github.jameshnsears.cameraoverlay.model.edgedetection

import com.github.jameshnsears.cameraoverlay.model.photo.repository.mediastore.MediaStoreRepository
import com.github.jameshnsears.cameraoverlay.utility.MediaStoreUtility
import com.github.jameshnsears.cameraoverlay.utility.TestUtility
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class EdgeDetectionTest : TestUtility() {
    init {
        System.loadLibrary("opencv_java4")
    }

    @Before
    fun useMediaStore() {
        val repository = MediaStoreRepository()

        if (repository.queryPhotoRepository(context).size != 3) {
            val mediaStoreUtility = MediaStoreUtility()
            mediaStoreUtility.setUpMediaStore(context)
        }
    }

    @Test
    fun canny() {
        val edgeDetectionCanny = Canny()
        val originalImageAsMat = getImageAsMat(edgeDetectionCanny, "MediaStore/reichstag.jpg")

        val blurredImage =
            edgeDetectionCanny.applyGaussianBlurFilterToReduceNoise(originalImageAsMat)

        val cannyImage = edgeDetectionCanny.applyCanny(blurredImage)

        val cannyBitmap = edgeDetectionCanny.convertMatToBitmap(cannyImage)
        assertNotNull(cannyBitmap)

        val transparentCannyBitmap = edgeDetectionCanny.makeBitmapTransparent(cannyBitmap)

        val expectedBitmap = edgeDetectionCanny
            .convertMatToBitmap(
                getImageAsMat(edgeDetectionCanny, "EdgeDetection/reichstag.png")
            )

        assertTrue(transparentCannyBitmap.sameAs(expectedBitmap))
    }

    private fun getImageAsMat(edgeDetectionUtils: EdgeDetectionUtils, path: String) =
        edgeDetectionUtils.convertOriginalImageToBitmap(
            this.javaClass.classLoader!!.getResourceAsStream(path)
        )
}
