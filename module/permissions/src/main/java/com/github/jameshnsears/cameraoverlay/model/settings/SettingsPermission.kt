package com.github.jameshnsears.cameraoverlay.model.settings

import androidx.datastore.preferences.core.booleanPreferencesKey

interface SettingsPermission {
    object PreferencesKey {
        val PERMISSION_REQUEST_STORAGE = booleanPreferencesKey("permission.request.storage")
        val PERMISSION_REQUEST_LOCATION = booleanPreferencesKey("permission.request.location")
    }

    suspend fun empty()

    suspend fun rememberPermissionRequestStorage()
    suspend fun rememberPermissionRequestLocation()
}
