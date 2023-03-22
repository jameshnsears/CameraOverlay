package com.github.jameshnsears.cameraoverlay.model.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings.cameraoverlay"
)

class SettingsCameraOverlayImpl : SettingsCameraOverlay {
//    val permissionRequestedForStorage: Flow<Boolean> = context.dataStore.data
//        .map {
//            it[PermissionSettings.PreferencesKey.PERMISSION_REQUEST_STORAGE] ?: false
//        }
//
//    val permissionRequestedForLocation: Flow<Boolean> = context.dataStore.data
//        .map {
//            it[PermissionSettings.PreferencesKey.PERMISSION_REQUEST_LOCATION] ?: false
//        }
//
//    override suspend fun empty() {
//        context.dataStore.edit {
//            it.clear()
//        }
//    }
//
//    override suspend fun rememberPermissionRequestStorage() {
//        context.dataStore.edit {
//            it[PermissionSettings.PreferencesKey.PERMISSION_REQUEST_STORAGE] = true
//        }
//    }
//
//    override suspend fun rememberPermissionRequestLocation() {
//        context.dataStore.edit {
//            it[PermissionSettings.PreferencesKey.PERMISSION_REQUEST_LOCATION] = true
//        }
//    }
}
