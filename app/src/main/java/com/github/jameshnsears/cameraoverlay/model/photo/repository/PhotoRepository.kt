package com.github.jameshnsears.cameraoverlay.model.photo.repository

import android.content.Context
import androidx.exifinterface.media.ExifInterface
import com.github.jameshnsears.cameraoverlay.model.photo.card.PhotoCardData

interface PhotoRepository {
    fun queryPhotoRepository(context: Context): List<PhotoRepositoryData>

    fun convertPhotoRepositoryDataIntoPhotoCardData(
        context: Context,
        photosFromRepository: List<PhotoRepositoryData>
    ): List<PhotoCardData>

    fun exifInterface(
        context: Context,
        photoRepositoryData: PhotoRepositoryData
    ): ExifInterface
}
