package com.github.jameshnsears.cameraoverlay.view.overlay

import android.app.Activity
import android.content.Context
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.view.common.CommonTopAppBar
import com.github.jameshnsears.cameraoverlay.view.common.Navigation
import com.github.jameshnsears.cameraoverlay.view.theme.CameraOverlayTheme
import com.github.jameshnsears.cameraoverlay.viewmodel.overlay.ViewModelOverlayConfigureScreen

@Composable
fun OverlayConfigureScreen(navController: NavController, photoId: Int?) {
    CameraOverlayTheme {
        Scaffold(
            topBar = {
                CommonTopAppBar(
                    stringResource(R.string.configure_overlay) + photoId,
                    navController,
                    Navigation.SCREEN_SELECT_PHOTO
                )
            },
        ) {
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp).fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Content()

                Size()

                CameraAppAndOverlay()
            }
        }
    }
}

@Composable
fun Content() {
    Column(Modifier.padding(bottom = 10.dp).padding(vertical = 6.dp)) {
        Text(
            stringResource(R.string.configure_overlay_screen_content),
            modifier = Modifier.padding(bottom = 5.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
        Row(modifier = Modifier
            .padding(vertical = 8.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_blur),
                contentDescription = null,
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                stringResource(R.string.configure_overlay_screen_blur)
            )
        }
        Row(modifier = Modifier
            .padding(vertical = 8.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_threshold),
                contentDescription = null,
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                stringResource(R.string.configure_overlay_screen_thresholds)
            )
        }
        Row(modifier = Modifier
            .padding(vertical = 8.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_transparency),
                contentDescription = null,
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                stringResource(R.string.configure_overlay_screen_transparency)
            )
        }
    }
}

@Composable
fun Size() {
    Column(Modifier.padding(vertical = 6.dp)) {
        Text(
            stringResource(R.string.configure_overlay_screen_window),
            modifier = Modifier.padding(bottom = 5.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
        Row(modifier = Modifier
            .padding(vertical = 8.dp)) {
            Icon(
                imageVector = Icons.Outlined.Height,
                contentDescription = null,
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                stringResource(R.string.configure_overlay_screen_height)
            )
        }
        Row(modifier = Modifier
            .padding(vertical = 8.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_width),
                contentDescription = null,
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                stringResource(R.string.configure_overlay_screen_width)
            )
        }
    }
}

@Composable
fun CameraAppAndOverlay() {
    val context = LocalContext.current
    val noPermissionMessage = stringResource(R.string.error_missing_mandatory_permission)

    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        horizontalAlignment = Alignment.End
    ) {
        Button(
            onClick = {
                LaunchOverlay(context, noPermissionMessage)
            },
            modifier = Modifier
                .size(width = 250.dp, height = 45.dp),
            shape = RoundedCornerShape(16.dp),
            enabled = true
        ) {
            Text(
                text = stringResource(
                    R.string.configure_overlay_screen_launch_camera_app_and_overlay
                )
            )
        }
    }
}

fun LaunchOverlay(context: Context, noPermissionMessage: String) {
    if (Settings.canDrawOverlays(context)) {
        val overlayScreenViewModel = ViewModelOverlayConfigureScreen()

        if (!overlayScreenViewModel.isOverlayWindowServiceActive) {
            overlayScreenViewModel.startOverlayWindowService(context)
            // quit Compose
//            (context as Activity).finish()
        } else {
            overlayScreenViewModel.stopOverlayWindowService(context)
        }
    }
    else {
        Toast.makeText(
            context as Activity,
            noPermissionMessage,
            Toast.LENGTH_SHORT
        ).show()
    }
}

@Preview(name = "Light Theme")
@Composable
fun PreviewOverlayConfigureScreen() {
    OverlayConfigureScreen(rememberNavController(), 0)
}
