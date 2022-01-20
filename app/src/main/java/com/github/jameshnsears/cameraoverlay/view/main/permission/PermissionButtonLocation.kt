package com.github.jameshnsears.cameraoverlay.view.main.permission

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.model.permission.PermissionArea
import com.github.jameshnsears.cameraoverlay.model.permission.PermissionPrompt
import com.github.jameshnsears.cameraoverlay.viewmodel.main.ViewModelMainScreen

@Composable
fun PermissionButtonLocation(viewModelMainScreen: ViewModelMainScreen) {
    var buttonEnabled =
        remember { mutableStateOf(viewModelMainScreen.permissionButtonEnabled(PermissionArea.LOCATION)) }
    var permissionPrompt =
        remember { mutableStateOf(viewModelMainScreen.permissionPrompt(PermissionArea.LOCATION)) }

    val launcherRequestPermissionShowLocation =
        launcherRequestPermissionAccessLocation(
            viewModelMainScreen,
            buttonEnabled,
            permissionPrompt
        )

    val launcherAppInfoShowLocation =
        launcherAppInfoAccessLocation(
            viewModelMainScreen,
            buttonEnabled
        )

    val packageName = LocalContext.current.packageName

//    if (BuildConfig.DEBUG) {
//        Text(text = "${buttonEnabled.value} : ${permissionPrompt.value}")
//    }

    PermissionButton(
        {
            viewModelMainScreen.rememberPermissionRequest(PermissionArea.LOCATION)

            if (buttonEnabled.value) {
                if (permissionPrompt.value == PermissionPrompt.PERMISSION_DIALOG) {
                    launcherRequestPermissionShowLocation.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                } else {
                    launcherAppInfoShowLocation
                        .launch(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.parse("package:$packageName")
                            )
                        )
                }
            }
        },
        buttonEnabled.value,
        Icons.Outlined.LocationOn,
        stringResource(R.string.main_show_distance)
    )
}

@Composable
private fun launcherRequestPermissionAccessLocation(
    viewModelMainScreen: ViewModelMainScreen,
    buttonEnabled: MutableState<Boolean>,
    permissionPrompt: MutableState<PermissionPrompt>
) = rememberLauncherForActivityResult(
    ActivityResultContracts.RequestMultiplePermissions()
) {

    buttonEnabled.value = viewModelMainScreen.permissionButtonEnabled(PermissionArea.LOCATION)
    permissionPrompt.value = viewModelMainScreen.permissionPrompt(PermissionArea.LOCATION)
}

@Composable
fun launcherAppInfoAccessLocation(
    viewModelMainScreen: ViewModelMainScreen,
    buttonEnabled: MutableState<Boolean>
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val launcherAppInfo = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        buttonEnabled.value = viewModelMainScreen.permissionButtonEnabled(PermissionArea.LOCATION)
    }
    return launcherAppInfo
}
