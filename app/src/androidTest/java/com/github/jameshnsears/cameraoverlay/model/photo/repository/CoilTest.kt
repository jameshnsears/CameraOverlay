package com.github.jameshnsears.cameraoverlay.model.photo.repository

import android.net.Uri
import coil.ImageLoader
import coil.request.ImageRequest
import com.github.jameshnsears.cameraoverlay.BuildConfig
import com.github.jameshnsears.cameraoverlay.model.photo.repository.mediastore.MediaStoreRepository
import com.github.jameshnsears.cameraoverlay.utility.CommonTestUtility
import com.github.jameshnsears.cameraoverlay.utility.MediaStoreTestUtility
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CoilTest : CommonTestUtility() {
    @Before
    fun setUp() {
        confirmEnvironmentCompatible()
        MediaStoreTestUtility().setUpMediaStore(context)
    }

    @Test
    fun getDrawableFromHttpEndpoint() = runTest {
        val httpEndpointUtility = HttpEndpointTestUtility()
        httpEndpointUtility.start()

        launch {
            // 10.0.2.2
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

        // drawable does NOT contain exif
        val drawable = imageLoader.execute(request).drawable

        advanceUntilIdle()

        if (drawable != null) {
            if (!BuildConfig.GITHUB_ACTION) {
                assertTrue(drawable.intrinsicWidth == 725)
                assertTrue(drawable.intrinsicHeight == 1342)
            }
        } else {
            fail()
        }
    }

//    @Test
//    fun getDrawableFromMediaStore() = runTest {
//        val mediaStoreRepository = MediaStoreRepository()
//        val photosRepositoryData = mediaStoreRepository.queryPhotoRepository(context)
//        val photoCardData = mediaStoreRepository.convertPhotoRepositoryDataIntoPhotoCardData(
//            context,
//            photosRepositoryData
//        )
//
//        testDrawable(Uri.parse(photoCardData[0].imageUri.toString()))
//    }
}
