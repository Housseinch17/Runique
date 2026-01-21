package com.example.run.presentation.active_run

sealed interface ActiveRunActions {
    data object OnToggleRunClick : ActiveRunActions
    data object OnFinishRunClick : ActiveRunActions
    data object OnResumeRunClick : ActiveRunActions
    data object OnBackClick : ActiveRunActions
    data class SubmitLocationPermissionInfo(
        val acceptedLocationPermission: Boolean,
        val showLocationPermissionRationale: Boolean
    ) : ActiveRunActions

    data class SubmitNotificationPermissionInfo(
        val acceptedNotificationPermission: Boolean,
        val showNotificationPermissionRationale: Boolean
    ): ActiveRunActions

    data object DismissRationaleDialog: ActiveRunActions
    data object ForcePermissionDialog: ActiveRunActions
    data object DismissPermissionDialog: ActiveRunActions
}