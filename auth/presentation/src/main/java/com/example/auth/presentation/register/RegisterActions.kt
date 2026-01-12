package com.example.auth.presentation.register

import androidx.compose.ui.text.input.TextFieldValue

sealed interface RegisterActions {
    data class UpdateEmailValue(val email: TextFieldValue): RegisterActions
    data class UpdatePasswordValue(val password: TextFieldValue): RegisterActions
    data object OnTogglePasswordVisibilityClick: RegisterActions
    data object OnLoginClick: RegisterActions
    data object OnRegisterClick: RegisterActions
}