package com.github.jameshnsears.cameraoverlay.view.main.permission

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat.startActivityForResult
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.viewmodel.MainScreenViewModel
import timber.log.Timber

@Composable
fun DisplayOverlayPermissionButton(mainScreenViewModel: MainScreenViewModel) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Timber.d("isGranted=%b", isGranted)
        } else {
            // request permission
            if (!Settings.canDrawOverlays(context)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + context.packageName)
                )

                if (context is Activity) {
                    startActivityForResult(context, intent, 1, null)
                }
            }
        }
    }

    PermissionElevatedButton({
        if (Settings.canDrawOverlays(context)) {
            Timber.d("received permission")
        } else {
            // ask for permission
            launcher.launch(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
        }
    }, false, Icons.Outlined.Layers, stringResource(R.string.main_display_overlay))
    }
