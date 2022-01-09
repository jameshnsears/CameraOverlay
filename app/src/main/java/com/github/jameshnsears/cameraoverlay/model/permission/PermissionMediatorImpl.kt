package com.github.jameshnsears.cameraoverlay.model.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.github.jameshnsears.cameraoverlay.model.settings.SettingsRepositoryImpl
import kotlinx.coroutines.flow.first
import timber.log.Timber

class PermissionMediatorImpl(private val context: Context) : PermissionMediator {
    private val settingsRepository = SettingsRepositoryImpl(context)

    override suspend fun rememberPermissionRequest(permissionArea: PermissionArea) {
        return when (permissionArea) {
            PermissionArea.STORAGE -> settingsRepository.rememberPermissionRequestStorage()
            PermissionArea.LOCATION -> settingsRepository.rememberPermissionRequestLocation()
            PermissionArea.OVERLAY -> { }
        }
    }

    override suspend fun permissionPreviouslyRequested(permissionArea: PermissionArea): Boolean {
        return when (permissionArea) {
            PermissionArea.STORAGE -> settingsRepository.permissionRequestedForStorage.first()
            PermissionArea.LOCATION -> settingsRepository.permissionRequestedForLocation.first()
            PermissionArea.OVERLAY -> false
        }
    }

    override fun permissionAllowed(permissionArea: PermissionArea): Boolean {
        return when (permissionArea) {
            PermissionArea.STORAGE -> permissionAllowedStorage()
            PermissionArea.LOCATION -> permissionAllowedLocation()
            PermissionArea.OVERLAY -> permissionAllowedOverlay()
        }
    }

    private fun permissionAllowedStorage(): Boolean {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }

        return false
    }

    private fun permissionAllowedLocation(): Boolean {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }

        return false
    }

    private fun permissionAllowedOverlay(): Boolean {
        return Settings.canDrawOverlays(context)
    }
}
