package com.github.jameshnsears.cameraoverlay.util

import android.os.Build

class EmulatorCompatibilityHelper {
    companion object {
        fun canTestButRunInEmulator(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R
        }
    }
}
