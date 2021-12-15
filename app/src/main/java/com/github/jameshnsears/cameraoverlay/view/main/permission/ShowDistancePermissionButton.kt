package com.github.jameshnsears.cameraoverlay.view.main.permission

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.viewmodel.MainScreenViewModel

@Composable
fun ShowDistancePermissionButton(mainScreenViewModel: MainScreenViewModel) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
            }
            else -> {
                // No location access granted.
                // TODO display dialog
            }
        }
    }

    // TODO foreground location service: https://developer.android.com/training/location/permissions#foreground

    PermissionElevatedButton({
        launcher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }, false, Icons.Outlined.LocationOn, stringResource(R.string.main_show_distance))

}
