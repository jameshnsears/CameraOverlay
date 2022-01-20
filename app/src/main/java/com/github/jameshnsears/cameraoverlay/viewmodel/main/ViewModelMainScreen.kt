package com.github.jameshnsears.cameraoverlay.viewmodel.main

import android.content.Context
import androidx.lifecycle.ViewModel
import com.github.jameshnsears.cameraoverlay.model.permission.PermissionArea
import com.github.jameshnsears.cameraoverlay.model.permission.PermissionMediatorImpl
import com.github.jameshnsears.cameraoverlay.model.permission.PermissionPrompt
import kotlinx.coroutines.runBlocking

class ViewModelMainScreen(context: Context) : ViewModel() {
    var permissionMediator = PermissionMediatorImpl(context)

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
}
