package com.github.jameshnsears.cameraoverlay.view.help

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
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
import com.github.jameshnsears.cameraoverlay.view.common.CommonSmallTopAppBar
import com.github.jameshnsears.cameraoverlay.view.theme.CameraOverlayTheme

@ExperimentalMaterial3Api
@Composable
fun HelpScreen(navController: NavController) {
    CameraOverlayTheme {
        Scaffold(
            topBar = {
                CommonSmallTopAppBar(
                    stringResource(R.string.help),
                    navController,
                    Navigation.MAIN_SCREEN
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            ) {
                Usage()
            }
        }
    }
}

@Composable
fun Usage() {
    Column(
        modifier = Modifier
            .padding(vertical = 5.dp)
    ) {
        Text(
            stringResource(R.string.help_privacy_policy),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Divider()

        Text(
            stringResource(R.string.help_permissions),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}

@ExperimentalMaterial3Api
@Preview(name = "Light Theme")
@Composable
fun PreviewPortrait() {
    HelpScreen(rememberNavController())
}

@ExperimentalMaterial3Api
@Preview(
    name = "Landscape",
    widthDp = 720, heightDp = 720,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)

@Composable
fun PreviewLandscape() {
    HelpScreen(rememberNavController())
}
