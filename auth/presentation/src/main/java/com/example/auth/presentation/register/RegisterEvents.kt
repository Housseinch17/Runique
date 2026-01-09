package com.example.auth.presentation.register

sealed interface RegisterEvents {
    data object LogIn: RegisterEvents
    data object RegisterSuccessfully: RegisterEvents
}