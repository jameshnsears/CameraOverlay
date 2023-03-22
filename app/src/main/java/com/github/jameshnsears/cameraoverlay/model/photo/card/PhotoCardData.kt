package com.github.jameshnsears.cameraoverlay.model.photo.card

import com.github.jameshnsears.cameraoverlay.model.photo.PhotoCollectionEnum

data class PhotoCardData(
    val collection: PhotoCollectionEnum,
    val imageType: String,
    val imageUri: Any,
    val dateTime: String?,
    val latLong: DoubleArray?
)
