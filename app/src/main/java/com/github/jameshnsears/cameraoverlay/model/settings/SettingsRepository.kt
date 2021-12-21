package com.github.jameshnsears.cameraoverlay.model.settings

import androidx.datastore.preferences.core.intPreferencesKey
import com.github.jameshnsears.cameraoverlay.model.permission.PermissionMediator

interface SettingsRepository {
    object PreferencesKey {
        val PERMISSION_ACCESS_PHOTOS = intPreferencesKey("permission.access.photos")
        val PERMISSION_DISPLAY_OVERLAY = intPreferencesKey("permission.display.overlay")
    }

    suspend fun empty()
    suspend fun denyPermission(permission: PermissionMediator.Permission)
}
