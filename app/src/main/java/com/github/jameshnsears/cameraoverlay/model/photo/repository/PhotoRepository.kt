package com.github.jameshnsears.cameraoverlay.model.photo.repository

import android.content.Context
import com.github.jameshnsears.cameraoverlay.model.photo.card.PhotoCardData

interface PhotoRepository {
    fun queryPhotoRepository(context: Context): List<PhotoRepositoryData>

    fun convertRepositoryDataIntoCardData(
        context: Context,
        photosFromRepository: List<PhotoRepositoryData>
    ): List<PhotoCardData>
}
