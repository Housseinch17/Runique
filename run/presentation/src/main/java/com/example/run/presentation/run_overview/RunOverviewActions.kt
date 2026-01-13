package com.example.run.presentation.run_overview

sealed interface RunOverviewActions {
    data object OnStartClick: RunOverviewActions
    data object OnLogoutClick: RunOverviewActions
    data object OnAnalyticsClick: RunOverviewActions
}