package com.github.jameshnsears.cameraoverlay.model.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.github.jameshnsears.cameraoverlay.model.settings.SettingsPermissionImpl
import kotlinx.coroutines.flow.first
import timber.log.Timber

open class PermissionMediatorImpl(private val context: Context) : PermissionMediator {
    private val settingsRepository = SettingsPermissionImpl(context)

    override suspend fun rememberPermissionRequest(permissionArea: PermissionArea) {
        if (permissionArea == PermissionArea.STORAGE) {
            settingsRepository.rememberPermissionRequestStorage()
        } else {
            settingsRepository.rememberPermissionRequestLocation()
        }
    }

    override suspend fun permissionPreviouslyRequested(permissionArea: PermissionArea): Boolean {
        return if (permissionArea == PermissionArea.STORAGE) {
            settingsRepository.permissionRequestedForStorage.first()
        } else {
            settingsRepository.permissionRequestedForLocation.first()
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
        // 29, "Allow" / "Deny"
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Timber.d("READ_EXTERNAL_STORAGE==PERMISSION_GRANTED")
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
            Timber.d("ACCESS_FINE_LOCATION==PERMISSION_GRANTED")
            return true
        }

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Timber.d("ACCESS_COARSE_LOCATION==PERMISSION_GRANTED")
            return true
        }

        return false
    }

    private fun permissionAllowedOverlay(): Boolean {
        val permission = Settings.canDrawOverlays(context)
        if (permission) Timber.d("SYSTEM_APPLICATION_OVERLAY==PERMISSION_GRANTED")
        return permission
    }
}
