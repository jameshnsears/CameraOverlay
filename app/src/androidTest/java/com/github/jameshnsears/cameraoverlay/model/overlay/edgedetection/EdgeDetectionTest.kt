package com.github.jameshnsears.cameraoverlay.model.overlay.edgedetection

import android.graphics.Bitmap
import android.os.Environment
import androidx.test.platform.app.InstrumentationRegistry
import com.github.jameshnsears.cameraoverlay.model.edgedetection.Canny
import com.github.jameshnsears.cameraoverlay.model.edgedetection.EdgeDetectionUtils
import java.io.File
import java.io.FileOutputStream
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class EdgeDetectionTest {
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

        saveBitmap(transparentCannyBitmap, "aaa.png")

        assertTrue(transparentCannyBitmap.sameAs(expectedBitmap))
    }

    private fun getImageAsMat(edgeDetectionUtils: EdgeDetectionUtils, path: String) =
        edgeDetectionUtils.convertOriginalImageToBitmap(
            this.javaClass.classLoader.getResourceAsStream(path),
        )


    private fun saveBitmap(bitmapToSave: Bitmap, filename: String) {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), filename)
        val outputStream = FileOutputStream(file)

        bitmapToSave.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.close()
    }
}
