package com.github.jameshnsears.cameraoverlay.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.github.jameshnsears.cameraoverlay.utils.MethodLineLoggingTree
import com.github.jameshnsears.cameraoverlay.permissions.BuildConfig
import timber.log.Timber

class MainScreenTest : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.DEBUG && Timber.treeCount == 0) {
            Timber.plant(MethodLineLoggingTree())
        }
    }
}
