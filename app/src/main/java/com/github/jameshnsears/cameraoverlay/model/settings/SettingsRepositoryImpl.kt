package com.github.jameshnsears.cameraoverlay.model.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.github.jameshnsears.cameraoverlay.model.permission.PermissionMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")

class SettingsRepositoryImpl(private val context: Context) : SettingsRepository {
    val denialsAccessPhotos: Flow<Int> = context.dataStore.data
        .map {
            it[SettingsRepository.PreferencesKey.PERMISSION_ACCESS_PHOTOS] ?: 0
        }

    val denialsDisplayOverlay: Flow<Int> = context.dataStore.data
        .map {
            it[SettingsRepository.PreferencesKey.PERMISSION_DISPLAY_OVERLAY] ?: 0
        }

    override suspend fun empty() {
        context.dataStore.edit {
            it.clear()
        }
    }

    override suspend fun denyPermission(permission: PermissionMediator.Permission) {
        var denyCount = 0

        when (permission) {
            PermissionMediator.Permission.ACCESS_PHOTOS -> {
                context.dataStore.edit {
                    denyCount = it[SettingsRepository.PreferencesKey.PERMISSION_ACCESS_PHOTOS] ?: 0
                    denyCount += 1
                    it[SettingsRepository.PreferencesKey.PERMISSION_ACCESS_PHOTOS] = denyCount
                }
            }
            PermissionMediator.Permission.DISPLAY_OVERLAY -> {
                context.dataStore.edit {
                    denyCount =
                        it[SettingsRepository.PreferencesKey.PERMISSION_DISPLAY_OVERLAY] ?: 0
                    denyCount += 1
                    it[SettingsRepository.PreferencesKey.PERMISSION_DISPLAY_OVERLAY] = denyCount
                }
            }
        }

        Timber.d("%s=%d", permission.toString(), denyCount)
    }
}
