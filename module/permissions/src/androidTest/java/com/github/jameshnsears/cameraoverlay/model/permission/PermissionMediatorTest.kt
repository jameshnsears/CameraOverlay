package com.github.jameshnsears.cameraoverlay.model.permission

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PermissionMediatorTest {
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun defaultPermissions() {
        val permissionMediator = PermissionMediatorImpl(context)

        assertFalse(permissionMediator.permissionAllowed(PermissionArea.STORAGE))
        assertFalse(permissionMediator.permissionAllowed(PermissionArea.LOCATION))

        // when using 'ANDROIDX_TEST_ORCHESTRATOR' this permission is granted be default!
        assertTrue(permissionMediator.permissionAllowed(PermissionArea.OVERLAY))
    }
}
