package com.github.jameshnsears.cameraoverlay.utility

import com.github.jameshnsears.cameraoverlay.common.EmulatorCompatibilityHelper
import org.junit.Assert.fail
import org.junit.Before

open class TestEnvironmentUtility : TestUtility() {
    @Before
    fun confirmEnvironmentCompatible() {
        if (!EmulatorCompatibilityHelper.canRunInEmulatorQ()) {
            fail()
        }
    }
}
