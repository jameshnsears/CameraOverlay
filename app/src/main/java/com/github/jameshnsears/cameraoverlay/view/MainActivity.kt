package com.github.jameshnsears.cameraoverlay.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.jameshnsears.cameraoverlay.BuildConfig
import com.github.jameshnsears.cameraoverlay.model.utils.MethodLineLoggingTree
import com.github.jameshnsears.cameraoverlay.view.main.MainScreen
import com.github.jameshnsears.cameraoverlay.view.main.PermissionScreen
import com.github.jameshnsears.cameraoverlay.view.overlay.ConfigureOverlayScreen
import com.github.jameshnsears.cameraoverlay.view.photo.SelectPhotoScreen
import timber.log.Timber

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLogging()

        setContent {
            val navController = rememberNavController()

            NavHost(navController, startDestination = Navigation.MAIN_SCREEN) {
                composable(Navigation.MAIN_SCREEN) {
                    MainScreen(
                        navController,
                        helloViewModel = viewModel(),
                        mainScreenViewModel = viewModel()
                    )
                }
                composable(Navigation.PERMISSIONS_SCREEN) {
                    PermissionScreen(navController)
                }
                composable(Navigation.SELECT_PHOTO_SCREEN) {
                    SelectPhotoScreen(navController)
                }
                composable(Navigation.CONFIGURE_OVERLAY_SCREEN) {
                    ConfigureOverlayScreen(navController)
                }
            }
        }
    }

    private fun initLogging() {
        if (BuildConfig.DEBUG && Timber.treeCount == 0) {
            Timber.plant(MethodLineLoggingTree())
        }
    }
}
