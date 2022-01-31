package com.github.jameshnsears.cameraoverlay.model.photo

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.model.utils.MethodLineLoggingTree
import com.github.jameshnsears.cameraoverlay.permissions.BuildConfig
import com.github.jameshnsears.cameraoverlay.view.photo.ButtonsLocation
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import timber.log.Timber

class PhotoSelectTest {
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule
    val composeTestRule = createComposeRule()

//    @Test
    fun browseStorage() {
        if (BuildConfig.DEBUG && Timber.treeCount == 0) {
            Timber.plant(MethodLineLoggingTree())
        }

        composeTestRule.setContent {
            ButtonsLocation()
        }

        val browseStorageButtonText =
            context.resources.getString(R.string.select_photo_screen_picker)

        composeTestRule.onNodeWithText(browseStorageButtonText).performClick()

//        composeTestRule.waitUntil {
//            composeTestRule.onAllNodesWithText(browseStorageButtonText).fetchSemanticsNodes(false)
//                .isNotEmpty()
//        }

        copyImageIntoExternalStoragePicturesFolder()

        // https://developer.android.com/training/data-storage/shared/media
        // https://developer.android.com/training/data-storage/shared/documents-files#perform-operations

        // Hence:
        // 1. allow user to select "External Storage" (using Storage Access Framework)
        //    = + user chooses to select a single file OR a folder
        // 2. allow user to select "MediaStore" (using Android's own database)
        //    =

        // TODO have a help button that shows which image formats supported + which can contain exif
        // also tell user what the icons show + what can be selected

        // TODO also enable the app to receive an image via a "Share"

        // TODO have different images formats that the test can select

    }

    fun mediaStore() {
        // https://www.cobeisfresh.com/stories/taming-file-storage-on-android-part-2
        // https://developer.android.com/training/data-storage/shared/media
    }

    @Test
    fun copyImageIntoExternalStoragePicturesFolder() {
        val sourceFilename = "eiffel_tower.jpg"
        val targetFilename = "/storage/emulated/0/Pictures/$sourceFilename"

        Files.copy(
            javaClass.classLoader.getResourceAsStream(sourceFilename),
            Paths.get(targetFilename),
            StandardCopyOption.REPLACE_EXISTING
        )
    }

    @Before
    fun emptySharedStoragePictures() {
        val targetFile = File("/storage/emulated/0/Pictures/")
        targetFile.deleteRecursively()
    }
}