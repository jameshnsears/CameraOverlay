package com.github.jameshnsears.cameraoverlay.utility

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onRoot
import java.io.File
import java.io.FileOutputStream
import org.junit.Rule
import org.junit.rules.TestName

open class ScreenshotTestUtility : CommonTestUtility() {
    @get:Rule
    var testName = TestName()

    protected fun getTestName() = "${javaClass.simpleName}.${testName.methodName}"

    protected fun saveScreenshotToInternalStorage(fileName: String, bitmap: Bitmap) {
        // /data/data/com.github.jameshnsears.cameraoverlay/files/
        FileOutputStream("${context.filesDir.canonicalPath}/$fileName").use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
    }

    protected fun getExpectedScreenshot(fileName: String): Bitmap? {
        return BitmapFactory.decodeStream(
            this::class.java.classLoader.getResourceAsStream(
                fileName
            )
        )
    }

    protected fun getActualScreenshot(): Bitmap? {
        return BitmapFactory.decodeFile(
            File(
                context.filesDir,
                getTestName()
            ).absolutePath
        )
    }

    protected fun takeScreenshot(composeTestRule: ComposeTestRule) {
        saveScreenshotToInternalStorage(
            getTestName(),
            composeTestRule.onRoot().captureToImage().asAndroidBitmap()
        )
    }
}
