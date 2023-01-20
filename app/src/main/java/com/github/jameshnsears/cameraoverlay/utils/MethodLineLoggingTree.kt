package com.github.jameshnsears.cameraoverlay.utils

import timber.log.Timber

class MethodLineLoggingTree : Timber.DebugTree() {
    protected override fun createStackElementTag(element: StackTraceElement): String {
        return String.format(
            "CameraOverlay: %s.%s, %s",
            super.createStackElementTag(element),
            element.methodName,
            element.lineNumber
        )
    }
}
