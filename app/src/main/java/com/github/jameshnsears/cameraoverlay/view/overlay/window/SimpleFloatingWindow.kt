package com.github.jameshnsears.cameraoverlay.view.overlay.window

import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import com.github.jameshnsears.cameraoverlay.R
import timber.log.Timber
import kotlin.math.abs


class SimpleFloatingWindow constructor(private val context: Context) {

    private var windowManager: WindowManager? = null
        get() {
            if (field == null)
                field = (context.getSystemService(WINDOW_SERVICE) as WindowManager)
            return field
        }

    private var floatView: View =
        LayoutInflater.from(context).inflate(R.layout.overlay_window, null)

    private lateinit var layoutParams: WindowManager.LayoutParams

    private var lastX: Int = 0
    private var lastY: Int = 0
    private var firstX: Int = 0
    private var firstY: Int = 0

    private var isShowing = false
    private var touchConsumedByMove = false

    private val onTouchListener = View.OnTouchListener { view, event ->
        val totalDeltaX = lastX - firstX
        val totalDeltaY = lastY - firstY

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.rawX.toInt()
                lastY = event.rawY.toInt()
                firstX = lastX
                firstY = lastY
            }

            MotionEvent.ACTION_UP -> {
                view.performClick()
            }

            MotionEvent.ACTION_MOVE -> {
                val deltaX = event.rawX.toInt() - lastX
                val deltaY = event.rawY.toInt() - lastY
                lastX = event.rawX.toInt()
                lastY = event.rawY.toInt()
                if (abs(totalDeltaX) >= 5 || abs(totalDeltaY) >= 5) {
                    if (event.pointerCount == 1) {
                        layoutParams.x += deltaX
                        layoutParams.y += deltaY
                        touchConsumedByMove = true
                        windowManager?.apply {
                            updateViewLayout(floatView, layoutParams)
                        }
                    } else {
                        touchConsumedByMove = false
                    }
                } else {
                    touchConsumedByMove = false
                }
            }
            else -> {
                Timber.d("%d", event.actionMasked)
            }
        }
        touchConsumedByMove
    }

    init {
        floatView.findViewById<ImageButton>(R.id.closeImageButton).setOnClickListener { dismiss() }


        /*
            seekBar.setOnSeekBarChangeListener( object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, currentValue: Int, p2: Boolean) {
                    Log.i("seekBar" , "${currentValue}")
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {}
                override fun onStopTrackingTouch(p0: SeekBar?) {}
            })
         */


        floatView.setOnTouchListener(onTouchListener)

        layoutParams = WindowManager.LayoutParams().apply {
            format = PixelFormat.TRANSLUCENT
            flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            gravity = Gravity.CENTER
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }
    }

    fun show() {
        if (context.canDrawOverlays) {
            dismiss()
            isShowing = true
            windowManager?.addView(floatView, layoutParams)
        }
    }

    private fun dismiss() {
        if (isShowing) {
            Timber.d("dismiss")
            windowManager?.removeView(floatView)
            isShowing = false
        }
    }
}
