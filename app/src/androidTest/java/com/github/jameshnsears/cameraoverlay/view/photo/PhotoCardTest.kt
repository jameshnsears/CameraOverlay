package com.github.jameshnsears.cameraoverlay.view.photo

import android.location.Location
import android.location.LocationManager
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoCollectionEnum
import com.github.jameshnsears.cameraoverlay.model.photo.card.PhotoCardData
import com.github.jameshnsears.cameraoverlay.model.photo.repository.mediastore.MediaStoreRepository
import com.github.jameshnsears.cameraoverlay.utility.ScreenshotTestUtility
import com.github.jameshnsears.cameraoverlay.viewmodel.photo.card.PhotoCardViewModel
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PhotoCardTest : ScreenshotTestUtility() {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        confirmEnvironmentCompatible()
    }

    @Test
    fun photoCard() {
        /*
        given
        when
        then
         */


        val photoCardData = PhotoCardData(
            PhotoCollectionEnum.MediaStore,
            "JPEG",
            "file://a/b",
            "1/1/1970",
            doubleArrayOf(1.0, 2.0)
        )

        /*
        mediaStoreRepository.queryPhotoRepository
        List<PhotoCardData> = mediaStoreRepository.convertPhotoRepositoryDataIntoPhotoCardData

        --

        PhotoCardViewModel(LocationManager, List<PhotoCardData>)
        .getLocation
        .calculateDistance
        .getImageType

        --

        // TODO inside the PhotoCardViewModel have mediaStoreRepository attribute!

        PhotoCard(navController: NavController, photoCardViewModel: PhotoCardViewModel)
        = n PhotoCard's all use the same ViewModel

- Create a ViewModel class that has a property for the list of data. For example,
  if you want to display a list of books, you can have a property called books that is an array of Book objects.

- Initialize the ViewModel with some data or fetch it from a source, such as a database or an API. You can use
  methods like viewDidLoad or viewWillAppear to do this in your ViewController.

- Bind the ViewModel to the View. This means that whenever the ViewModel changes its data, the View will reflect
  those changes automatically.

- Use the list of data from the ViewModel to populate the View elements


         */

        val mediaStoreRepository = MediaStoreRepository()
        val photoCardViewModel = PhotoCardViewModel(mockk<LocationManager>())


        val location = Location("")
        location.longitude = 52.5186
        location.latitude = 13.3763

        // TODO pass in a valid imageUri, so I see nice pic

        composeTestRule.setContent {
            PhotoCard(rememberNavController(),  photoCardData)
        }

        composeTestRule
            .onNodeWithContentDescription("GPS EXIF")
            .assertExists()

        composeTestRule
            .onNodeWithText(
                "1.0, 2.0"
            )
            .assertIsDisplayed()
    }
}
