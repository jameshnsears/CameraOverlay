package com.github.jameshnsears.cameraoverlay.model.photo.mediastore

import android.Manifest
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.Assert.assertEquals
import junit.framework.Assert.fail
import org.junit.Test
import androidx.test.rule.GrantPermissionRule
import org.junit.Rule
import org.junit.runner.RunWith



class MediaStoreTest {
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule
    var mRuntimePermissionRule = GrantPermissionRule.grant(
        Manifest.permission.READ_EXTERNAL_STORAGE,
    )

    @Test
    fun mediaStoreTest() {
//        val application = ApplicationProvider.getApplicationContext() as Application
//
//        val viewModel = MainActivityViewModel(application)
//        viewModel.loadImages()

        ////////////////////////////////////////////////////

        // https://developer.android.com/training/data-storage/shared/media
        // https://developer.android.com/training/data-storage/shared/media#query-collection
        // /home/jsears/GIT_REPOS/PicturePostcard/storage-samples

        /*
        If your app targets Android 10 (API level 29) or higher, in order for your app to
        retrieve unredacted Exif metadata from photos, you need to declare the
        ACCESS_MEDIA_LOCATION permission in your app's manifest, then request this
        permission at runtime.

        With scoped storage, an app no longer has direct access to all the files in external storage.
        If an app wants to manipulate a file that it didn't create, it has to get explicit authorization from the user.
        */

        val galleryImageUrls = mutableListOf<Uri>()
        val columns = arrayOf(MediaStore.Images.Media._ID,
            MediaStore.Images.Media.RELATIVE_PATH)
        val orderBy = MediaStore.Images.Media.DATE_TAKEN

        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL
                )
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }

        context.contentResolver.query(
            collection, columns,
            null, null, "$orderBy DESC"
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)

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

        assertEquals(3, galleryImageUrls.size);
    }
}