package com.github.jameshnsears.cameraoverlay.model.photo.repository.mediastore

import com.github.jameshnsears.cameraoverlay.model.photo.PhotoCollectionEnum
import com.github.jameshnsears.cameraoverlay.utility.CommonTestUtility
import com.github.jameshnsears.cameraoverlay.utility.MediaStoreTestUtility
import java.util.*
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import org.junit.Before
import org.junit.Test

class MediaStoreRepositoryTest : CommonTestUtility() {
    @Before
    fun setUpMediaStore() {
        confirmEnvironmentCompatible()
        MediaStoreTestUtility().setUpMediaStore(context)
    }

    @Test
    fun queryMediaStore() {
        val mediaStoreRepository = MediaStoreRepository()
        val photosRepositoryData = mediaStoreRepository.queryPhotoRepository(context)
        assertEquals(7, photosRepositoryData.size)

        val photoCardData = mediaStoreRepository.convertPhotoRepositoryDataIntoPhotoCardData(
            context,
            photosRepositoryData
        )
        assertEquals(7, photoCardData.size)

        assertEquals(PhotoCollectionEnum.MediaStore, photoCardData[0].collection)
        assertNotNull(photoCardData[0].imageUri)

        assertEquals("image/jpeg", photoCardData[0].imageType)
        assertEquals("2021:09:14 17:08:31", photoCardData[0].whenTaken)
        assertNull(photoCardData[0].whereTaken)

        assertEquals("image/x-ms-bmp", photoCardData[1].imageType)
        assertNull(photoCardData[1].whenTaken)
        assertNull(photoCardData[1].whereTaken)

        assertEquals("image/gif", photoCardData[2].imageType)
        assertNull(photoCardData[2].whenTaken)
        assertNull(photoCardData[2].whereTaken)

        assertEquals("image/jpeg", photoCardData[3].imageType)
        assertEquals("2021:09:14 17:07:37", photoCardData[3].whenTaken)
        assertEquals("[52.5186, 13.3763]", Arrays.toString(photoCardData[3].whereTaken))

        assertEquals("image/png", photoCardData[4].imageType)
        assertNull(photoCardData[4].whenTaken)
        assertNull(photoCardData[4].whereTaken)

        assertEquals("image/webp", photoCardData[5].imageType)
        assertNull(photoCardData[5].whenTaken)
        assertNull(photoCardData[5].whereTaken)

        assertEquals("image/jpeg", photoCardData[6].imageType)
        assertEquals("2021:09:14 17:09:11", photoCardData[6].whenTaken)
        assertEquals("[51.5055, -0.075406]", Arrays.toString(photoCardData[6].whereTaken))
    }
}
