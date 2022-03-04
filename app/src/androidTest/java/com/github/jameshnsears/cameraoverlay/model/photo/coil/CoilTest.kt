package com.github.jameshnsears.cameraoverlay.model.photo.coil

import android.widget.ImageView
import coil.ImageLoader
import coil.request.ImageRequest
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoResourcesUtility
import com.github.jameshnsears.cameraoverlay.model.photo.mediastore.MediaStoreMediator
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test

class CoilTest : PhotoResourcesUtility() {
    @Test
    fun coil() {
        runBlocking {
            val picturesInMediaStore = MediaStoreMediator.picturesInMediaStore(context)

            val imageLoader = ImageLoader.Builder(context)
                .availableMemoryPercentage(0.25)
                .crossfade(true)
                .build()

            var imageView = ImageView(context)

            val request = ImageRequest.Builder(context)
                .data(picturesInMediaStore[0].uri)
                .target(imageView)
                .build()

            val result = imageLoader.enqueue(request)

            assertNotNull(result)
        }
    }
}
