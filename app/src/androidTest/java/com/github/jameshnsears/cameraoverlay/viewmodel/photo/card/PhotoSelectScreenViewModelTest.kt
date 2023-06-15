package com.github.jameshnsears.cameraoverlay.viewmodel.photo.card

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.core.app.ActivityCompat
import androidx.test.rule.GrantPermissionRule
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoCollectionEnum
import com.github.jameshnsears.cameraoverlay.model.photo.card.PhotoCardData
import com.github.jameshnsears.cameraoverlay.model.photo.repository.mediastore.MediaStoreRepository
import com.github.jameshnsears.cameraoverlay.utility.CommonTestUtility
import com.github.jameshnsears.cameraoverlay.viewmodel.photo.PhotoSelectScreenViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Assert.assertNotEquals
import org.junit.Rule
import org.junit.Test

class PhotoSelectScreenViewModelTest : CommonTestUtility() {

    /*
given
when
then


in viewmodel
= query the repo (a pure Model) to get list of repo data
= convert list of repo data to list of ui data

in view
= pass in viewmodel & construct ui from list of viewmodel.ui data
= TODO when ui filter clicked

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

    PhotoCardViewModel(LocationManager, MediaStoreRepository)
    .getLocation
    .calculateDistance
    .getImageType

    --



    PhotoCard(navController: NavController, photoCardViewModel: PhotoCardViewModel)
    = n PhotoCard's all use the same ViewModel

================

- Create a ViewModel class that has a property for the list of data. For example,
if you want to display a list of books, you can have a property called books that is an array of Book objects.

- Initialize the ViewModel with some data or fetch it from a source, such as a database or an API. You can use
methods like viewDidLoad or viewWillAppear to do this in your ViewController.

- Bind the ViewModel to the View. This means that whenever the ViewModel changes its data, the View will reflect
those changes automatically.

- Use the list of data from the ViewModel to populate the View elements

================

class MyViewModel : ViewModel() {
private val _uiItems: MutableStateFlow<List<UiItem>> = MutableStateFlow(emptyList())
val uiItems: StateFlow<List<UiItem>> = _uiItems

fun fetchData() {
    // Fetch data from a data source
    val dataList: List<Data> = // Fetch data

    // Transform data into UI items
    val uiItemList = dataList.map { data ->
        when (data.type) {
            "TypeA" -> UiItem.TypeA(data.text)
            "TypeB" -> UiItem.TypeB(data.value)
            // Map other data types to corresponding UiItem types
        }
    }

    // Update the MutableStateFlow with the list of UI items
    _uiItems.value = uiItemList
}

// Rest of the code remains the same...
}

=================

import androidx.lifecycle.ViewModel

class MyViewModel(private val myModel: MyModel) : ViewModel() {
private var view: MyView? = null

fun attachView(view: MyView) {
    this.view = view
}

fun detachView() {
    view = null
}

fun fetchData() {
    // Perform data fetching from the model
    val data = myModel.getData()

    // Update the view with the fetched data
    view?.showData(data)
}
}

==============

In Android Jetpack Compose, the recommended practice is to have one ViewModel per screen or
screen-like component. This approach helps to keep the responsibilities of each ViewModel
focused and ensures that the data and behavior associated with a specific screen are contained
within a single class.

Having multiple ViewModels for each individual object or element within a screen is generally
not necessary. Instead, you can use the same ViewModel for different elements within a screen
if they share the same data or behavior. By doing so, you maintain a more cohesive and manageable
architecture.
     */

    val mediaStoreRepository = MediaStoreRepository()
    val photoSelectScreenViewModel = PhotoSelectScreenViewModel(mockk<LocationManager>())



    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            fail("PackageManager.PERMISSION_DENIED")
        }
    }

    @Test
    fun getLocation() = runTest {
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val photoSelectScreenViewModel = PhotoSelectScreenViewModel(locationManager)

        assertEquals(
            photoSelectScreenViewModel.defaultLocation,
            photoSelectScreenViewModel.locationState.value
        )

        // so the asStateFlow fires
        composeTestRule.apply {
            photoSelectScreenViewModel.requestLocation()

//            val location = launch { photoCardViewModel.getLocation() }.join()
//            val location = async { photoCardViewModel.getLocation() }.await()
            val location =
                withContext(Dispatchers.Default) { photoSelectScreenViewModel.getLocation() }

            assertNotEquals(
                photoSelectScreenViewModel.defaultLocation,
                location
            )

            // visit google's campus
            assertEquals(37.421998333333335, location.latitude)
            assertEquals(-122.08400000000002, location.longitude)
        }
    }

    @Test
    fun calculateDistance() = runTest {
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val photoSelectScreenViewModel = PhotoSelectScreenViewModel(locationManager)

        composeTestRule.apply {
            val locationFrom = Location("")
            locationFrom.latitude = 37.421998333333335
            locationFrom.longitude = -122.08400000000002

            val locationTo = Location("")
            locationTo.latitude = 51.50509557482467
            locationTo.longitude = -0.07566490651243916

            assertEquals(
                8658391.0f,
                photoSelectScreenViewModel.calculateDistance(locationFrom, locationTo)
            )
        }
    }
}
