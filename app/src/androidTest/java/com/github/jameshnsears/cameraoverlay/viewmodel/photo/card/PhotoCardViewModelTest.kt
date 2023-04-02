package com.github.jameshnsears.cameraoverlay.viewmodel.photo.card

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.core.app.ActivityCompat
import androidx.test.rule.GrantPermissionRule
import com.github.jameshnsears.cameraoverlay.utility.CommonTestUtility
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Assert.assertNotEquals
import org.junit.Rule
import org.junit.Test

class PhotoCardViewModelTest : CommonTestUtility() {
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

        val photoCardViewModel = PhotoCardViewModel(locationManager)

        assertEquals(
            photoCardViewModel.defaultLocation,
            photoCardViewModel.locationState.value
        )

        // so the asStateFlow fires
        composeTestRule.apply {
            photoCardViewModel.requestLocation()

//            val location = launch { photoCardViewModel.getLocation() }.join()
//            val location = async { photoCardViewModel.getLocation() }.await()
            val location =
                withContext(Dispatchers.Default) { photoCardViewModel.getLocation() }

            assertNotEquals(
                photoCardViewModel.defaultLocation,
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

        val photoCardViewModel = PhotoCardViewModel(locationManager)

        composeTestRule.apply {
            val locationFrom = Location("")
            locationFrom.latitude = 37.421998333333335
            locationFrom.longitude = -122.08400000000002

            val locationTo = Location("")
            locationTo.latitude = 51.50509557482467
            locationTo.longitude = -0.07566490651243916

            assertEquals(
                8658391.0f,
                photoCardViewModel.calculateDistance(locationFrom, locationTo)
            )
        }
    }
}
