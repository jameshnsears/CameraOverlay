package com.github.jameshnsears.cameraoverlay.view.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.ImageSearch
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.github.jameshnsears.cameraoverlay.BuildConfig
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.stateholder.HelloStateHolder
import com.github.jameshnsears.cameraoverlay.view.Navigation
import com.github.jameshnsears.cameraoverlay.view.common.CommonPermissionsButton
import com.github.jameshnsears.cameraoverlay.view.theme.CameraOverlayTheme
import com.github.jameshnsears.cameraoverlay.viewmodel.HelloViewModel

@ExperimentalMaterial3Api
@Composable
fun MainScreen(navController: NavController, helloViewModel: HelloViewModel) {
    CameraOverlayTheme {
        Scaffold(
            topBar = { CentreAlignedTopAppBar() },
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            ) {
                Usage()

                PermissionsHeader(navController)

                CommonPermissionsButton(
                    Icons.Outlined.Folder,
                    stringResource(R.string.main_media)
                )
                CommonPermissionsButton(
                    Icons.Outlined.LocationOn,
                    stringResource(R.string.main_location)
                )
                CommonPermissionsButton(
                    Icons.Outlined.Layers,
                    stringResource(R.string.main_display)
                )

                SelectPhoto(navController)

                HoistedHello(helloViewModel)

                Footer()
            }
        }
    }
}

@Composable
fun HoistedHello(helloViewModel: HelloViewModel) {
    val helloStateHolder by helloViewModel.helloStateHolder
    Hello(
        helloStateHolder = helloStateHolder,
        onNameChange = {
            helloViewModel.onNameChange(HelloStateHolder(it))
        }
    )
}

@Composable
fun Hello(helloStateHolder: HelloStateHolder, onNameChange: (String) -> Unit) {
    Column {
        if (helloStateHolder.name.isNotEmpty()) {
            Text(
                text = "Hello, " + helloStateHolder.name
            )
        }
        OutlinedTextField(
            value = helloStateHolder.name,
            onValueChange = onNameChange,
            label = { Text("Name") }
        )
    }
}

@Composable
fun CentreAlignedTopAppBar() {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
    )
}

@Composable
fun Usage() {
    Column(
        modifier = Modifier
            .padding(vertical = 5.dp)
    ) {
        Text(
            stringResource(R.string.main_usage_0),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            stringResource(R.string.main_usage_1),
            modifier = Modifier.padding(start = 16.dp).padding(vertical = 4.dp)
        )
        Text(
            stringResource(R.string.main_usage_2),
            modifier = Modifier.padding(start = 16.dp).padding(vertical = 4.dp)
        )
        Text(
            stringResource(R.string.main_usage_3),
            modifier = Modifier.padding(start = 16.dp).padding(vertical = 4.dp)
        )
    }
}

@Composable
fun PermissionsHeader(navController: NavController) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(R.string.main_required),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        IconButton(
            onClick = { navController.navigate(Navigation.HELP_SCREEN) },
        ) {
            Icon(
                imageVector = Icons.Outlined.HelpOutline,
                contentDescription = null,
            )
        }
    }
}

@Composable
fun SelectPhoto(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        ElevatedButton(
            onClick = { navController.navigate(Navigation.PHOTO_SCREEN) },
            modifier = Modifier
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            enabled = false
        ) {
            Icon(
                imageVector = Icons.Outlined.ImageSearch,
                contentDescription = null,
                modifier = Modifier.padding(end = 16.dp),
            )
            Text(
                text = stringResource(R.string.main_select_photo)
            )
        }
    }
}

@Composable
fun Footer() {
    Row(
        Modifier
            .fillMaxSize()
            .padding(bottom = 4.dp)
    ) {
        Column(Modifier.align(Alignment.Bottom)) {
            Text(
                text = BuildConfig.VERSION_NAME + "/" + BuildConfig.GIT_HASH,
                fontSize = 14.sp
            )
        }
        Column(
            Modifier
                .fillMaxWidth()
                .align(Alignment.Bottom)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_github_logo),
                contentDescription = null,
                modifier = Modifier
                    .height(24.dp)
                    .align(Alignment.End)
            )
        }
    }
}

@ExperimentalMaterial3Api
@Preview(name = "Light Theme")
@Composable
fun PreviewPortrait() {
    MainScreen(rememberNavController(), HelloViewModel())
}

@ExperimentalMaterial3Api
@Preview(
    name = "Landscape",
    widthDp = 720, heightDp = 720,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)

@Composable
fun PreviewLandscape() {
    MainScreen(rememberNavController(), HelloViewModel())
}
