package com.github.jameshnsears.cameraoverlay.model.photo.mediastore

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.exifinterface.media.ExifInterface

class MediaStoreMediator {
    private constructor() {
        // detekt: UtilityClassWithPublicConstructor
    }

    companion object {
        fun picturesInMediaStore(context: Context): List<MediaStoreData> {
            val mediaStoreEntries = mutableListOf<MediaStoreData>()

            val cursor = context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // /storage/emulated/0/Pictures
                arrayOf(
                    MediaStore.Images.Media._ID, // column 0
                    MediaStore.Images.Media.DISPLAY_NAME, // column 1
                ),
                null,
                null,
                "${MediaStore.Images.Media.DISPLAY_NAME} ASC"
            ) ?: throw MediaStoreMediatorException("Query could not be executed")

            cursor.use {
                while (cursor.moveToNext()) {
                    val imageUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        cursor.getInt(0).toLong()
                    )

                    mediaStoreEntries.add(
                        MediaStoreData(
                            imageUri,
                            cursor.getString(1)
                        )
                    )
                }
            }

            return mediaStoreEntries
        }

        fun getExifFromUri(context: Context, mediaStore: MediaStoreData): ExifInterface {
            val inputStream = context.contentResolver.openInputStream(mediaStore.uri)
            return ExifInterface(inputStream!!)
        }
    }
}
