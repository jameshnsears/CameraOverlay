package com.github.jameshnsears.cameraoverlay.model.permission

import kotlinx.coroutines.flow.Flow

interface PermissionMediator {
    enum class Permission {
        ACCESS_PHOTOS,
        DISPLAY_OVERLAY
    }

    suspend fun deny(permission: Permission)
    fun countDeny(permission: Permission): Flow<Int?>
    fun isAllow(permission: Permission): Boolean
}
