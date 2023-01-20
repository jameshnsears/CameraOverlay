package com.github.jameshnsears.cameraoverlay.util

import android.os.Build

class EmulatorCompatibilityHelper {
    companion object {
        fun canTestButRunInEmulator(): Boolean {
            // tests tied to Permission / MediaStore functionality of Android 13
            return Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU
        }
    }
}
