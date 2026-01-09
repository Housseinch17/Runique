package com.example.auth.presentation.register

import androidx.compose.ui.text.input.TextFieldValue

sealed interface RegisterAction {
    data class UpdateEmailValue(val email: TextFieldValue): RegisterAction
    data class UpdatePasswordValue(val password: TextFieldValue): RegisterAction
    data object OnTogglePasswordVisibilityClick: RegisterAction
    data object OnLoginClick: RegisterAction
    data object OnRegisterClick: RegisterAction
}