package com.github.jameshnsears.cameraoverlay.model.preferences

import androidx.datastore.preferences.core.booleanPreferencesKey

interface PreferencesRepository {
    object PreferencesKeys {
        val PERMISSION_ACCESS_PHOTOS = booleanPreferencesKey("permission.access.photos")
        val PERMISSION_SHOW_DISTANCE = booleanPreferencesKey("permission.show.distance")
        val PERMISSION_DISPLAY_OVERLAY = booleanPreferencesKey("permission.display.overlay")
    }

    suspend fun empty()
    suspend fun setPermissionAccessPhotos()
    suspend fun setPermissionShowDistance()
    suspend fun setPermissionDisplayOverlay()
}
