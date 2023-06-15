package com.github.jameshnsears.cameraoverlay.model.photo.repository.mediastore

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.exifinterface.media.ExifInterface
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoCollectionEnum
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoImageTypeEnum
import com.github.jameshnsears.cameraoverlay.model.photo.card.PhotoCardData
import com.github.jameshnsears.cameraoverlay.model.photo.repository.PhotoRepository
import com.github.jameshnsears.cameraoverlay.model.photo.repository.PhotoRepositoryData
import timber.log.Timber

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

    override fun convertPhotoRepositoryDataIntoPhotoCardData(
        context: Context,
        photosFromRepository: List<PhotoRepositoryData>
    ): List<PhotoCardData> {
        val photoCardData = mutableListOf<PhotoCardData>()

        for (photoFromRepository in photosFromRepository) {
            photoCardData.add(
                PhotoCardData(
                    PhotoCollectionEnum.MediaStore,
                    when (photoFromRepository.mimeType) {
                        "jpg" -> PhotoImageTypeEnum.JPEG
                        "jpeg" -> PhotoImageTypeEnum.JPEG
                        "png" -> PhotoImageTypeEnum.PNG
                        "tif" -> PhotoImageTypeEnum.TIFF
                        else -> {
                            Timber.e(photoFromRepository.mimeType)
                            PhotoImageTypeEnum.UNKNOWN
                        }
                    },
                    photoFromRepository.uri,
                    exifInterface(context, photoFromRepository)
                        .getAttribute(ExifInterface.TAG_DATETIME),
                    exifInterface(context, photoFromRepository).latLong
                )
            )
        }

        return photoCardData
    }

    override fun exifInterface(
        context: Context,
        photoRepositoryData: PhotoRepositoryData
    ): ExifInterface {
        val inputStream = context.contentResolver.openInputStream(photoRepositoryData.uri)
        val exifInterface = ExifInterface(inputStream!!)
        inputStream.close()
        return exifInterface
    }
}
