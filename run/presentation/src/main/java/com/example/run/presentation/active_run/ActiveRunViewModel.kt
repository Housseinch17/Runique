package com.example.run.presentation.active_run

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ActiveRunViewModel() : ViewModel() {
    private val _state = MutableStateFlow(ActiveRunState())
    val state = _state.asStateFlow()

    private val _events = Channel<ActiveRunEvents>()
    val events = _events.receiveAsFlow()

    fun onActions(actions: ActiveRunActions) {
        when (actions) {
            ActiveRunActions.OnBackClick -> onBackClick()
            ActiveRunActions.OnDismissDialog -> {}
            ActiveRunActions.OnFinishRunClick -> {}
            ActiveRunActions.OnResumeRunClick -> {}
            ActiveRunActions.OnToggleRunClick -> {}
            ActiveRunActions.DismissRationaleDialog -> dismissRationaleDialog()
            ActiveRunActions.ForcePermissionDialog -> forcePermissionDialog()
            ActiveRunActions.DismissPermissionDialog -> dismissPermissionDialog()
            is ActiveRunActions.SubmitLocationPermissionInfo -> submitLocationPermissionInfo(
                acceptedLocationPermission = actions.acceptedLocationPermission,
                showLocationPermissionRationale = actions.showLocationPermissionRationale,
            )

            is ActiveRunActions.SubmitNotificationPermissionInfo -> submitNotificationPermissionInfo(
                acceptedNotificationPermission = actions.acceptedNotificationPermission,
                showNotificationPermissionRationale = actions.showNotificationPermissionRationale
            )

        }
    }

    private fun forcePermissionDialog(){
        _state.update { newState->
            newState.copy(
                isPermissionDialogForced = true
            )
        }
    }

    private fun dismissPermissionDialog(){
        _state.update { newState->
            newState.copy(
                isPermissionDialogForced = false
            )
        }
    }
    private fun onBackClick() {
        viewModelScope.launch {
            _events.send(ActiveRunEvents.OnBackClick)
        }
    }

    private fun dismissRationaleDialog() {
        _state.update { newState ->
            newState.copy(
                showLocationRationale = false,
                showNotificationRationale = false
            )
        }
    }

    private fun submitLocationPermissionInfo(
        acceptedLocationPermission: Boolean,
        showLocationPermissionRationale: Boolean
    ) {
        _state.update { newState ->
            newState.copy(
                hasLocationPermission = acceptedLocationPermission,
                showLocationRationale = showLocationPermissionRationale
            )
        }
    }

    private fun submitNotificationPermissionInfo(
        acceptedNotificationPermission: Boolean,
        showNotificationPermissionRationale: Boolean
    ) {
        _state.update { newState ->
            newState.copy(
                hasNotificationPermission = acceptedNotificationPermission,
                showNotificationRationale = showNotificationPermissionRationale
            )
        }
    }
}