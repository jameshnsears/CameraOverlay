package com.github.jameshnsears.cameraoverlay.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.jameshnsears.cameraoverlay.BuildConfig
import com.github.jameshnsears.cameraoverlay.view.help.HelpScreen
import com.github.jameshnsears.cameraoverlay.view.main.MainScreen
import com.github.jameshnsears.cameraoverlay.view.overlay.OverlayScreen
import com.github.jameshnsears.cameraoverlay.view.photo.SelectPhotoScreen
import com.google.android.material.color.DynamicColors
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        DynamicColors.applyToActivitiesIfAvailable(application)

        initLogging()

        setContent {
            val navController = rememberNavController()

            NavHost(navController, startDestination = Navigation.MAIN_SCREEN) {
                composable(Navigation.MAIN_SCREEN) {
                    MainScreen(navController, helloViewModel = viewModel())
                }
                composable(Navigation.HELP_SCREEN) {
                    HelpScreen(navController)
                }
                composable(Navigation.PHOTO_SCREEN) {
                    SelectPhotoScreen(navController)
                }
                composable(Navigation.OVERLAY_SCREEN) {
                    OverlayScreen(navController)
                }
            }
        }
    }

    private fun initLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
