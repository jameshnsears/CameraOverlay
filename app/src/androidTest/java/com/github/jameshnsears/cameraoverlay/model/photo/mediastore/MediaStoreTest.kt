package com.github.jameshnsears.cameraoverlay.model.photo.mediastore

import android.Manifest
import android.app.Application
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test


class MediaStoreTest {
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    // no need for permission, as automatically granted
//    @get:Rule
//    var permissionRule = GrantPermissionRule.grant(
//        Manifest.permission.READ_EXTERNAL_STORAGE,  // access other apps files, outside of Scoped Storage
//        Manifest.permission.ACCESS_MEDIA_LOCATION   // retrieve unredacted Exif metadata from photos
//    )

    @Test
    fun mediaStoreTest() {
        val application = ApplicationProvider.getApplicationContext() as Application
//        val viewModel = MainActivityViewModel(application)
//        viewModel.loadImages()

        // https://developer.android.com/training/data-storage/shared/media
        // https://developer.android.com/training/data-storage/shared/media#query-collection
        // /home/jsears/GIT_REPOS/PicturePostcard/storage-samples

        /*
        With scoped storage, an app no longer has direct access to all the files in external storage.
        If an app wants to manipulate a file that it didn't create, it has to get explicit authorization from the user.
        */

        // MediaStore.Images            == stored in the DCIM/ and Pictures/ directories
        // /storage/emulated/0/Pictures == internal == MediaStore.Images.Media.INTERNAL_CONTENT_URI
        // /storage/1A0E-211D/Pictures  == external (SDCARD) == MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val galleryImageUrls = mutableListOf<Uri>()

        val cursor = context.contentResolver.query(
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
                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    cursor.getInt(0).toLong()
                )

                val filename = cursor.getString(1)
                val type = context.contentResolver.getType(contentUri)


                val date = cursor.getLong(2)


                galleryImageUrls.add(contentUri)
            }
        }

        assertEquals(1, galleryImageUrls.size);
    }
}