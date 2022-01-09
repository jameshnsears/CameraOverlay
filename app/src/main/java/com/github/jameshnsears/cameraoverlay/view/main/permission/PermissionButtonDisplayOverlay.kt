package com.github.jameshnsears.cameraoverlay.view.main.permission

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.viewmodel.main.ViewModelMainScreen
import timber.log.Timber

@Composable
fun PermissionButtonDisplayOverlay(viewModelMainScreen: ViewModelMainScreen) {
    val enabled =
        remember { mutableStateOf(viewModelMainScreen.permissionAllowedOverlay()) }
    Timber.d("enabled=%b", enabled.value)

    val context = LocalContext.current

    val permissionDeniedMessage =
        stringResource(R.string.permissions_denial_mandatory) as CharSequence

    val launcherAppInfoDisplayOverlay =
        launcherAppInfoDisplayOverlay(
            viewModelMainScreen,
            context,
            permissionDeniedMessage
        )

    PermissionButton(
        {
            if (!enabled.value) {
                launcherAppInfoDisplayOverlay.launch(
                    Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + context.packageName)
                    )
                )
            }
        },
        !viewModelMainScreen.permissionAllowedOverlay(),
        Icons.Outlined.Layers, stringResource(R.string.main_display_overlay)
    )
}

@Composable
fun launcherAppInfoDisplayOverlay(
    viewModel: ViewModelMainScreen,
    context: Context,
    permissionDeniedMessage: CharSequence
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val launcherAppInfo = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (!viewModel.permissionAllowedOverlay()) {
            Toast.makeText(
                context as Activity,
                permissionDeniedMessage,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    return launcherAppInfo
}
