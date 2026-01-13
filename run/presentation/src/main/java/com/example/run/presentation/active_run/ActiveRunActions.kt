package com.example.run.presentation.active_run

sealed interface ActiveRunActions {
    data object OnToggleRunClick : ActiveRunActions
    data object OnFinishRunClick : ActiveRunActions
    data object OnResumeRunClick : ActiveRunActions
    data object OnBackClick : ActiveRunActions
    data object OnDismissDialog: ActiveRunActions
}