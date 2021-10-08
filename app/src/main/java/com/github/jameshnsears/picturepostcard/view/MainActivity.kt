package com.github.jameshnsears.picturepostcard.view

import android.os.Bundle
import android.os.StrictMode
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.jameshnsears.picturepostcard.BuildConfig
import com.github.jameshnsears.picturepostcard.view.main.MainScreen
import com.github.jameshnsears.picturepostcard.view.overlay.OverlayScreen
import com.github.jameshnsears.picturepostcard.view.photo.PhotoScreen
import com.google.android.material.color.DynamicColors
import timber.log.Timber


class MainActivity : AppCompatActivity() {
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        DynamicColors.applyToActivitiesIfAvailable(application);

        /*
2021-11-17 12:03:05.651 6642-6652/com.github.jameshnsears.picturepostcard D/StrictMode: StrictMode policy violation: android.os.strictmode.LeakedClosableViolation: A resource was acquired at attached stack trace but never released. See java.io.Closeable for information on avoiding resource leaks. Callsite: close
        at android.os.StrictMode$AndroidCloseGuardReporter.report(StrictMode.java:1992)
        at dalvik.system.CloseGuard.warnIfOpen(CloseGuard.java:347)
        at sun.nio.fs.UnixSecureDirectoryStream.finalize(UnixSecureDirectoryStream.java:580)
        at java.lang.Daemons$FinalizerDaemon.doFinalize(Daemons.java:291)
        at java.lang.Daemons$FinalizerDaemon.runInternal(Daemons.java:278)
        at java.lang.Daemons$Daemon.run(Daemons.java:139)
        at java.lang.Thread.run(Thread.java:920)
         */
//        initStrictMode()

        initLogging()

        setContent {
            val navController = rememberNavController()

            NavHost(navController, startDestination = Navigation.MAIN_SCREEN) {
                composable(Navigation.MAIN_SCREEN) {
                    MainScreen(navController)
                }
                composable(Navigation.PHOTO_SCREEN) {
                    PhotoScreen(navController)
                }
                composable(Navigation.OVERLAY_SCREEN) {
                    OverlayScreen(navController)
                }
            }
        }
    }

    private fun initStrictMode() {
        // https://developer.android.com/reference/android/os/StrictMode
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build()
            )
        }
    }

    private fun initLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
