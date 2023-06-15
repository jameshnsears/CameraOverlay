package com.github.jameshnsears.cameraoverlay.model.photo.card

import com.github.jameshnsears.cameraoverlay.model.photo.PhotoCollectionEnum
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoImageTypeEnum

class PhotoCardDataSample {
    companion object {
        val photoCardDataSample01 = PhotoCardData(
            PhotoCollectionEnum.MediaStore,
            PhotoImageTypeEnum.JPEG,
            "file://a/b",
            "1/1/1970",
            doubleArrayOf(52.5186, 13.3763)
        )

        val photoCardDataSample02 = PhotoCardData(
            PhotoCollectionEnum.MediaStore,
            PhotoImageTypeEnum.TIFF,
            "file://a/b",
            "2/1/1970",
            doubleArrayOf(1.0, 2.0)
        )
    }
}