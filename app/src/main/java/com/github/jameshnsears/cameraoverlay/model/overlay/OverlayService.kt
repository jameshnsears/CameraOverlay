package com.github.jameshnsears.cameraoverlay.model.overlay

import android.app.Service
import android.content.Intent
import android.content.res.Configuration
import android.os.IBinder
import com.github.jameshnsears.cameraoverlay.BuildConfig
import com.github.jameshnsears.cameraoverlay.common.MethodLineLoggingTree
import com.github.jameshnsears.cameraoverlay.view.overlay.window.OverlayWindow
import timber.log.Timber

class OverlayService : Service() {
    companion object {
        val OVERLAY_WINDOW_HEIGHT_ADJUSTMENT = .80
        val OVERLAY_WINDOW_WIDTH_ADJUSTMENT = .75
    }

    init {
        if (BuildConfig.DEBUG && Timber.treeCount == 0) {
            Timber.plant(MethodLineLoggingTree())
        }
    }

    lateinit var overlayWindow: OverlayWindow

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Timber.d("startService")

        overlayWindow = OverlayWindow(this)
        overlayWindow.show(getOverlayWindowWidth(), getOverlayWindowHeight())

        return START_STICKY
    }

    private fun getOverlayWindowWidth(): Int {
        if (this.resources.displayMetrics.widthPixels
            < this.resources.displayMetrics.heightPixels
        ) {
            // Landscape
            return (
                    this.resources.displayMetrics.widthPixels
                            * OVERLAY_WINDOW_WIDTH_ADJUSTMENT
                    ).toInt()
        } else {
            // Portrait
            return (
                    this.resources.displayMetrics.widthPixels
                            * OVERLAY_WINDOW_HEIGHT_ADJUSTMENT
                    ).toInt()
        }
    }

    private fun getOverlayWindowHeight(): Int {
        if (this.resources.displayMetrics.widthPixels
            < this.resources.displayMetrics.heightPixels
        ) {
            // Landscape
            return (
                    this.resources.displayMetrics.heightPixels
                            * OVERLAY_WINDOW_HEIGHT_ADJUSTMENT
                    ).toInt()
        } else {
            // Portrait
            return (
                    this.resources.displayMetrics.heightPixels
                            * OVERLAY_WINDOW_WIDTH_ADJUSTMENT
                    ).toInt()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        overlayWindow.dismiss()
        overlayWindow.show(getOverlayWindowWidth(), getOverlayWindowHeight())
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
