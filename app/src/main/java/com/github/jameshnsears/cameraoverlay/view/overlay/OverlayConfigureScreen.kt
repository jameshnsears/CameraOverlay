package com.github.jameshnsears.cameraoverlay.view.overlay

import android.app.Activity
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
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Visibility
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
fun OverlayConfigureScreen(navController: NavController) {
    CameraOverlayTheme {
        Scaffold(
            topBar = {
                CommonTopAppBar(
                    stringResource(R.string.configure_overlay),
                    navController,
                    Navigation.SCREEN_SELECT_PHOTO
                )
            },
        ) {
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp).fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {

                Photo()

                EdgeDetection()

                Window()

                DisplayOverlay()
            }
        }
    }
}

@Composable
fun Window() {
    Column {
        Text(
            stringResource(R.string.configure_overlay_screen_window),
            modifier = Modifier.padding(bottom = 5.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
        Row {
            Icon(
                imageVector = Icons.Outlined.Palette,
                contentDescription = null,
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                stringResource(R.string.configure_overlay_screen_colour)
            )
        }
        Row {
            Icon(
                painter = painterResource(id = R.drawable.ic_width_black_24dp),
                contentDescription = null,
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                stringResource(R.string.configure_overlay_screen_width)
            )
        }
        Row {
            Icon(
                imageVector = Icons.Outlined.Height,
                contentDescription = null,
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                stringResource(R.string.configure_overlay_screen_height)
            )
        }
    }
}

@Composable
fun Photo() {
    Column {
        Text(
            stringResource(R.string.configure_overlay_screen_photo),
            modifier = Modifier.padding(bottom = 5.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
        Row {
            Icon(
                imageVector = Icons.Outlined.Visibility,
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
fun EdgeDetection() {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        Row {
            Text(
                stringResource(R.string.configure_overlay_screen_edge_detection),
                modifier = Modifier
                    .padding(start = 16.dp)
                    .padding(bottom = 5.dp)
                    .padding(top = 4.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 16.dp),
        ) {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_blur_24dp),
                    contentDescription = null,
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    stringResource(R.string.configure_overlay_screen_blur)
                )
            }
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_threshold_24dp),
                    contentDescription = null,
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    stringResource(R.string.configure_overlay_screen_thresholds)
                )
            }
        }
    }
}

@Composable
fun DisplayOverlay() {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        horizontalAlignment = Alignment.End
    ) {
        Button(
            onClick = {
                val overlayScreenViewModel = ViewModelOverlayConfigureScreen()
                if (!overlayScreenViewModel.isOverlayWindowServiceActive) {
                    overlayScreenViewModel.startOverlayWindowService(context)
                    // quit Compose
                    (context as Activity).finish()
                } else {
                    overlayScreenViewModel.stopOverlayWindowService(context)
                }
            },
            modifier = Modifier
                .size(width = 220.dp, height = 45.dp),
            shape = RoundedCornerShape(16.dp),
            enabled = true
        ) {
            Text(
                text = stringResource(R.string.configure_overlay_screen_launch_camera_app_and_overlay)
            )
        }
    }
}

@Preview(name = "Light Theme")
@Composable
fun Preview() {
    OverlayConfigureScreen(rememberNavController())
}
