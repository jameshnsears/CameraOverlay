package com.github.jameshnsears.cameraoverlay.view.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.github.jameshnsears.cameraoverlay.BuildConfig
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.model.permission.PermissionArea
import com.github.jameshnsears.cameraoverlay.view.common.Navigation
import com.github.jameshnsears.cameraoverlay.view.main.about.AboutDialog
import com.github.jameshnsears.cameraoverlay.view.main.permission.PermissionButtonLocation
import com.github.jameshnsears.cameraoverlay.view.main.permission.PermissionButtonOverlay
import com.github.jameshnsears.cameraoverlay.view.main.permission.PermissionButtonStorage
import com.github.jameshnsears.cameraoverlay.view.main.permission.observeAsSate
import com.github.jameshnsears.cameraoverlay.view.theme.CameraOverlayTheme
import com.github.jameshnsears.cameraoverlay.viewmodel.permission.ViewModelPermission
import timber.log.Timber

@Composable
fun MainScreen(
    navController: NavController,
    viewModelPermission: ViewModelPermission
) {
    CameraOverlayTheme {
        Scaffold(
            topBar = { AppBar() },
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Usage()

                Permissions(navController, viewModelPermission)

                ButtonPhoto(navController, viewModelPermission)
            }
        }
    }
}

@Composable
fun AppBar() {
    val infoDialogState = remember { mutableStateOf(false) }

    if (infoDialogState.value) {
        AboutDialog(infoDialogState)
    }

    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        actions = {
            IconButton(
                onClick = {
                    infoDialogState.value = true
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
    )
}

@Composable
fun Usage() {
    Column(
        modifier = Modifier
            .padding(vertical = 6.dp)
    ) {
        Column(Modifier.padding(bottom = 10.dp)) {
            Text(
                stringResource(R.string.main_screen_usage),
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        }
        Text(
            stringResource(R.string.main_screen_usage_0),
            modifier = Modifier
                .padding(vertical = 4.dp)
        )
        Text(
            stringResource(R.string.main_screen_usage_1),
            modifier = Modifier
                .padding(vertical = 4.dp)
        )
        Text(
            stringResource(R.string.main_screen_usage_2),
            modifier = Modifier
                .padding(vertical = 4.dp)
        )
        Text(
            stringResource(R.string.main_screen_usage_3),
            modifier = Modifier
                .padding(vertical = 4.dp)
        )
    }
}

@Composable
fun Permissions(
    navController: NavController,
    viewModelPermission: ViewModelPermission
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(R.string.permissions),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )

        IconButton(
            onClick = { navController.navigate(Navigation.SCREEN_PERMISSIONS) },
        ) {
            Icon(
                imageVector = Icons.Outlined.HelpOutline,
                contentDescription = null,
            )
        }
    }

    PermissionButtons(viewModelPermission)
}

@Composable
fun PermissionButtons(
    viewModelPermission: ViewModelPermission
) {
    val lifeCycleState = LocalLifecycleOwner.current.lifecycle.observeAsSate()
    Timber.d("lifeCycleState=${lifeCycleState.value.name}")

    Column {
        PermissionButtonStorage(viewModelPermission)
        PermissionButtonLocation(viewModelPermission)
        PermissionButtonOverlay(viewModelPermission)
    }
}

@Composable
fun ButtonPhoto(
    navController: NavController,
    viewModelPermission: ViewModelPermission
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp),
        horizontalAlignment = Alignment.End
    ) {
        Button(
            onClick = { navController.navigate(Navigation.SCREEN_SELECT_PHOTO) },
            modifier = Modifier
                .size(width = 180.dp, height = 45.dp),
            shape = RoundedCornerShape(16.dp),
            enabled = enableButtonSelectPhoto(viewModelPermission)
        ) {
            Text(
                text = stringResource(R.string.select_photo)
            )
        }
    }
}

fun enableButtonSelectPhoto(viewModelPermission: ViewModelPermission): Boolean {
    if (BuildConfig.DEBUG) {
        return true
    }

    return !(
        !viewModelPermission.permissionButtonEnabled(PermissionArea.STORAGE) &&
            !viewModelPermission.permissionButtonEnabled(PermissionArea.OVERLAY)
        )
}

@Preview(name = "Light Theme")
@Composable
fun PreviewMainScreenPortrait() {
    val context = LocalContext.current

    MainScreen(
        rememberNavController(),
        ViewModelPermission(context)
    )
}

@Preview(
    name = "Dark Theme, Landscape",
    widthDp = 720, heightDp = 720,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewMainScreenLandscape() {
    val context = LocalContext.current

    MainScreen(
        rememberNavController(),
        ViewModelPermission(context)
    )
}
