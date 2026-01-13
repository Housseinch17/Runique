package com.example.run.presentation.active_run

import com.example.core.presentation.ui.UiText

sealed interface ActiveRunEvents {
    data object OnBackClick : ActiveRunEvents
    data class Error(val error: UiText) : ActiveRunEvents
    data object RunSaved: ActiveRunEvents
}