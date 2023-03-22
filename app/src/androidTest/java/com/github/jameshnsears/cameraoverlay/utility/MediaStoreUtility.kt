package com.github.jameshnsears.cameraoverlay.utility

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Environment
import android.provider.MediaStore
import com.github.jameshnsears.cameraoverlay.model.photo.repository.mediastore.MediaStoreRepository
import java.io.File
import java.io.FileOutputStream
import timber.log.Timber

class MediaStoreUtility {
    private val repository = MediaStoreRepository()

    fun setUpMediaStore(context: Context) {
        if (repository.queryPhotoRepository(context).size != 3) {
            copyImageResourcesToExternalStorage(context)
        }
    }

    private var images = arrayOf(
        "eiffel_tower",
        "reichstag",
        "tower_bridge"
    )

    fun copyImageResourcesToExternalStorage(context: Context) {
        for (image in images) {
            copyImageToExternalStorage(context, image)
        }
    }

    private fun copyImageToExternalStorage(context: Context, imageName: String) {
        Timber.d("imageName=%s", imageName)

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, imageName)

            // https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")

            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        val uri = context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )

        if (uri != null) {
            val outputStream = context.contentResolver.openOutputStream(uri)
            if (outputStream != null) {
                val inputStream =
                    this::class.java.classLoader!!.getResourceAsStream("MediaStore/$imageName.jpg")
                outputStream.write(inputStream.readBytes())
                outputStream.close()
            }

            MediaScannerConnection.scanFile(
                context,
                arrayOf(uri.path),
                arrayOf("image/jpeg"),
                null
            )
        }
    }

    fun saveBitmap(context: Context, bitmapToSave: Bitmap, filename: String) {
        val outputStream = FileOutputStream(
            File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                filename
            )
        )

        bitmapToSave.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.close()
    }
}
