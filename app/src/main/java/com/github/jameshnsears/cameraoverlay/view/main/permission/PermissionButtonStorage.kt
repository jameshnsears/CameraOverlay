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
import androidx.compose.material.icons.outlined.Folder
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
fun PermissionButtonStorage(viewModelMainScreen: ViewModelMainScreen) {
    var buttonEnabled = remember { mutableStateOf(viewModelMainScreen.permissionButtonEnabled(PermissionArea.STORAGE)) }
    var permissionPrompt = remember { mutableStateOf(viewModelMainScreen.permissionPrompt(PermissionArea.STORAGE)) }

    val permissionDeniedMessage =
        stringResource(R.string.permissions_denial_mandatory) as CharSequence

    val launcherRequestPermissionAccessPhotos =
        launcherRequestPermissionAccessPhotos(
            viewModelMainScreen,
            buttonEnabled,
            permissionPrompt,
            LocalContext.current,
            permissionDeniedMessage
        )

    val launcherAppInfoAccessPhotos =
        launcherAppInfoAccessPhotos(
            viewModelMainScreen,
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
            viewModelMainScreen.rememberPermissionRequest(PermissionArea.STORAGE)

            if (buttonEnabled.value) {
                if (permissionPrompt.value == PermissionPrompt.PERMISSION_DIALOG) {
                    launcherRequestPermissionAccessPhotos
                        .launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                } else {
                    launcherAppInfoAccessPhotos
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
        Icons.Outlined.Folder,
        stringResource(R.string.main_access_photos)
    )
}

@Composable
private fun launcherRequestPermissionAccessPhotos(
    viewModelMainScreen: ViewModelMainScreen,
    buttonEnabled: MutableState<Boolean>,
    permissionPrompt: MutableState<PermissionPrompt>,
    context: Context,
    permissionDeniedMessage: CharSequence
): ManagedActivityResultLauncher<String, Boolean> {
    val launcherRequestPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        buttonEnabled.value = viewModelMainScreen.permissionButtonEnabled(PermissionArea.STORAGE)
        permissionPrompt.value = viewModelMainScreen.permissionPrompt(PermissionArea.STORAGE)

        if (buttonEnabled.value) {
            Toast.makeText(
                context as Activity,
                permissionDeniedMessage,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    return launcherRequestPermission
}

@Composable
fun launcherAppInfoAccessPhotos(
    viewModelMainScreen: ViewModelMainScreen,
    buttonEnabled: MutableState<Boolean>,
    context: Context,
    permissionDeniedMessage: CharSequence
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val launcherAppInfo = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        buttonEnabled.value = viewModelMainScreen.permissionButtonEnabled(PermissionArea.STORAGE)

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
