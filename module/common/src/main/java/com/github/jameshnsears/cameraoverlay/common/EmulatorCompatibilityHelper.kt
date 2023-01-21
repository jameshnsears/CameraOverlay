package com.github.jameshnsears.cameraoverlay.common

import android.os.Build

class EmulatorCompatibilityHelper {
    companion object {
        fun canTestButRunInEmulatorQ(): Boolean {
            // tests tied to Permission / MediaStore functionality of Android Q == 10 == API 29
            return Build.VERSION.SDK_INT == Build.VERSION_CODES.Q
        }
    }
}
