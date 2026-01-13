package com.example.run.presentation.run_overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RunOverviewViewModel(): ViewModel() {
    private val _events = Channel<RunOverviewEvents>()
    val events = _events.receiveAsFlow()
    fun onActions(actions: RunOverviewActions){
        when(actions){
            RunOverviewActions.OnAnalyticsClick -> {}
            RunOverviewActions.OnLogoutClick -> {}
            RunOverviewActions.OnStartClick -> onStartClick()
        }
    }

    private fun onStartClick(){
        viewModelScope.launch {
            _events.send(RunOverviewEvents.OnStartClick)
        }
    }
}