package com.github.jameshnsears.cameraoverlay.model.photo.card

import com.github.jameshnsears.cameraoverlay.model.photo.PhotoCollectionEnum
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoImageTypeEnum

data class PhotoCardData(
    val collection: PhotoCollectionEnum,
    val imageType: PhotoImageTypeEnum,
    val imageUri: Any,
    val whenTaken: String?,
    val whereTaken: DoubleArray?
)
