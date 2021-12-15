package com.github.jameshnsears.cameraoverlay.stateholder

data class PermissionStateHolder(
    var permission: Boolean
)

typealias AccessPhotosStateHolder = PermissionStateHolder
typealias ShowDistanceStateHolder = PermissionStateHolder
typealias DisplayOverlayStateHolder = PermissionStateHolder
