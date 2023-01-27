package com.github.jameshnsears.cameraoverlay.model.permission

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.github.jameshnsears.cameraoverlay.common.EmulatorCompatibilityHelper
import com.github.jameshnsears.cameraoverlay.permissions.BuildConfig
import junit.framework.TestCase
import org.junit.Assert.assertFalse
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PermissionMediatorTest {
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun defaultPermissions() {
        if (!EmulatorCompatibilityHelper.canTestButRunInEmulatorQ()) {
            TestCase.fail()
        }

//        if (BuildConfig.GITHUB_ACTION) {
//            return
//        }

        val permissionMediator = PermissionMediatorImpl(context)

        assertFalse(permissionMediator.permissionAllowed(PermissionArea.STORAGE))
        assertFalse(permissionMediator.permissionAllowed(PermissionArea.LOCATION))
        assertFalse(permissionMediator.permissionAllowed(PermissionArea.OVERLAY))
    }
}
