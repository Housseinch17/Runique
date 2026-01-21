package com.example.run.presentation.active_run

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.run.domain.RunningTracker
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ActiveRunViewModel(
    private val runningTracker: RunningTracker
) : ViewModel() {
    private val _state = MutableStateFlow(ActiveRunState())
    val state = _state.asStateFlow()

    private val _events = Channel<ActiveRunEvents>()
    val events = _events.receiveAsFlow()

    private val shouldTrack = snapshotFlow {
        _state.value.shouldTrack
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        state.value.shouldTrack
    )

    private val hasLocationPermission = MutableStateFlow(false)

    private val isTracking = combine(
        shouldTrack,
        hasLocationPermission,
    ) { shouldTrack, hasPermission ->
        shouldTrack && hasPermission
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        false
    )

    init {
        hasLocationPermission
            .onEach { hasPermission ->
                if (hasPermission) {
                    runningTracker.startObservingLocation()
                } else {
                    runningTracker.stopObservingLocation()
                }
            }.launchIn(viewModelScope)

        isTracking
            .onEach { isTracking ->
                runningTracker.setIsTracking(isTracking = isTracking)
            }.launchIn(viewModelScope)

        runningTracker
            .currentLocation
            .onEach {
                _state.update { newState ->
                    newState.copy(currentLocation = it?.location)
                }
            }.launchIn(viewModelScope)

        runningTracker
            .runData
            .onEach {
                _state.update { newState ->
                    newState.copy(
                        runData = it
                    )
                }
            }.launchIn(viewModelScope)

        runningTracker
            .elapsedTime
            .onEach {
                _state.update { newState ->
                    newState.copy(
                        elapsedTime = it
                    )
                }
            }.launchIn(viewModelScope)
    }

    fun onActions(actions: ActiveRunActions) {
        when (actions) {
            ActiveRunActions.OnBackClick -> onBackClick()

            ActiveRunActions.OnFinishRunClick -> {

            }

            ActiveRunActions.OnResumeRunClick -> {
                _state.update { newState ->
                    newState.copy(shouldTrack = true)
                }
            }

            ActiveRunActions.OnToggleRunClick -> {
                _state.update { newState ->
                    newState.copy(
                        hasStartedRunning = true,
                        shouldTrack = !_state.value.shouldTrack
                    )
                }
            }

            ActiveRunActions.DismissRationaleDialog -> dismissRationaleDialog()
            ActiveRunActions.ForcePermissionDialog -> forcePermissionDialog()
            ActiveRunActions.DismissPermissionDialog -> dismissPermissionDialog()
            is ActiveRunActions.SubmitLocationPermissionInfo -> {
                hasLocationPermission.value = actions.acceptedLocationPermission
                submitLocationPermissionInfo(
                    showLocationPermissionRationale = actions.showLocationPermissionRationale,
                )
            }

            is ActiveRunActions.SubmitNotificationPermissionInfo -> submitNotificationPermissionInfo(
                showNotificationPermissionRationale = actions.showNotificationPermissionRationale
            )

        }
    }

    private fun forcePermissionDialog() {
        _state.update { newState ->
            newState.copy(
                isPermissionDialogForced = true
            )
        }
    }

    private fun dismissPermissionDialog() {
        _state.update { newState ->
            newState.copy(
                isPermissionDialogForced = false
            )
        }
    }

    private fun onBackClick() {
        _state.update { newState ->
            newState.copy(
                shouldTrack = false
            )
        }
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
        showLocationPermissionRationale: Boolean
    ) {
        _state.update { newState ->
            newState.copy(
                showLocationRationale = showLocationPermissionRationale
            )
        }
    }

    private fun submitNotificationPermissionInfo(
        showNotificationPermissionRationale: Boolean
    ) {
        _state.update { newState ->
            newState.copy(
                showNotificationRationale = showNotificationPermissionRationale
            )
        }
    }
}