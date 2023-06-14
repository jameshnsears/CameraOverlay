package com.github.jameshnsears.cameraoverlay.utility

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.github.jameshnsears.cameraoverlay.common.EmulatorCompatibilityHelper
import com.github.jameshnsears.cameraoverlay.common.MethodLineLoggingTree
import org.junit.Assert
import org.junit.Before
import timber.log.Timber

open class CommonTestUtility {
    protected val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun initLogging() {
        if (Timber.treeCount == 0) {
            Timber.plant(MethodLineLoggingTree())
        }
    }

    fun confirmEnvironmentCompatible() {
        if (!EmulatorCompatibilityHelper.canEmulatorSupportTest()) {
            Assert.fail("API 29 required")
        }
    }
}
