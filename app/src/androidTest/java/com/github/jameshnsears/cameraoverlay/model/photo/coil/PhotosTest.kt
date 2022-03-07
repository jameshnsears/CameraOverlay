package com.github.jameshnsears.cameraoverlay.model.photo.coil


import android.widget.ImageView
import coil.ImageLoader
import coil.request.ImageRequest
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoResourcesUtility
import com.github.jameshnsears.cameraoverlay.model.photo.mediastore.MediaStoreMediator
import org.junit.Assert.assertNotNull
import org.junit.Test


class PhotosTest : PhotoResourcesUtility() {
//    @Test
    fun coil() {
        val picturesInMediaStore = MediaStoreMediator.picturesInMediaStore(context)

        var imageView = ImageView(context)

        val imageLoader = ImageLoader.Builder(context)
            .crossfade(false)
            .build()

        val request = ImageRequest.Builder(imageView.context)
            .data("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png")
            .target(imageView)
            .build()
        imageLoader.enqueue(request)

        ////////////////

        // .kt extension function on ImageView
        // i.e. content://media/external/images/media/247
//        imageView.load(picturesInMediaStore[0].uri)

        assertNotNull(imageView)

        //////////////////

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
