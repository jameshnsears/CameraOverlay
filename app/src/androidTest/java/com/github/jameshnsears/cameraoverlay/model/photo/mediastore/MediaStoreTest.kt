package com.github.jameshnsears.cameraoverlay.model.photo.mediastore

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.exifinterface.media.ExifInterface
import androidx.test.platform.app.InstrumentationRegistry
import com.github.jameshnsears.cameraoverlay.model.utils.MethodLineLoggingTree
import junit.framework.Assert.assertEquals
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
    fun copyImageResourcesToExternalStorage() {
        if (Timber.treeCount == 0) {
            Timber.plant(MethodLineLoggingTree())
        }

        removeExternalStorageImages()

        val images = arrayOf(
            "eiffel_tower",
            "reichstag",
            "tower_bridge"
        )

        for (image in images) {
            copyImageToExternalStorage(image)
        }
    }

    private fun removeExternalStorageImages() {
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
        val mediaStoreEntries = mutableListOf<Uri>()

        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,       // /storage/emulated/0/Pictures
            arrayOf(
                MediaStore.Images.Media._ID,                    // column 0
                MediaStore.Images.Media.DISPLAY_NAME,           // column 1
            ),
            null,
            null,
            "${MediaStore.Images.Media.DISPLAY_NAME} ASC"
        ) ?: throw Exception("Query could not be executed")

        cursor.use {
            while (cursor.moveToNext()) {
                val mediaStoreImageUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    cursor.getInt(0).toLong()
                )

                getExif(mediaStoreImageUri)

                mediaStoreEntries.add(mediaStoreImageUri)
            }
        }

        assertEquals(3, mediaStoreEntries.size);
    }

    private fun getExif(mediaStoreImageUri: Uri) {
        val inputStream = context.contentResolver.openInputStream(mediaStoreImageUri)
        val exifInterface = ExifInterface(inputStream!!)

        Timber.d("latLongArray=${exifInterface.latLong}")
        Timber.d("dateTime={$exifInterface.dateTime}")
    }
}