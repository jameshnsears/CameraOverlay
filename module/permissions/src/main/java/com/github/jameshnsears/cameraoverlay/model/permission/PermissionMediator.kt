package com.github.jameshnsears.cameraoverlay.model.permission

interface PermissionMediator {
    suspend fun rememberPermissionRequest(permissionArea: PermissionArea)
    suspend fun permissionPreviouslyRequested(permissionArea: PermissionArea): Boolean
    fun permissionAllowed(permissionArea: PermissionArea): Boolean
}
