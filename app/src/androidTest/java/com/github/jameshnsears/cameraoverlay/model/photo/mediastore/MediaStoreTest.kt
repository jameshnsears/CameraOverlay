package com.github.jameshnsears.cameraoverlay.model.photo.mediastore

import android.Manifest
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.Assert.assertEquals
import junit.framework.Assert.fail
import org.junit.Test
import androidx.test.rule.GrantPermissionRule
import org.junit.Rule
import org.junit.runner.RunWith
import timber.log.Timber


class MediaStoreTest {
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule
    var mRuntimePermissionRule = GrantPermissionRule.grant(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_MEDIA_LOCATION   // retrieve unredacted Exif metadata from photos
    )

    @Test
    fun mediaStoreTest() {
//        val application = ApplicationProvider.getApplicationContext() as Application
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

        context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.RELATIVE_PATH
            ),
            null,
            null,
            null    // "${MediaStore.Images.Media.DATE_ADDED} DESC"
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)

            Timber.d("Found ${cursor.count} images")

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)

                galleryImageUrls.add(
                    ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                )
            }
        }

        assertEquals(1, galleryImageUrls.size);
    }
}