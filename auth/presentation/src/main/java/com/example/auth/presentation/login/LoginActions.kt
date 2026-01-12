package com.example.auth.presentation.login

import androidx.compose.ui.text.input.TextFieldValue

sealed interface LoginActions {
    data object OnTogglePasswordVisibility : LoginActions
    data object OnLoginClick : LoginActions
    data class UpdateEmailValue(val email: TextFieldValue) : LoginActions
    data class UpdatePasswordValue(val password: TextFieldValue) : LoginActions
    data object OnRegisterClick: LoginActions
}