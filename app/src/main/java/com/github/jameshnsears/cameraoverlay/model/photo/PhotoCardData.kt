package com.github.jameshnsears.cameraoverlay.model.photo


data class PhotoCardData(
    val source: PhotoCollection,
    val type: String,
    val imageModel: Any?,   // uri / url
    val whenTaken: String,
    val gps: String,
    val photoId: Int
)
