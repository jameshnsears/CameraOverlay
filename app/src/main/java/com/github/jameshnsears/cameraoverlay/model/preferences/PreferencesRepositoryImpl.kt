package com.github.jameshnsears.cameraoverlay.model.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferencesRepositoryImpl(private val context: Context) : PreferencesRepository {
    val permissionAccessPhotos: Flow<Boolean> = context.dataStore.data
        .map {
            it[PreferencesRepository.PreferencesKeys.PERMISSION_ACCESS_PHOTOS] ?: false
        }

    val permissionShowDistance: Flow<Boolean> = context.dataStore.data
        .map {
            it[PreferencesRepository.PreferencesKeys.PERMISSION_SHOW_DISTANCE] ?: false
        }

    val permissionDisplayOverlay: Flow<Boolean> = context.dataStore.data
        .map {
            it[PreferencesRepository.PreferencesKeys.PERMISSION_DISPLAY_OVERLAY] ?: false
        }

    override suspend fun empty() {
        context.dataStore.edit {
            it.clear()
        }
    }

    override suspend fun setPermissionAccessPhotos() {
        context.dataStore.edit {
            it[PreferencesRepository.PreferencesKeys.PERMISSION_ACCESS_PHOTOS] = true
        }
    }

    override suspend fun setPermissionShowDistance() {
        context.dataStore.edit {
            it[PreferencesRepository.PreferencesKeys.PERMISSION_SHOW_DISTANCE] = true
        }
    }

    override suspend fun setPermissionDisplayOverlay() {
        context.dataStore.edit {
            it[PreferencesRepository.PreferencesKeys.PERMISSION_DISPLAY_OVERLAY] = true
        }
    }
}
