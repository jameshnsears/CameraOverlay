package com.github.jameshnsears.cameraoverlay.model.photo

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.common.MethodLineLoggingTree
import com.github.jameshnsears.cameraoverlay.permissions.BuildConfig
import com.github.jameshnsears.cameraoverlay.view.photo.PhotoSelectScreen
import io.mockk.mockk
import org.junit.Assert.fail
import org.junit.Rule
import org.junit.Test
import timber.log.Timber

class PhotoSelectScreenTest: PhotoResourcesUtility() {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun buttonFilter() {
         composeTestRule.setContent {
            PhotoSelectScreen(mockk())
        }

        val filterButtonText =
            context.resources.getString(R.string.select_photo_filter)

        // https://developer.android.com/jetpack/compose/testing-cheatsheet
        composeTestRule.onNodeWithContentDescription(filterButtonText).performClick()

//        composeTestRule.waitUntil {
//            composeTestRule.onAllNodesWithText(browseStorageButtonText).fetchSemanticsNodes(false)
//                .isNotEmpty()
//        }

//        copyImageIntoExternalStoragePicturesFolder()

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

        fail("todo")
    }

    fun mediaStore() {
        // https://www.cobeisfresh.com/stories/taming-file-storage-on-android-part-2
        // https://developer.android.com/training/data-storage/shared/media
    }

//    @Test
//    fun buttonCollection() {
//         fail()
//    }
//
//    @Test
//    fun buttonSort() {
//         fail()
//    }
}
