package com.github.jameshnsears.cameraoverlay.model.permission

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PermissionAreaMediatorTest {
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun defaultPermissions() = runBlocking {
        val permissionMediator = PermissionMediatorImpl(context)

        assertFalse(permissionMediator.permissionPreviouslyRequested())

        assertFalse(permissionMediator.permissionAllowed(PermissionArea.STORAGE))
        assertFalse(permissionMediator.permissionAllowed(PermissionArea.LOCATION))

        // on emulator this permission is given by default
        assertTrue(permissionMediator.permissionAllowed(PermissionArea.OVERLAY))
    }
}