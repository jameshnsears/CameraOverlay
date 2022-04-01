package com.github.jameshnsears.cameraoverlay.model.overlay

import android.app.Service
import android.content.Intent
import android.content.res.Configuration
import android.os.IBinder
import android.widget.Toast
import com.github.jameshnsears.cameraoverlay.BuildConfig
import com.github.jameshnsears.cameraoverlay.model.utils.MethodLineLoggingTree
import com.github.jameshnsears.cameraoverlay.view.overlay.window.OverlayWindow
import timber.log.Timber

class OverlayService : Service() {
    init {
        if (BuildConfig.DEBUG && Timber.treeCount == 0) {
            Timber.plant(MethodLineLoggingTree())
        }
    }

    lateinit var overlayWindow: OverlayWindow

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Timber.d("startService")

        overlayWindow = OverlayWindow(this)
        overlayWindow.show(
            this.resources.displayMetrics.widthPixels,
            this.resources.displayMetrics.heightPixels - 500)

        return START_STICKY
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        overlayWindow.dismiss()

        super.onConfigurationChanged(newConfig)

        var height = this.resources.displayMetrics.heightPixels
        var width = this.resources.displayMetrics.widthPixels

        // TODO convert adjustments to % values

        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            // width/height = 2072/1080
            Timber.d("landscape")
            width = width - 500
            height = height - 100
        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            // width/height = 1080/2072
            Timber.d("portrait")
            width = width - 100

            height = height - 500
        }


        overlayWindow.show(width, height)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        Timber.d("stopService")
        super.onDestroy()

        overlayWindow.dismiss()
    }
}
