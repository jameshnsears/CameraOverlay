package com.github.jameshnsears.cameraoverlay.model.photo.coil

import android.graphics.drawable.BitmapDrawable
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import coil.ImageLoader
import coil.request.ImageRequest
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoResourcesUtility
import com.github.jameshnsears.cameraoverlay.model.photo.mediastore.MediaStoreMediator
import com.squareup.picasso.Picasso
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

    @Test
    fun picasso() {
        val picturesInMediaStore = MediaStoreMediator.picturesInMediaStore(context)

        var imageView = ImageView(context)

        // https://developer.android.com/guide/background

        // TODO https://github.com/diffplug/spotless/tree/main/plugin-gradle

        // TODO put mediastore db calls in a coroutine, as db calls expensive

        // Method call should happen from the main thread.
        Handler(Looper.getMainLooper()).post {
//            Picasso.get().load(picturesInMediaStore[0].uri).into(imageView)
              Picasso.get().load("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png").into(imageView)

            val bd = imageView.drawable as BitmapDrawable
            val b = bd.bitmap
        }



        assertNotNull(imageView)
    }
}
