package com.github.jameshnsears.cameraoverlay.view

import android.os.Build

class EmulatorCompatibilityUtility {
    companion object {
        fun canTestButRunInEmulator(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R
        }
    }
}
