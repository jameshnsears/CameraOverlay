package com.github.jameshnsears.cameraoverlay.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.github.jameshnsears.cameraoverlay.model.overlay.OverlayWindowForegroundService

class OverlayScreenViewModel : ViewModel() {
    val isOverlayWindowServiceActive = OverlayWindowForegroundService.isActive

    fun startOverlayWindowService(context: Context) {
        OverlayWindowForegroundService.start(context)
    }

    fun stopOverlayWindowService(context: Context) {
        OverlayWindowForegroundService.stop(context)
    }
}
