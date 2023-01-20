package com.github.jameshnsears.cameraoverlay.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.jameshnsears.cameraoverlay.BuildConfig
import com.github.jameshnsears.cameraoverlay.model.overlay.OverlayService
import com.github.jameshnsears.cameraoverlay.utils.MethodLineLoggingTree
import com.github.jameshnsears.cameraoverlay.view.common.CommonNavigation
import com.github.jameshnsears.cameraoverlay.view.main.MainScreen
import com.github.jameshnsears.cameraoverlay.view.main.permission.PermissionScreen
import com.github.jameshnsears.cameraoverlay.view.photo.PhotoSelectScreen
import com.github.jameshnsears.cameraoverlay.viewmodel.permission.ViewModelPermission
import timber.log.Timber

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLogging()

        setContent {
            val navController = rememberNavController()

            NavHost(navController, startDestination = CommonNavigation.SCREEN_MAIN) {
                composable(CommonNavigation.SCREEN_MAIN) {
                    MainScreen(
                        navController,
                        viewModelPermission = ViewModelPermission(applicationContext)
                    )
                }
                composable(CommonNavigation.SCREEN_PERMISSIONS) {
                    PermissionScreen(navController)
                }
                composable(CommonNavigation.SCREEN_SELECT_PHOTO) {
                    PhotoSelectScreen(navController)
                }
                // https://code.luasoftware.com/tutorials/android/jetpack-compose-navigation-pass-arguments/
//                composable(
//                    Navigation.SCREEN_CONFIGURE_OVERLAY + "/{photoId}",
//                    arguments = listOf(navArgument("photoId") { type = NavType.IntType })
//                ) {
//                    OverlayConfigureScreen(
//                        navController, it.arguments?.getInt("photoId")
//                    )
//                }
//
//                @Composable
//                fun OverlayConfigureScreen(navController: NavController, photoId: Int?) {
            }
        }
    }

    private fun initLogging() {
        if (BuildConfig.DEBUG && Timber.treeCount == 0) {
            Timber.plant(MethodLineLoggingTree())
        }
    }

    override fun onDestroy() {
        stopService(Intent(this, OverlayService::class.java))
        super.onDestroy()
    }
}
