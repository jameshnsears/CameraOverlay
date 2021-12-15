package com.github.jameshnsears.cameraoverlay.model.main.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.github.jameshnsears.cameraoverlay.model.settings.SettingsRepositoryImpl
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class PermissionMediatorImpl(val context: Context) : PermissionMediator {
    private val settingsRepository = SettingsRepositoryImpl(context)

    override suspend fun deny(permission: PermissionMediator.Permission) {
        settingsRepository.denyPermission(permission)
    }

    override fun countDeny(permission: PermissionMediator.Permission): Flow<Int> {
        return when (permission) {
            PermissionMediator.Permission.ACCESS_PHOTOS -> settingsRepository.denialsAccessPhotos
            PermissionMediator.Permission.DISPLAY_OVERLAY -> settingsRepository.denialsDisplayOverlay
        }
    }

    override fun isAllow(permission: PermissionMediator.Permission): Boolean {
        return when (permission) {
            PermissionMediator.Permission.ACCESS_PHOTOS -> isAllowAccessPhotos()
            PermissionMediator.Permission.DISPLAY_OVERLAY -> isAllowDisplayOverlay()
        }
    }

    // TODO move OS permission checks into seperate class...
    private fun isAllowAccessPhotos(): Boolean {
        var allow = false

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            allow = true
        }

        Timber.d("%b", allow)
        return allow
    }

    private fun isAllowDisplayOverlay() :Boolean {
        return false
    }
}