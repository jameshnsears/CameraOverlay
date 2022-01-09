package com.github.jameshnsears.cameraoverlay.viewmodel.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.jameshnsears.cameraoverlay.model.permission.PermissionArea
import com.github.jameshnsears.cameraoverlay.model.permission.PermissionMediatorImpl
import com.github.jameshnsears.cameraoverlay.model.permission.PermissionPrompt
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class ViewModelMainScreen(context: Context) : ViewModel() {
    private var permissionMediator = PermissionMediatorImpl(context)

    fun permissionButtonEnabled(permissionArea: PermissionArea): Boolean {
        return !permissionMediator.permissionAllowed(permissionArea)
    }

    fun permissionPrompt(permissionArea: PermissionArea): PermissionPrompt {
        var permissionPrompt = PermissionPrompt.PERMISSION_DIALOG

        runBlocking {
            if (permissionMediator.permissionPreviouslyRequested(permissionArea)) {
                permissionPrompt = PermissionPrompt.APP_INFO
            }
        }

        return permissionPrompt
    }

    fun rememberPermissionRequest(permissionArea: PermissionArea) {
        runBlocking {
            permissionMediator.rememberPermissionRequest(permissionArea)
        }
    }

    /////

    fun permissionAllowedOverlay(): Boolean {
        var permissionAllowedOverlay = false
        viewModelScope.launch {
            permissionAllowedOverlay = permissionMediator.permissionAllowed(PermissionArea.OVERLAY)
        }
        return permissionAllowedOverlay
    }
}
