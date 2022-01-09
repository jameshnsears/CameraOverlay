package com.github.jameshnsears.cameraoverlay.view.main.permission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.github.jameshnsears.cameraoverlay.BuildConfig
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

    val permissionDeniedMessage =
        stringResource(R.string.permissions_denial_optional) as CharSequence

    val launcherRequestPermissionShowLocation =
        launcherRequestPermissionAccessLocation(
            viewModelMainScreen,
            buttonEnabled,
            permissionPrompt,
            LocalContext.current,
            permissionDeniedMessage
        )

    val launcherAppInfoShowLocation =
        launcherAppInfoAccessLocation(
            viewModelMainScreen,
            buttonEnabled,
            LocalContext.current,
            permissionDeniedMessage
        )

    val packageName = LocalContext.current.packageName

    if (BuildConfig.DEBUG) {
        Text(text = "${buttonEnabled.value} : ${permissionPrompt.value}")
    }

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
    permissionPrompt: MutableState<PermissionPrompt>,
    context: Context,
    permissionDeniedMessage: CharSequence
) = rememberLauncherForActivityResult(
    ActivityResultContracts.RequestMultiplePermissions()
) {
    buttonEnabled.value = viewModelMainScreen.permissionButtonEnabled(PermissionArea.LOCATION)
    permissionPrompt.value = viewModelMainScreen.permissionPrompt(PermissionArea.LOCATION)

    if (buttonEnabled.value) {
        Toast.makeText(
            context as Activity,
            permissionDeniedMessage,
            Toast.LENGTH_SHORT
        ).show()
    }
}

@Composable
fun launcherAppInfoAccessLocation(
    viewModelMainScreen: ViewModelMainScreen,
    buttonEnabled: MutableState<Boolean>,
    context: Context,
    permissionDeniedMessage: CharSequence
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val launcherAppInfo = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        buttonEnabled.value = viewModelMainScreen.permissionButtonEnabled(PermissionArea.LOCATION)

        if (buttonEnabled.value) {
            Toast.makeText(
                context as Activity,
                permissionDeniedMessage,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    return launcherAppInfo
}