package com.github.jameshnsears.cameraoverlay.view.overlay.window

import android.content.Context
import android.graphics.PixelFormat
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.github.jameshnsears.cameraoverlay.R
import timber.log.Timber

class OverlayWindow(context: Context) {

    private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private val layoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val rootView =
        layoutInflater.inflate(R.layout.overlay_window_view, null) as OverlayWindowLinearLayout

    /*

Instruction:
. Tap the window’s title bar and move it anywhere you want!

    ----------

From Android API level 28, extra permission is necessary for foreground services. Add the line below to your AndroidManifest.xml:

<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />


    ----------
Resizing Windows 🔗

This one is tricky. In Floating Apps, some windows can be resized, and such windows have
 a small handle in the right bottom corner.

Resizing the window is very similar to moving it, and you can use the same 
DraggableTouchListener as we introduced in the Moving Window article. Just 
change x and y for width and height.

I experimented with changing the window size directly, but for windows with a complex 
layout, it’s slow.

So my final version is: When the resize handle is touched, a new semi-transparent 
floating view is injected above the original window with the same size and position 
and it’s resized instead of it. When resizing is finished, the new size is applied 
to the original window.

    --------

Screen Rotation 🔗

In the foreground service, register the broadcast receiver to listen to 
Intent.ACTION_CONFIGURATION_CHANGED, and you get notified when the screen is rotated.

In Floating Apps, when the screen orientation is changed, I keep the same size of 
the window and recalculate its position using the percentual calculation:

newX = oldX / oldScreenWidth * newScreenWidth

The window seems to be still in the same position relatively.

    --------
    Transparency

    WindowManager.LayoutParams comes with alpha, so this one is as simple as:

    // Make the window transparent with 50% opacity.
    params.alpha = 0.5f

    Don’t allow the user to make the window completely invisible as it could have
    undesired effects. I bet you can imagine it.
     */
    private val windowParams = WindowManager.LayoutParams(
        0,
        0,
        0,
        0,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
            WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
        PixelFormat.TRANSLUCENT
    )

    init {
        initWindowParams()
        initWindow()
    }

    private fun getCurrentDisplayMetrics(): DisplayMetrics {
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        return dm
    }

    private fun calculateSizeAndPosition(
        params: WindowManager.LayoutParams,
        widthInDp: Int,
        heightInDp: Int
    ) {
        val dm = getCurrentDisplayMetrics()
        // We have to set gravity for which the calculated position is relative.
        params.gravity = Gravity.TOP or Gravity.START
        params.width = (widthInDp * dm.density).toInt()
        params.height = (heightInDp * dm.density).toInt()
        params.x = (dm.widthPixels - params.width) / 2
        params.y = (dm.heightPixels - params.height) / 2
    }

    private fun initWindowParams() {
        calculateSizeAndPosition(windowParams, 259, 259)
    }

    private fun initWindow() {
        rootView.findViewById<View>(R.id.window_close).setOnClickListener { close() }

        rootView.findViewById<View>(R.id.cat_button).setOnClickListener {
            Timber.d("yyy")
        }

        rootView.findViewById<View>(R.id.window_header).registerDraggableTouchListener(
            initialPosition = { Point(windowParams.x, windowParams.y) },
            positionListener = { x, y -> setPosition(x, y) }
        )

        rootView.setListener {
            if (it) {
                enableKeyboard()
            } else {
                disableKeyboard()
            }
        }
    }

    private fun enableKeyboard() {
        if (windowParams.flags and WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE != 0) {
            windowParams.flags =
                windowParams.flags and WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE.inv()
            update()
        }
    }

    private fun disableKeyboard() {
        if (windowParams.flags and WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE == 0) {
            windowParams.flags = windowParams.flags or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            update()
        }
    }

    private fun setPosition(x: Int, y: Int) {
        windowParams.x = x
        windowParams.y = y
        update()
    }

    private fun update() {
        windowManager.updateViewLayout(rootView, windowParams)
    }

    fun open() {
        windowManager.addView(rootView, windowParams)
    }

    fun close() {
        windowManager.removeView(rootView)
    }
}
