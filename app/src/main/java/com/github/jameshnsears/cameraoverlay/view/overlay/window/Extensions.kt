package com.github.jameshnsears.cameraoverlay.view.overlay.window

import android.content.Context
import android.provider.Settings
import android.widget.Toast

private var toast: Toast? = null

fun Context.showToast(message: CharSequence?) {
    message?.let {
        toast?.cancel()
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT).apply { show() }
    }
}

val Context.canDrawOverlays: Boolean
    get() = Settings.canDrawOverlays(this)
