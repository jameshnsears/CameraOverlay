package com.github.jameshnsears.cameraoverlay.view.main.permission

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
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
import com.github.jameshnsears.cameraoverlay.view.common.CommonTopAppBar
import com.github.jameshnsears.cameraoverlay.view.common.Navigation
import com.github.jameshnsears.cameraoverlay.view.theme.CameraOverlayTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PermissionScreen(navController: NavController) {
    CameraOverlayTheme {
        Scaffold(
            topBar = {
                CommonTopAppBar(
                    stringResource(R.string.permissions),
                    navController,
                    Navigation.SCREEN_MAIN
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                FilesAndMedia()
                Location()
                DisplayOverOtherApps()
            }
        }
    }
}

@Composable
fun FilesAndMedia() {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            Modifier
                .padding(16.dp)
        ) {
            Row {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Folder,
                            contentDescription = null
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(
                            stringResource(R.string.permissions_screen_files_and_media),
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    }
                    Text(
                        stringResource(R.string.permissions_screen_mandatory),
                        modifier = Modifier
                            .padding(vertical = 6.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        stringResource(R.string.permissions_screen_choose_photo),
                        modifier = Modifier
                            .padding(vertical = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Location() {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            Modifier
                .padding(16.dp)
        ) {
            Row {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = null
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(
                            stringResource(R.string.permissions_screen_location),
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    }
                    Text(
                        stringResource(R.string.permissions_screen_optional),
                        modifier = Modifier
                            .padding(vertical = 6.dp)
                    )
                    Text(
                        stringResource(R.string.permissions_screen_show_distance),
                        modifier = Modifier
                            .padding(vertical = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DisplayOverOtherApps() {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            Modifier
                .padding(16.dp)
        ) {
            Row {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Outlined.Layers,
                            contentDescription = null
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(
                            stringResource(R.string.permissions_screen_overlay),
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    }
                    Text(
                        stringResource(R.string.permissions_screen_mandatory),
                        modifier = Modifier
                            .padding(vertical = 6.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        stringResource(R.string.permissions_screen_overlay_display_photo),
                        modifier = Modifier
                            .padding(vertical = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewPermissionScreen() {
    PermissionScreen(rememberNavController())
}
