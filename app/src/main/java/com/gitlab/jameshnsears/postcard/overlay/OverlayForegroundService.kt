package com.gitlab.jameshnsears.postcard.overlay

import android.app.Service
import android.content.Context
import android.content.Intent
import com.example.overlaysample.floating.OverlayWindow

/*

vScreen Rotation 🔗

In the foreground service, register the broadcast receiver to listen to Intent.ACTION_CONFIGURATION_CHANGED, and you get notified when the screen is rotated.

In Floating Apps, when the screen orientation is changed, I keep the same size of the window and recalculate its position using the percentual calculation:

newX = oldX / oldScreenWidth * newScreenWidth

The window seems to be still in the same position relatively.

 */

/**
 * A foreground service for managing the life cycle of overlay view.
 */
class OverlayForegroundService : Service() {
    companion object {
        private const val ACTION_SHOW = "SHOW"
        private const val ACTION_HIDE = "HIDE"

        fun start(context: Context) {
            val intent = Intent(context, OverlayForegroundService::class.java).apply {
                action = ACTION_SHOW
            }
            context.startService(intent)
        }

        fun stop(context: Context) {
            val intent = Intent(context, OverlayForegroundService::class.java).apply {
                action = ACTION_HIDE
            }
            context.startService(intent)
        }

        // To control toggle button in MainActivity. This is not elegant but works.
        var isActive = false
            private set
    }

    private lateinit var overlay: OverlayWindow

    override fun onCreate() {
        overlay = OverlayWindow(this)
    }

    /** Handles [ACTION_SHOW] and [ACTION_HIDE] intents. */
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
}
