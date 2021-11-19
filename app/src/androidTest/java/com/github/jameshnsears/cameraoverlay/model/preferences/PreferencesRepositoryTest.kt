package com.github.jameshnsears.cameraoverlay.model.preferences

import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PreferencesRepositoryTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun mainScreen() = runBlocking {
        val preferencesRepository = PreferencesRepositoryImpl(context)

        preferencesRepository.empty()
        assertFalse(preferencesRepository.permissionAccessPhotos.first())
        assertFalse(preferencesRepository.permissionShowDistance.first())
        assertFalse(preferencesRepository.permissionDisplayOverlay.first())

        preferencesRepository.setPermissionAccessPhotos()
        assertTrue(preferencesRepository.permissionAccessPhotos.first())

        preferencesRepository.setPermissionShowDistance()
        assertTrue(preferencesRepository.permissionShowDistance.first())

        preferencesRepository.setPermissionDisplayOverlay()
        assertTrue(preferencesRepository.permissionDisplayOverlay.first())
    }
}
