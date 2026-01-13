package com.example.run.presentation.active_run

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class ActiveRunViewModel() : ViewModel() {
    private val _state = MutableStateFlow(ActiveRunState())
    val state = _state.asStateFlow()

    private val _events = Channel<ActiveRunEvents>()
    val events = _events.receiveAsFlow()

    fun onActions(actions: ActiveRunActions) {
        when (actions) {

            else -> {}
        }
    }
}