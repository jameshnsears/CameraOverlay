package com.github.jameshnsears.cameraoverlay.utility

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream
import timber.log.Timber

class MediaStoreTestUtility {
    // android, natively, only supports image types: JPEG; GIF; PNG; BMP; WebP
    private var images = arrayOf(
        "eiffel_tower.jpg",
        "parthenon.bmp",
        "pizza.gif",
        "reichstag.jpeg",
        "rialto.png",
        "sofia.webp",
        "tower_bridge.jpg"
    )

    fun setUpMediaStore(context: Context) {
        val imagesInMediaStore = imagesInMediaStore(context)

        for (image in images) {
            if (!imagesInMediaStore.contains(image)) {
                copyImageToExternalStorage(context, image)
            }
        }
    }

    private fun imagesInMediaStore(context: Context): List<String> {
        val images = mutableListOf<String>()
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )
        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            while (it.moveToNext()) {
                val imagePath = it.getString(columnIndex)
                images.add(imagePath.substringAfterLast("/"))
            }
        }
        return images
    }

    private fun copyImageToExternalStorage(context: Context, fileName: String) {
        Timber.d(fileName)

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)

            // https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types
            when (fileName.substringAfterLast('.', "")) {
                "bmp" -> {
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/bmp")
                }
                "gif" -> {
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/gif")
                }
                "jpg" -> {
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                }
                "jpeg" -> {
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                }
                "png" -> {
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                }
                "webp" -> {
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/webp")
                }
            }

            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        val uri = context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )

        if (uri != null) {
            val outputStream = context.contentResolver.openOutputStream(uri)
            if (outputStream != null) {
                val fileName = "MediaStore/$fileName"
                Timber.d(fileName)

                val inputStream =
                    this::class.java.classLoader!!.getResourceAsStream(fileName)
                outputStream.write(inputStream.readBytes())
                outputStream.close()
            }

            MediaScannerConnection.scanFile(
                context,
                arrayOf(uri.path),
                null,
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
