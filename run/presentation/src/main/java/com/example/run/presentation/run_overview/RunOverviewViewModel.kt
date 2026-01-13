package com.example.run.presentation.run_overview

import androidx.lifecycle.ViewModel

class RunOverviewViewModel(): ViewModel() {

    fun onActions(actions: RunOverviewActions){
        when(actions){
            RunOverviewActions.OnAnalyticsClick -> {}
            RunOverviewActions.OnLogoutClick -> {}
            RunOverviewActions.OnStartClick -> {}
        }
    }
}