package com.example.auth.presentation.login

import com.example.core.presentation.ui.UiText

sealed interface LoginEvents {
    data object LoginSuccess : LoginEvents
    data class Error(val error: UiText) : LoginEvents
    data object Register : LoginEvents
}