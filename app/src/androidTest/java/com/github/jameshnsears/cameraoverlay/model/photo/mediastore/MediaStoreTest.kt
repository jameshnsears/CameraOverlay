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

    @get:Rule
    var permissionRule = GrantPermissionRule.grant(
        Manifest.permission.READ_EXTERNAL_STORAGE,  // access other apps files, outside of Scoped Storage
        Manifest.permission.ACCESS_MEDIA_LOCATION   // retrieve unredacted Exif metadata from photos
    )

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

        // "external" == actually internal on the Phone!
        val externalContentUri = MediaStore.Files.getContentUri("external")
            ?: throw Exception("External Storage not available")

        val cursor = context.contentResolver.query(
            externalContentUri,
            arrayOf(
//            MediaStore.Files.FileColumns._ID,
                MediaStore.Images.Media._ID,
//            MediaStore.Files.FileColumns.DISPLAY_NAME,
//            MediaStore.Files.FileColumns.SIZE,
//            MediaStore.Files.FileColumns.MEDIA_TYPE,
//            MediaStore.Files.FileColumns.MIME_TYPE,
//            MediaStore.Files.FileColumns.DATA,
            ),
            null,
            null,
            "${MediaStore.MediaColumns.DATE_ADDED} DESC"
        ) ?: throw Exception("Query could not be executed")


        cursor.use {
            while (cursor.moveToNext()) {
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
//                val displayNameColumn =
//                    cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
//                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE)
//                val mediaTypeColumn =
//                    cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MEDIA_TYPE)
//                val mimeTypeColumn =
//                    cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE)
//                val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)

                val id = cursor.getInt(idColumn)
                val contentUri: Uri = ContentUris.withAppendedId(
                    externalContentUri,
                    id.toLong()
                )

                val type = context.contentResolver.getType(contentUri)
                var fileName = ""

                context.contentResolver.query(
                    contentUri,
                    arrayOf(
                        MediaStore.MediaColumns.DISPLAY_NAME),
                    null,
                    null,
                    null
                )?.use { metaCursor ->
                    if (metaCursor.moveToFirst()) {
                        fileName = metaCursor.getString(0)
                    }
                }


                galleryImageUrls.add(contentUri)
            }


            /*
            context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.DATE_ADDED
                ),
                null,
                null,
                "${MediaStore.Images.Media.DATE_ADDED} DESC"
            )?.use { cursor ->
                Timber.d("Found ${cursor.count} images")

                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val dateModifiedColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
                val displayNameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)

    //                var photoUri: Uri = Uri.withAppendedPath(
    //                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
    //                    cursor.getString(idColumn)
    //                )
    //
    //                photoUri = MediaStore.setRequireOriginal(photoUri)
    //                context.contentResolver.openInputStream(photoUri)?.use { stream ->
    //                    ExifInterface(stream).run {
    //                        // If lat/long is null, fall back to the coordinates (0, 0).
    //                        val latLong = latLong ?: doubleArrayOf(0.0, 0.0)
    //                    }
    //                }

                    galleryImageUrls.add(
                        ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            id
                        )
                    )
                }
             */
        }

        assertEquals(1, galleryImageUrls.size);
    }
}