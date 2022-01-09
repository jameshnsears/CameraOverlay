package com.github.jameshnsears.cameraoverlay.model.settings

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SettingsRepositoryTest {
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun markAppAsRun() = runBlocking {
        val settingsRepository = SettingsRepositoryImpl(context)
        settingsRepository.empty()

        assertFalse(settingsRepository.permissionRequestedForStorage.first())

        settingsRepository.rememberPermissionRequestStorage()

        assertTrue(settingsRepository.permissionRequestedForStorage.first())
    }
}
