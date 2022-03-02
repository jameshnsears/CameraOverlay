package com.github.jameshnsears.cameraoverlay.model.photo.mediastore

import android.content.ContentValues
import android.content.Context
import android.media.MediaScannerConnection
import android.os.Environment
import android.provider.MediaStore
import androidx.test.platform.app.InstrumentationRegistry
import com.github.jameshnsears.cameraoverlay.model.utils.MethodLineLoggingTree
import java.util.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import org.junit.Before
import org.junit.Test
import timber.log.Timber


class MediaStoreTest {
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    // no need for permission, as automatically granted to androidTest
//    @get:Rule
//    var permissionRule = GrantPermissionRule.grant(
//        Manifest.permission.READ_EXTERNAL_STORAGE,  // access other apps files, outside of Scoped Storage
//        Manifest.permission.ACCESS_MEDIA_LOCATION   // retrieve unredacted Exif metadata from photos
//    )

    @Before
    fun setup() {
        initLogging()

        removeImagesFromExternalStorage()

        copyImageResourcesToExternalStorage()
    }

    private fun initLogging() {
        if (Timber.treeCount == 0) {
            Timber.plant(MethodLineLoggingTree())
        }
    }

    private fun copyImageResourcesToExternalStorage() {
        for (image in arrayOf(
            "eiffel_tower",
            "reichstag",
            "tower_bridge"
        )) {
            copyImageToExternalStorage(image)
        }
    }

    private fun removeImagesFromExternalStorage() {
        context.contentResolver.delete(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null
        )
    }

    private fun copyImageToExternalStorage(imageName: String) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, imageName)

            // https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")

            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        val uri = context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )

        if (uri != null) {
            val outputStream = context.contentResolver.openOutputStream(uri)
            if (outputStream != null) {
                val inputStream = this::class.java.classLoader.getResourceAsStream("$imageName.jpg")
                outputStream.write(inputStream.readBytes())
                outputStream.close()
            }

            MediaScannerConnection.scanFile(
                context,
                arrayOf(uri.path),
                arrayOf("image/jpeg"),
                null
            )
        }
    }

    @Test
    fun locateItemsInMediaStore() {
        val picturesInMediaStore = MediaStoreMediator.picturesInMediaStore(context)
        assertEquals(3, picturesInMediaStore.size)
    }

    @Test
    fun confirmExifValues() {
        val picturesInMediaStore = MediaStoreMediator.picturesInMediaStore(context)

        val eiffelTower = picturesInMediaStore[0]
        val eiffelTowerExif = MediaStoreMediator.getExifFromUri(context, eiffelTower)
        assertEquals("eiffel_tower.jpg", eiffelTower.filename)
        assertEquals(1631639311000, eiffelTowerExif.dateTime)
        assertNull(eiffelTowerExif.latLong)

        val towerBridge = picturesInMediaStore[2]
        val towerBridgeExif = MediaStoreMediator.getExifFromUri(context, towerBridge)
        assertEquals("tower_bridge.jpg", towerBridge.filename)
        assertEquals(1631639351000, towerBridgeExif.dateTime)

        // latitude = 51/1,30/1,99/5
        // longitude = 0/1,4/1,39327/1250
        assertEquals("[51.5055, -0.075406]", Arrays.toString(towerBridgeExif.latLong))
    }
}