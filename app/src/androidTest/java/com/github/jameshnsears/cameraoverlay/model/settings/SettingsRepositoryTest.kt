package com.github.jameshnsears.cameraoverlay.model.settings

import androidx.test.platform.app.InstrumentationRegistry
import com.github.jameshnsears.cameraoverlay.model.permission.PermissionMediator
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class SettingsRepositoryTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun mainScreen() = runBlocking {
        val preferencesRepository = SettingsRepositoryImpl(context)

        assertEmpty(preferencesRepository)

        preferencesRepository.denyPermission(PermissionMediator.Permission.ACCESS_PHOTOS)
        assertEquals(1, preferencesRepository.denialsAccessPhotos.first())

        preferencesRepository.denyPermission(PermissionMediator.Permission.DISPLAY_OVERLAY)
        assertEquals(1, preferencesRepository.denialsDisplayOverlay.first())

        preferencesRepository.denyPermission(PermissionMediator.Permission.ACCESS_PHOTOS)
        assertEquals(2, preferencesRepository.denialsAccessPhotos.first())

        assertEmpty(preferencesRepository)
    }

    private suspend fun assertEmpty(preferencesRepository: SettingsRepositoryImpl) {
        preferencesRepository.empty()
        assertEquals(0, preferencesRepository.denialsAccessPhotos.first())
        assertEquals(0, preferencesRepository.denialsDisplayOverlay.first())
    }
}
