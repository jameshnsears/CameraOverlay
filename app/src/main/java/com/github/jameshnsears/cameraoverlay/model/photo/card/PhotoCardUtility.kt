package com.github.jameshnsears.cameraoverlay.model.photo.card

import android.content.Context
import androidx.exifinterface.media.ExifInterface
import com.github.jameshnsears.cameraoverlay.model.photo.repository.PhotoRepositoryData

class PhotoCardUtility {
    fun exifInterface(context: Context, photoRepositoryData: PhotoRepositoryData): ExifInterface {
        val inputStream = context.contentResolver.openInputStream(photoRepositoryData.uri)
        val exifInterface = ExifInterface(inputStream!!)
        inputStream.close()
        return exifInterface
    }

    fun distance() {
        TODO("Not yet implemented")
    }
}
