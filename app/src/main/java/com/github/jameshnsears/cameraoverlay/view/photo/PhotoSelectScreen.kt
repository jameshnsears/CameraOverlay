package com.github.jameshnsears.cameraoverlay.view.photo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.github.jameshnsears.cameraoverlay.view.common.CommonTopAppBar
import com.github.jameshnsears.cameraoverlay.view.common.Navigation
import com.github.jameshnsears.cameraoverlay.view.photo.sortby.SortByDialog
import com.github.jameshnsears.cameraoverlay.view.theme.CameraOverlayTheme

@Composable
fun PhotoSelectScreen(navController: NavController) {
    CameraOverlayTheme {
        Scaffold(
            topBar = {
                CommonTopAppBar(
                    stringResource(R.string.select_photo),
                    navController,
                    Navigation.SCREEN_MAIN
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Row {
                    BrowseStorage()

                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.End,
                    ) {
                        SortByDialog()
                    }
                }

                CardPhoto()

                ConfigureOverlay(navController)
            }
        }
    }
}

@Composable
fun BrowseStorage() {
    /*
    MediaStore.Images
    MediaStore.Files

    Android provides two APIs for storing and accessing shareable data.
    MediaStore API is a recommended way to go when working with media files (pictures, audio, video).
    If, on the other hand, you need to work with documents and other files, you should use the platform’s
    Storage Access Framework.

    https://www.cobeisfresh.com/stories/taming-file-storage-on-android-part-2

    https://github.com/android/storage-samples

    https://guides.codepath.com/android/Accessing-the-Camera-and-Stored-Media


     */
    Column(Modifier.padding(top = 10.dp, bottom = 5.dp)) {
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
                text = stringResource(R.string.select_photo_screen_location)
            )
        }
    }
}

@Composable
fun ConfigureOverlay(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .padding(bottom = 10.dp),
        horizontalAlignment = Alignment.End
    ) {
        Button(
            onClick = { navController.navigate(Navigation.SCREEN_CONFIGURE_OVERLAY) },
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
fun PreviewPortrait() {
    PhotoSelectScreen(rememberNavController())
}
