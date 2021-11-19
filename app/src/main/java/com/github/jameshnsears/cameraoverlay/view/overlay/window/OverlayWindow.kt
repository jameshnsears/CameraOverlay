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
        layoutInflater.inflate(R.layout.overlay_window_view, null) as OverlayLinearLayout

    /*
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
        params.gravity = Gravity.TOP or Gravity.LEFT
        params.width = (widthInDp * dm.density).toInt()
        params.height = (heightInDp * dm.density).toInt()
        params.x = (dm.widthPixels - params.width) / 2
        params.y = (dm.heightPixels - params.height) / 2
    }

    private fun initWindowParams() {
        // cat dimentsions!
        calculateSizeAndPosition(windowParams, 259, 259)
    }

    private fun initWindow() {
        // Using kotlin extension for views caused error, so good old findViewById is used
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
