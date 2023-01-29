package com.github.jameshnsears.cameraoverlay.model.photo

import android.content.ContentValues
import android.content.Context
import android.media.MediaScannerConnection
import android.os.Environment
import android.provider.MediaStore
import androidx.test.platform.app.InstrumentationRegistry
import com.github.jameshnsears.cameraoverlay.common.EmulatorCompatibilityHelper
import com.github.jameshnsears.cameraoverlay.common.MethodLineLoggingTree
import com.github.jameshnsears.cameraoverlay.model.photo.mediastore.MediaStoreMediator
import junit.framework.TestCase
import org.junit.Before
import timber.log.Timber

open class PhotoResourcesUtility {
    protected val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setUp() {
        confirmEnvironmentOK()
        initLogging()
        populateMediaStoreWithImages()
    }

    private fun confirmEnvironmentOK() {
        if (!EmulatorCompatibilityHelper.canRunInEmulatorQ()) {
            TestCase.fail()
        }
    }

    private fun initLogging() {
        if (Timber.treeCount == 0) {
            Timber.plant(MethodLineLoggingTree())
        }
    }

    private fun populateMediaStoreWithImages() {
        if (MediaStoreMediator.picturesInMediaStore(context).size != 3) {
            copyImageResourcesToExternalStorage()
        }
        TestCase.assertEquals(3, MediaStoreMediator.picturesInMediaStore(context).size)
    }

    var images = arrayOf(
        "eiffel_tower",
        "reichstag",
        "tower_bridge",
    )

    private fun copyImageResourcesToExternalStorage() {
        for (image in images) {
            copyImageToExternalStorage(image)
        }
    }

    private fun copyImageToExternalStorage(imageName: String) {
        Timber.d("imageName=%s", imageName)

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, imageName)

            // https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")

            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        val uri = context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues,
        )

        if (uri != null) {
            val outputStream = context.contentResolver.openOutputStream(uri)
            if (outputStream != null) {
                val inputStream =
                    this::class.java.classLoader.getResourceAsStream("$imageName.jpg")
                outputStream.write(inputStream.readBytes())
                outputStream.close()
            }

            MediaScannerConnection.scanFile(
                context,
                arrayOf(uri.path),
                arrayOf("image/jpeg"),
                null,
            )
        }
    }
}
