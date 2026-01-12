package com.example.auth.presentation.login

import androidx.compose.ui.text.input.TextFieldValue

data class LoginState(
    val email: TextFieldValue = TextFieldValue(),
    val password: TextFieldValue = TextFieldValue(),
    val isPasswordVisible: Boolean = false,
    val isLoggingIn: Boolean = false,
    val isEmailValid: Boolean = false,
) {
    val canLogIn: Boolean = isEmailValid && password.text.isNotBlank() && !isLoggingIn
}
