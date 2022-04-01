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

        // width/height = 1080/2072

        // TODO find layout - as might start off in landscape
        overlayWindow.show(
            (this.resources.displayMetrics.widthPixels * .8).toInt(),
            (this.resources.displayMetrics.heightPixels * .8).toInt())

        return START_STICKY
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        overlayWindow.dismiss()

        super.onConfigurationChanged(newConfig)

        var height = this.resources.displayMetrics.heightPixels
        var width = this.resources.displayMetrics.widthPixels

        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            // width/height = 2072/1080
            Timber.d("landscape")
            width = (width * .8).toInt()
            height = (height  * .8).toInt()
        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            // width/height = 1080/2072
            Timber.d("portrait")
            width = (width  * .8).toInt()
            height = (height * .8).toInt()
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
