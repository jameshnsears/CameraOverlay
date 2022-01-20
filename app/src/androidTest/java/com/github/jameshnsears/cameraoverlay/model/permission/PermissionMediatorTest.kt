package com.github.jameshnsears.cameraoverlay.model.permission

import android.content.Context
import android.provider.Settings
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PermissionMediatorTest {
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun defaultPermissions() = runBlocking(Dispatchers.Default) {
        val permissionMediator = PermissionMediatorImpl(context)

        assertFalse(permissionMediator.permissionAllowed(PermissionArea.STORAGE))
        assertFalse(permissionMediator.permissionAllowed(PermissionArea.LOCATION))

        if (Settings.canDrawOverlays(context)) {
            assertTrue(permissionMediator.permissionAllowed(PermissionArea.OVERLAY))
        } else {
            assertFalse(permissionMediator.permissionAllowed(PermissionArea.OVERLAY))
        }
    }
}
