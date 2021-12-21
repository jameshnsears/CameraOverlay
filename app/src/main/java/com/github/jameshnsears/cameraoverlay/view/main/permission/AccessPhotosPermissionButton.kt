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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.viewmodel.MainScreenViewModel
import timber.log.Timber

@Composable
fun Lifecycle.observeAsSate(): State<Lifecycle.Event> {
    val state = remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(this) {
        val observer = LifecycleEventObserver { _, event ->
            state.value = event
        }

        this@observeAsSate.addObserver(observer)

        onDispose {
            this@observeAsSate.removeObserver(observer)
        }
    }

    return state
}

@Composable
fun AccessPhotosPermissionButton(viewModel: MainScreenViewModel) {
    val countDeny: Int by viewModel.countDenyAccessPhotos.collectAsState()
    Timber.d("countDeny=%d", countDeny)

    val enabled = remember { mutableStateOf(!viewModel.isPermissionGrantedAccessPhotos()) }

    val context = LocalContext.current
    val permissionDeniedMessage = stringResource(R.string.permissions_denial_msg) as CharSequence

    val launcherRequestPermission =
        launcherRequestPermission(viewModel, enabled, context, permissionDeniedMessage)

    val launcherAppInfo = launcherAppInfo(viewModel, enabled, context, permissionDeniedMessage)

    val lifeCycleState = LocalLifecycleOwner.current.lifecycle.observeAsSate()
    Timber.d("lifeCycleState = ${lifeCycleState.value.name}")

    PermissionElevatedButton(
        {
            if (viewModel.isPermissionGrantedAccessPhotos()) {
                enabled.value = false
            } else {
                if (countDeny < 2) {
                    Timber.d("RequestPermission")
                    launcherRequestPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                } else {
                    Timber.d("AppInfo")
                    launcherAppInfo.launch(
                        Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:" + context.packageName)
                        )
                    )
                }
            }
        },
        enabled.value,
        Icons.Outlined.Folder,
        stringResource(R.string.main_access_photos)
    )
}

@Composable
fun launcherRequestPermission(
    viewModel: MainScreenViewModel,
    enabled: MutableState<Boolean>,
    context: Context,
    permissionDeniedMessage: CharSequence
): ManagedActivityResultLauncher<String, Boolean> {
    val launcherRequestPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        handleLauncherResponse(viewModel, enabled, context, permissionDeniedMessage)
    }
    return launcherRequestPermission
}

@Composable
fun launcherAppInfo(
    viewModel: MainScreenViewModel,
    enabled: MutableState<Boolean>,
    context: Context,
    permissionDeniedMessage: CharSequence
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val launcherAppInfo = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        handleLauncherResponse(viewModel, enabled, context, permissionDeniedMessage)
    }
    return launcherAppInfo
}

fun handleLauncherResponse(
    viewModel: MainScreenViewModel,
    enabled: MutableState<Boolean>,
    context: Context,
    permissionDeniedMessage: CharSequence
) {
    if (viewModel.isPermissionGrantedAccessPhotos()) {
        enabled.value = false
    } else {
        viewModel.denyPermissionAccessPhotos()

        Toast.makeText(
            context as Activity,
            permissionDeniedMessage,
            Toast.LENGTH_SHORT
        ).show()
    }
}
