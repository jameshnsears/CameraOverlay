package com.github.jameshnsears.cameraoverlay.model.photo.coil

import android.widget.ImageView
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoResourcesUtility
import com.github.jameshnsears.cameraoverlay.model.photo.mediastore.MediaStoreMediator
import org.junit.Assert.assertNotNull
import org.junit.Test

class CoilTest : PhotoResourcesUtility() {
    @Test
    fun coil() {
        val picturesInMediaStore = MediaStoreMediator.picturesInMediaStore(context)

        // https://github.com/coil-kt/coil

        // .kt extension function on ImageView
        var imageView = ImageView(context)
        // TODO use a url not uri
        imageView.load(picturesInMediaStore[0].uri)
        assertNotNull(imageView)

        //////////////////

        // https://github.com/coil-kt/coil/issues/559
        // https://coil-kt.github.io/coil/image_pipeline/#fetchers
        imageView.load(picturesInMediaStore[0].uri) { // content://media/external/video/media/6945
//            fetcher(VideoFrameUriFetcher(context))
        }

        /*
        Preload:
        val image = Coil.get(imageUrl)

        imageView.load(File("/path/to/ironman.png"))

        transformations(CircleCropTransformation())
        transformations(RoundedCornersTransformation(25f))
        transformations(GrayscaleTransformation())
        transformations(BlurTransformation(applicationContext))
        transformations(BlurTransformation(applicationContext, 5f))
         */
    }
}
