package com.github.jameshnsears.cameraoverlay.view.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material.icons.outlined.ImageSearch
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.model.permission.PermissionArea
import com.github.jameshnsears.cameraoverlay.view.common.Navigation
import com.github.jameshnsears.cameraoverlay.view.main.about.AboutDialog
import com.github.jameshnsears.cameraoverlay.view.permission.PermissionButtonLocation
import com.github.jameshnsears.cameraoverlay.view.permission.PermissionButtonOverlay
import com.github.jameshnsears.cameraoverlay.view.permission.PermissionButtonStorage
import com.github.jameshnsears.cameraoverlay.view.permission.observeAsSate
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
            }
        }
    }
}

@Composable
fun PermissionButtons(
    navController: NavController,
    viewModelPermission: ViewModelPermission
) {
    val lifeCycleState = LocalLifecycleOwner.current.lifecycle.observeAsSate()
    Timber.d("lifeCycleState=${lifeCycleState.value.name}")

    PermissionButtonStorage(viewModelPermission)
    PermissionButtonLocation(viewModelPermission)
    PermissionButtonOverlay(viewModelPermission)

    ButtonSelectPhoto(navController, viewModelPermission)
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
                    contentDescription = ""
                )
            }
        }
    )
}

@Composable
fun Usage() {
    Column(
        modifier = Modifier
            .padding(vertical = 5.dp)
    ) {
        Column() {
            Text(
                stringResource(R.string.main_screen_usage),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
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
            fontSize = 20.sp
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

    PermissionButtons(navController, viewModelPermission)
}

@Composable
fun ButtonSelectPhoto(
    navController: NavController,
    viewModelPermission: ViewModelPermission
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalAlignment = Alignment.End
    ) {
        Button(
            onClick = { navController.navigate(Navigation.SCREEN_SELECT_PHOTO) },
            shape = RoundedCornerShape(16.dp),
            enabled = !viewModelPermission.permissionButtonEnabled(PermissionArea.STORAGE) &&
                !viewModelPermission.permissionButtonEnabled(PermissionArea.OVERLAY)

        ) {
            Icon(
                imageVector = Icons.Outlined.ImageSearch,
                contentDescription = null,
                modifier = Modifier.padding(end = 16.dp),
            )
            Text(
                text = stringResource(R.string.select_photo)
            )
        }
    }
}

@Preview(name = "Light Theme")
@Composable
fun PreviewPortrait() {
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
fun PreviewLandscape() {
    val context = LocalContext.current

    MainScreen(
        rememberNavController(),
        ViewModelPermission(context)
    )
}
