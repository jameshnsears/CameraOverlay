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
import junit.framework.Assert.assertEquals
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

    fun copyFileToInternalStorage() {
        // /storage/emulated/0/Pictures
        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "reichstag")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        this::class.java.classLoader.res

        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        if (uri != null) {
            val outputStream = context.contentResolver.openOutputStream(uri)
            if (outputStream != null) {
                val inputStream = this::class.java.classLoader.getResourceAsStream("reichstag.jpg")
                outputStream.write(inputStream.readBytes())
                outputStream.close()
            }
        }

        if (uri != null) {
            MediaScannerConnection.scanFile(
                context,
                arrayOf(uri.path),
                null
            ) { path, uri ->
                Timber.d("path=", path)
                Timber.d("uri=", uri)
            }
        }


        return
    }

    fun foreMediaStoreRefresh() {
//        MediaScannerConnection.scanFile(
//            context,
//            arrayOf(f.toString()),
//            null
//        ) { path, uri ->
//            Timber.d("Scanned ${path}:${uri}")
//        }
//
//        return
    }

    @Test
    fun mediaStoreTest() {
//        val application = ApplicationProvider.getApplicationContext() as Application
//        val viewModel = MainActivityViewModel(application)
//        viewModel.loadImages()

        copyFileToInternalStorage()
        foreMediaStoreRefresh()

        val galleryImageUrls = mutableListOf<Uri>()

        // TODO need to launch in viewModelScope.launch
        val cursor = context.contentResolver.query(
            // MediaStore.Images are stored in the DCIM/ and Pictures/ directories
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,       // not sdcard!
            arrayOf(
                MediaStore.Images.Media._ID,                    // column 0
                MediaStore.Images.Media.DISPLAY_NAME,           // column 1
                MediaStore.Images.Media.DATE_TAKEN,             // column 2
            ),
            null,
            null,
            "${MediaStore.Images.Media.DATE_TAKEN} ASC"
        ) ?: throw Exception("Query could not be executed")

        cursor.use {
            while (cursor.moveToNext()) {
                val uri: Uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    cursor.getInt(0).toLong()
                )

                val filename = cursor.getString(1)
                val type = context.contentResolver.getType(uri)

                // if dateTaken == 0 then see: https://developer.android.com/reference/android/provider/MediaStore.MediaColumns#DATE_TAKEN
                val dateTaken = cursor.getLong(2)

                // https://developer.android.com/reference/androidx/exifinterface/media/ExifInterface
                val inputStream = context.contentResolver.openInputStream(uri)
                val exifInterface = ExifInterface(inputStream!!)
                var latLongArray = exifInterface.latLong

                galleryImageUrls.add(uri)
            }
        }

        assertEquals(1, galleryImageUrls.size);
    }
}