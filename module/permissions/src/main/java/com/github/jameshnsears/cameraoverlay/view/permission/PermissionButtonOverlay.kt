package com.github.jameshnsears.cameraoverlay.view.permission

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
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.github.jameshnsears.cameraoverlay.model.permission.PermissionArea
import com.github.jameshnsears.cameraoverlay.permissions.R
import com.github.jameshnsears.cameraoverlay.viewmodel.permission.ViewModelPermission

@Composable
fun PermissionButtonOverlay(viewModelPermission: ViewModelPermission) {
    val buttonEnabled = remember {
        mutableStateOf(
            viewModelPermission.permissionButtonEnabled(
                PermissionArea.OVERLAY
            )
        )
    }

    buttonEnabled.value = viewModelPermission.permissionButtonEnabled(PermissionArea.OVERLAY)

    val context = LocalContext.current

    val permissionDeniedMessage =
        stringResource(R.string.permissions_denial_mandatory) as CharSequence

    val launcherAppInfoDisplayOverlay =
        launcherAppInfoOverlay(
            viewModelPermission,
            buttonEnabled,
            context,
            permissionDeniedMessage
        )

//    if (BuildConfig.DEBUG) {
//        Text(text = "${buttonEnabled.value}")
//    }

    PermissionButton(
        {
            if (buttonEnabled.value) {
                launcherAppInfoDisplayOverlay.launch(
                    Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + context.packageName)
                    )
                )
            }
        },
        buttonEnabled.value,
        Icons.Outlined.Layers, stringResource(R.string.permissions_display_overlay)
    )
}

@Composable
fun launcherAppInfoOverlay(
    viewModelPermission: ViewModelPermission,
    buttonEnabled: MutableState<Boolean>,
    context: Context,
    permissionDeniedMessage: CharSequence
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val launcherAppInfo = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        buttonEnabled.value = viewModelPermission.permissionButtonEnabled(PermissionArea.OVERLAY)

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
