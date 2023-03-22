package com.github.jameshnsears.cameraoverlay.model.photo.repository

import android.net.Uri

data class PhotoRepositoryData(
    val uri: Uri,
    val mimeType: String,
    val fileName: String
)
