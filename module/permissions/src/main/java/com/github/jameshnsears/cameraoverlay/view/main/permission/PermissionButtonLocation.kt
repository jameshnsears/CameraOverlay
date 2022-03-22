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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.github.jameshnsears.cameraoverlay.model.permission.PermissionArea
import com.github.jameshnsears.cameraoverlay.model.permission.PermissionPrompt
import com.github.jameshnsears.cameraoverlay.permissions.R
import com.github.jameshnsears.cameraoverlay.viewmodel.permission.ViewModelPermission

@Composable
fun PermissionButtonLocation(viewModelPermission: ViewModelPermission) {
    val buttonEnabled =
        remember {
            mutableStateOf(viewModelPermission.permissionButtonEnabled(PermissionArea.LOCATION))
        }
    val permissionPrompt =
        remember {
            mutableStateOf(viewModelPermission.permissionPrompt(PermissionArea.LOCATION))
        }

    val permissionDeniedMessage =
        stringResource(R.string.permissions_denial_optional) as CharSequence

    val launcherRequestPermissionShowLocation =
        launcherRequestPermissionAccessLocation(
            viewModelPermission,
            buttonEnabled,
            permissionPrompt,
            LocalContext.current,
            permissionDeniedMessage
        )

    val launcherAppInfoShowLocation =
        launcherAppInfoAccessLocation(
            viewModelPermission,
            buttonEnabled,
            LocalContext.current,
            permissionDeniedMessage
        )

    val packageName = LocalContext.current.packageName

//    if (BuildConfig.DEBUG) {
//        Text(text = "${buttonEnabled.value} : ${permissionPrompt.value}")
//    }

    PermissionButton(
        {
            viewModelPermission.rememberPermissionRequest(PermissionArea.LOCATION)

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
        stringResource(R.string.permissions_location)
    )
}

@Composable
private fun launcherRequestPermissionAccessLocation(
    viewModelPermission: ViewModelPermission,
    buttonEnabled: MutableState<Boolean>,
    permissionPrompt: MutableState<PermissionPrompt>,
    context: Context,
    permissionDeniedMessage: CharSequence
) = rememberLauncherForActivityResult(
    ActivityResultContracts.RequestMultiplePermissions()
) {

    buttonEnabled.value = viewModelPermission.permissionButtonEnabled(PermissionArea.LOCATION)
    permissionPrompt.value = viewModelPermission.permissionPrompt(PermissionArea.LOCATION)

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
    viewModelPermission: ViewModelPermission,
    buttonEnabled: MutableState<Boolean>,
    context: Context,
    permissionDeniedMessage: CharSequence
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val launcherAppInfo = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        buttonEnabled.value = viewModelPermission.permissionButtonEnabled(PermissionArea.LOCATION)

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
