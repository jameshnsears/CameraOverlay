package com.github.jameshnsears.cameraoverlay.utility

import android.content.Context
import android.graphics.Bitmap
import androidx.test.platform.app.InstrumentationRegistry
import com.github.jameshnsears.cameraoverlay.common.MethodLineLoggingTree
import java.io.FileOutputStream
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestName
import timber.log.Timber

open class TestUtility {
    @get:Rule
    var testName = TestName()

    protected fun getTestName() = "${javaClass.simpleName}.${testName.methodName}"

    protected val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun initLogging() {
        if (Timber.treeCount == 0) {
            Timber.plant(MethodLineLoggingTree())
        }
    }

    protected fun saveScreenshotToInternalStorage(fileName: String, bitmap: Bitmap) {
        // /data/data/com.github.jameshnsears.cameraoverlay/files/
        FileOutputStream("${context.filesDir.canonicalPath}/$fileName").use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
    }
}
