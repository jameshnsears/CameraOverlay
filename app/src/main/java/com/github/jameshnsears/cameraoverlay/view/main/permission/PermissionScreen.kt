package com.github.jameshnsears.cameraoverlay.view.main.permission

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.github.jameshnsears.cameraoverlay.view.common.CommonTopAppBar
import com.github.jameshnsears.cameraoverlay.view.theme.CameraOverlayTheme

@Composable
fun PermissionScreen(navController: NavController) {
    CameraOverlayTheme {
        Scaffold(
            topBar = {
                CommonTopAppBar(
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
                Common()
                AccessPhotos()
                ShowLocation()
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
        Row {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Folder,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp),
                    )
                    Text(
                        stringResource(R.string.permissions_access_photos),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Text(
                    stringResource(R.string.permissions_mandatory),
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                )
                Text(
                    stringResource(R.string.permissions_access_photos_2),
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun ShowLocation() {
    Column(
        modifier = Modifier
            .padding(vertical = 5.dp)
    ) {
        Row {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp),
                    )
                    Text(
                        stringResource(R.string.permissions_show_distance),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Text(
                    stringResource(R.string.permissions_optional),
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
    }
}

@Composable
fun DisplayOverlay() {
    Column(
        modifier = Modifier
            .padding(vertical = 5.dp)
    ) {
        Row {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Outlined.Layers,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp),
                    )
                    Text(
                        stringResource(R.string.permissions_display_overlay),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Text(
                    stringResource(R.string.permissions_mandatory),
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
    }
}

@Composable
fun Common() {
    Column {
        Text(
            stringResource(R.string.permissions_display_analytics),
            modifier = Modifier
                .padding(vertical = 4.dp)
        )
    }
}

@Composable
@Preview
fun PreviewPermissionScreen() {
    PermissionScreen(rememberNavController())
}
