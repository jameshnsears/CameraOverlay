package com.github.jameshnsears.cameraoverlay.model.photo.repository.mediastore

import com.github.jameshnsears.cameraoverlay.model.photo.PhotoCollectionEnum
import com.github.jameshnsears.cameraoverlay.utility.MediaStoreUtility
import com.github.jameshnsears.cameraoverlay.utility.TestUtility
import java.util.Arrays
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import org.junit.Test

class MediaStoreRepositoryTest : TestUtility() {
    @Test
    fun queryMediaStore() {
        MediaStoreUtility().setUpMediaStore(context)

        val mediaStoreRepository = MediaStoreRepository()
        val photosRepositoryData = mediaStoreRepository.queryPhotoRepository(context)
        assertEquals(3, photosRepositoryData.size)

        val photoCardData = mediaStoreRepository.convertRepositoryDataIntoCardData(
            context,
            photosRepositoryData
        )
        assertEquals(3, photoCardData.size)

        assertEquals(PhotoCollectionEnum.MediaStore, photoCardData[0].collection)
        assertEquals("image/jpeg", photoCardData[0].imageType)
        assertNotNull(photoCardData[0].imageUri)
        assertEquals("2021:09:14 17:08:31", photoCardData[0].dateTime)
        assertNull(photoCardData[0].latLong)

        assertEquals("2021:09:14 17:07:37", photoCardData[1].dateTime)
        assertEquals("[52.5186, 13.3763]", Arrays.toString(photoCardData[1].latLong))

        assertEquals("2021:09:14 17:09:11", photoCardData[2].dateTime)
        assertEquals("[51.5055, -0.075406]", Arrays.toString(photoCardData[2].latLong))
    }
}
