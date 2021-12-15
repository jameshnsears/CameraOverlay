package com.github.jameshnsears.cameraoverlay.view.main.permission

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.view.Navigation
import com.github.jameshnsears.cameraoverlay.view.theme.CameraOverlayTheme

@ExperimentalMaterial3Api
@Composable
fun PermissionScreen(navController: NavController) {
    CameraOverlayTheme {
        Scaffold(
            topBar = {
                CommonSmallTopAppBar(
                    stringResource(R.string.permissions),
                    navController,
                    Navigation.MAIN_SCREEN
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                MandatoryWarning()
                AccessPhotos()
                ShowDistance()
                DisplayOverlay()
            }
        }
    }
}

@Composable
fun AccessPhotos() {
    Column(
        modifier = Modifier
            .padding(vertical = 5.dp)
    ) {
        Text(
            stringResource(R.string.permissions_access_photos),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            stringResource(R.string.permissions_access_photos_0),
            modifier = Modifier
                .padding(vertical = 4.dp)
        )
        Text(
            stringResource(R.string.permissions_access_photos_1),
            modifier = Modifier
                .padding(vertical = 4.dp)
        )
    }
}

@Composable
fun ShowDistance() {
    Column(
        modifier = Modifier
            .padding(vertical = 5.dp)
    ) {
        Text(
            stringResource(R.string.permissions_show_distance),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            stringResource(R.string.permissions_show_distance_0),
            modifier = Modifier
                .padding(vertical = 4.dp)
        )
        Text(
            stringResource(R.string.permissions_show_distance_1),
            modifier = Modifier
                .padding(vertical = 4.dp)
        )
    }
}

@Composable
fun DisplayOverlay() {
    Column(
        modifier = Modifier
            .padding(vertical = 5.dp)
    ) {
        Text(
            stringResource(R.string.permissions_display_overlay),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            stringResource(R.string.permissions_display_overlay_0),
            modifier = Modifier
                .padding(vertical = 4.dp)
        )
        Text(
            stringResource(R.string.permissions_display_overlay_1),
            modifier = Modifier
                .padding(vertical = 4.dp)
        )
    }
}

@Composable
fun MandatoryWarning() {
    Column {
        Text(
            stringResource(R.string.permissions_display_mandatory),
            modifier = Modifier
                .padding(vertical = 4.dp)
        )
    }
}


@ExperimentalMaterial3Api
@Composable
@Preview
fun PreviewPermissionScreen() {
    PermissionScreen(rememberNavController())
}

