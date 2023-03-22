package com.github.jameshnsears.cameraoverlay.model.photo.card

import android.net.Uri
import coil.ImageLoader
import coil.request.ImageRequest
import com.github.jameshnsears.cameraoverlay.model.photo.repository.mediastore.MediaStoreRepository
import com.github.jameshnsears.cameraoverlay.utility.MediaStoreUtility
import com.github.jameshnsears.cameraoverlay.utility.TestUtility
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CoilTest : TestUtility() {
    @Test
    fun drawableFromHttpEndpoint() = runTest {
        val httpEndpointUtility = HttpEndpointUtility()
        httpEndpointUtility.start()

        launch {
            testDrawable("http://127.0.0.1:8080/MediaStore/eiffel_tower.jpg")
        }.join()

        httpEndpointUtility.shutdown()
    }

    private suspend fun TestScope.testDrawable(uri: Any) {
        val request = ImageRequest.Builder(context)
            .data(uri)
            .build()

        val imageLoader = ImageLoader.Builder(context)
            .crossfade(true)
            .allowHardware(false)
            .build()

        // no drawable contains exif
        val drawable = imageLoader.execute(request).drawable

        advanceUntilIdle()

        if (drawable != null) {
            assertEquals(725, drawable.intrinsicWidth)
            assertEquals(1342, drawable.intrinsicHeight)
        } else {
            fail()
        }
    }

    @Test
    fun drawableFromMediaStore() = runTest {
        MediaStoreUtility().setUpMediaStore(context)

        val mediaStoreRepository = MediaStoreRepository()
        val photosRepositoryData = mediaStoreRepository.queryPhotoRepository(context)
        val photoCardData = mediaStoreRepository.convertRepositoryDataIntoCardData(
            context,
            photosRepositoryData
        )

        testDrawable(Uri.parse(photoCardData[0].imageUri.toString()))
    }
}
