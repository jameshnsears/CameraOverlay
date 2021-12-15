package com.github.jameshnsears.cameraoverlay.view.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.ImageSearch
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.stateholder.HelloStateHolder
import com.github.jameshnsears.cameraoverlay.view.Navigation
import com.github.jameshnsears.cameraoverlay.view.main.permission.AccessPhotosPermissionButton
import com.github.jameshnsears.cameraoverlay.view.main.permission.DisplayOverlayPermissionButton
import com.github.jameshnsears.cameraoverlay.view.main.permission.AboutDialog
import com.github.jameshnsears.cameraoverlay.view.main.permission.ShowDistancePermissionButton
import com.github.jameshnsears.cameraoverlay.view.theme.CameraOverlayTheme
import com.github.jameshnsears.cameraoverlay.viewmodel.HelloViewModel
import com.github.jameshnsears.cameraoverlay.viewmodel.MainScreenViewModel

@ExperimentalMaterial3Api
@Composable
fun MainScreen(navController: NavController,
               helloViewModel: HelloViewModel,
) {

    val mainScreenViewModel = MainScreenViewModel(LocalContext.current)

    CameraOverlayTheme {
        Scaffold(
            topBar = { CentreAlignedTopAppBar() },
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Usage()

                PermissionsHeader(navController)
                AccessPhotosPermissionButton(mainScreenViewModel)
                ShowDistancePermissionButton(mainScreenViewModel)
                DisplayOverlayPermissionButton(mainScreenViewModel)

                SelectPhoto(navController)

                HoistedHello(helloViewModel)
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
//    Column {
//        if (helloStateHolder.name.isNotEmpty()) {
//            Text(
//                text = "Hello, " + helloStateHolder.name
//            )
//        }
//        OutlinedTextField(
//            value = helloStateHolder.name,
//            onValueChange = onNameChange,
//            label = { Text("Name") }
//        )
//    }
}

@Composable
fun CentreAlignedTopAppBar() {
    val infoDialogState = remember { mutableStateOf(false) }

    if (infoDialogState.value) {
        AboutDialog(infoDialogState)
    }

    CenterAlignedTopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        actions = {
            IconButton(onClick = {
                infoDialogState.value = true
            }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = ""
                )
            }
        })
}

@Composable
fun Usage() {
    Column(
        modifier = Modifier
            .padding(vertical = 5.dp)
    ) {
        Text(
            stringResource(R.string.main_usage),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            stringResource(R.string.main_usage_0),
            modifier = Modifier
                .padding(vertical = 4.dp)
        )
        Text(
            stringResource(R.string.main_usage_1),
            modifier = Modifier
                .padding(vertical = 4.dp)
        )
        Text(
            stringResource(R.string.main_usage_2),
            modifier = Modifier
                .padding(vertical = 4.dp)
        )
        Text(
            stringResource(R.string.main_usage_3),
            modifier = Modifier
                .padding(vertical = 4.dp)
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
            onClick = { navController.navigate(Navigation.PERMISSIONS_SCREEN) },
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalAlignment = Alignment.End
    ) {
        ElevatedButton(
            onClick = { navController.navigate(Navigation.SELECT_PHOTO_SCREEN) },
            shape = RoundedCornerShape(16.dp),
            enabled = true
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


@ExperimentalMaterial3Api
@Preview(name = "Light Theme")
@Composable
fun PreviewPortrait() {
    MainScreen(
        rememberNavController(),
        HelloViewModel()
    )
}

@ExperimentalMaterial3Api
@Preview(
    name = "Dark Theme, Landscape",
    widthDp = 720, heightDp = 720,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewLandscape() {
    MainScreen(
        rememberNavController(),
        HelloViewModel()
    )
}
