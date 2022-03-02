package com.github.jameshnsears.cameraoverlay.model.photo.coil

import android.widget.ImageView
import coil.ImageLoader
import coil.request.ImageRequest
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoResourcesUtility
import com.github.jameshnsears.cameraoverlay.model.photo.mediastore.MediaStoreMediator
import io.mockk.mockk
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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

            // Executes the ImageRequest in the current coroutine and returns an ImageResult
            launch {
                val result = imageLoader.execute(request)
            }
        }
    }
}
