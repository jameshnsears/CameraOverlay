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
import com.github.jameshnsears.cameraoverlay.model.permission.PermissionArea
import com.github.jameshnsears.cameraoverlay.model.permission.PermissionPrompt
import com.github.jameshnsears.cameraoverlay.permissions.R
import com.github.jameshnsears.cameraoverlay.viewmodel.permission.ViewModelPermission

@Composable
fun PermissionButtonStorage(viewModelPermission: ViewModelPermission) {
    var buttonEnabled = remember {
        mutableStateOf(viewModelPermission.permissionButtonEnabled(PermissionArea.STORAGE))
    }
    var permissionPrompt = remember {
        mutableStateOf(viewModelPermission.permissionPrompt(PermissionArea.STORAGE))
    }

    val permissionDeniedMessage =
        stringResource(R.string.permissions_denial_mandatory) as CharSequence

    val launcherRequestPermissionAccessPhotos =
        launcherRequestPermissionAccessPhotos(
            viewModelPermission,
            buttonEnabled,
            permissionPrompt,
            LocalContext.current,
            permissionDeniedMessage
        )

    val launcherAppInfoAccessPhotos =
        launcherAppInfoAccessPhotos(
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
            viewModelPermission.rememberPermissionRequest(PermissionArea.STORAGE)

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
        stringResource(R.string.permissions_files_and_media)
    )
}

@Composable
private fun launcherRequestPermissionAccessPhotos(
    viewModelPermission: ViewModelPermission,
    buttonEnabled: MutableState<Boolean>,
    permissionPrompt: MutableState<PermissionPrompt>,
    context: Context,
    permissionDeniedMessage: CharSequence
): ManagedActivityResultLauncher<String, Boolean> {
    val launcherRequestPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        buttonEnabled.value = viewModelPermission.permissionButtonEnabled(PermissionArea.STORAGE)
        permissionPrompt.value = viewModelPermission.permissionPrompt(PermissionArea.STORAGE)

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
    viewModelPermission: ViewModelPermission,
    buttonEnabled: MutableState<Boolean>,
    context: Context,
    permissionDeniedMessage: CharSequence
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val launcherAppInfo = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        buttonEnabled.value = viewModelPermission.permissionButtonEnabled(PermissionArea.STORAGE)

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
