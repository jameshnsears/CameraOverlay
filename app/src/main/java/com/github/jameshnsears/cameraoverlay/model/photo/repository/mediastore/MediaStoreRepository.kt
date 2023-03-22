package com.github.jameshnsears.cameraoverlay.model.photo.repository.mediastore

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.exifinterface.media.ExifInterface
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoCollectionEnum
import com.github.jameshnsears.cameraoverlay.model.photo.card.PhotoCardData
import com.github.jameshnsears.cameraoverlay.model.photo.card.PhotoCardUtility
import com.github.jameshnsears.cameraoverlay.model.photo.repository.PhotoRepository
import com.github.jameshnsears.cameraoverlay.model.photo.repository.PhotoRepositoryData

class MediaStoreRepository : PhotoRepository {
    override fun queryPhotoRepository(context: Context): List<PhotoRepositoryData> {
        val mediaStoreEntries = mutableListOf<PhotoRepositoryData>()

        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // /storage/emulated/0/Pictures
            arrayOf(
                MediaStore.Images.Media._ID, // column 0
                MediaStore.Images.Media.DISPLAY_NAME, // column 1
                MediaStore.Images.Media.MIME_TYPE
            ),
            null,
            null,
            "${MediaStore.Images.Media.DISPLAY_NAME} ASC"
        ) ?: throw MediaStoreException("Query could not be executed")

        cursor.use {
            while (cursor.moveToNext()) {
                val imageUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    cursor.getInt(0).toLong()
                )

                mediaStoreEntries.add(
                    PhotoRepositoryData(
                        imageUri,
                        cursor.getString(2),
                        cursor.getString(1)
                    )
                )
            }
        }

        return mediaStoreEntries
    }

    override fun convertRepositoryDataIntoCardData(
        context: Context,
        photosFromRepository: List<PhotoRepositoryData>
    ): List<PhotoCardData> {
        val photoCardUtility = PhotoCardUtility()
        val photoCardData = mutableListOf<PhotoCardData>()

        for (photoFromRepository in photosFromRepository) {
            val exifInterface = photoCardUtility.exifInterface(context, photoFromRepository)

            photoCardData.add(
                PhotoCardData(
                    PhotoCollectionEnum.MediaStore,
                    photoFromRepository.mimeType,
                    photoFromRepository.uri,
                    exifInterface.getAttribute(ExifInterface.TAG_DATETIME),
                    exifInterface.latLong
                )
            )
        }

        return photoCardData
    }
}
