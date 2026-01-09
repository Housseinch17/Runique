package com.example.auth.presentation.register

import com.example.core.presentation.ui.UiText

sealed interface RegisterEvents {
    data object LogIn: RegisterEvents
    data object RegisterSuccessfully: RegisterEvents
    data class Error(val error: UiText): RegisterEvents
}