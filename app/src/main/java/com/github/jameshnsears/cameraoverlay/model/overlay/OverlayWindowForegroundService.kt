package com.github.jameshnsears.cameraoverlay.model.overlay

import android.app.Service
import android.content.Context
import android.content.Intent
import com.github.jameshnsears.cameraoverlay.view.overlay.window.OverlayWindow

/*
vScreen Rotation

In the foreground service, register the broadcast receiver to listen to
Intent.ACTION_CONFIGURATION_CHANGED, and you get notified when the screen is rotated.

In Floating Apps, when the screen orientation is changed, I keep the same size of the
window and recalculate its position using the percentual calculation:

newX = oldX / oldScreenWidth * newScreenWidth

The window seems to be still in the same position relatively.
*/
class OverlayWindowForegroundService : Service() {
    private lateinit var overlay: OverlayWindow

    override fun onCreate() {
        overlay = OverlayWindow(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_SHOW -> {
                    isActive = true
                    overlay.open()
                }
                ACTION_HIDE -> {
                    isActive = false
                    overlay.close()
                    stopSelf()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() = overlay.close()

    override fun onBind(intent: Intent?) = null

    companion object {
        var isActive = false
            private set

        private const val ACTION_SHOW = "SHOW"
        private const val ACTION_HIDE = "HIDE"

        fun start(context: Context) {
            val intent = Intent(context, OverlayWindowForegroundService::class.java).apply {
                action = ACTION_SHOW
            }
            context.startService(intent)
        }

        fun stop(context: Context) {
            val intent = Intent(context, OverlayWindowForegroundService::class.java).apply {
                action = ACTION_HIDE
            }
            context.startService(intent)
        }
    }
}
