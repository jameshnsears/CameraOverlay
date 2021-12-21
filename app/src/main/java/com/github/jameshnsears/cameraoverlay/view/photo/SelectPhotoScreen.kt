package com.github.jameshnsears.cameraoverlay.view.photo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FolderOpen
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.view.Navigation
import com.github.jameshnsears.cameraoverlay.view.common.CommonTopAppBar
import com.github.jameshnsears.cameraoverlay.view.photo.sortby.SortByDialog
import com.github.jameshnsears.cameraoverlay.view.theme.CameraOverlayTheme

@Composable
fun SelectPhotoScreen(navController: NavController) {
    // TODO see Cart.kt in Jetsnack to make my own reusable composable API

    CameraOverlayTheme {
        Scaffold(
            topBar = {
                CommonTopAppBar(
                    stringResource(R.string.select_photo),
                    navController,
                    Navigation.MAIN_SCREEN
                )
            },
        ) {
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp).fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Location()

                SortByDialog()

                CardPhoto()

                ConfigureOverlay(navController)
            }
        }
    }
}

@Composable
fun Location() {
    Column {
        Button(
            onClick = {},
            shape = RoundedCornerShape(16.dp),
        ) {
            Icon(
                imageVector = Icons.Outlined.FolderOpen,
                contentDescription = null,
                modifier = Modifier.padding(end = 16.dp),
            )
            Text(
                text = stringResource(R.string.select_photo_location)
            )
        }
    }
}

@Composable
fun ConfigureOverlay(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp).padding(bottom = 8.dp),
        horizontalAlignment = Alignment.End
    ) {
        Button(
            onClick = { navController.navigate(Navigation.CONFIGURE_OVERLAY_SCREEN) },
            shape = RoundedCornerShape(16.dp),
            enabled = true
        ) {
            Icon(
                imageVector = Icons.Outlined.Tune,
                contentDescription = null,
                modifier = Modifier.padding(end = 16.dp),
            )
            Text(
                text = stringResource(R.string.configure_overlay)
            )
        }
    }
}

@Preview(name = "Light Theme")
@Composable
fun Preview() {
    SelectPhotoScreen(rememberNavController())
}
