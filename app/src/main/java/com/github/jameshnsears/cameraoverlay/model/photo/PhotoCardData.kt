package com.github.jameshnsears.cameraoverlay.model.photo

data class PhotoCardData(
    val collection: PhotoCollection,
    val imageType: PhotoImageType,
    val imageModel: Any?, // uri / url
    val whenTaken: String,
    val gpsWhereTaken: String,
    val photoId: Int,
)
