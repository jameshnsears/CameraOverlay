package com.github.jameshnsears.cameraoverlay.common

import android.os.Build

object EmulatorCompatibilityHelper {
    fun canRunInEmulatorQ(): Boolean {
        // tests tied to Permission / MediaStore functionality of Android Q == 10 == API 29
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.Q
    }
}
