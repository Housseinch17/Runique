package com.example.auth.presentation.login

import androidx.compose.ui.text.input.TextFieldValue

data class LoginState(
    val email: TextFieldValue = TextFieldValue(),
    val password: TextFieldValue = TextFieldValue(),
    val isPasswordVisible: Boolean = false,
    val isLoggingIn: Boolean = false,
) {
    val canLogIn: Boolean = email.text.isNotBlank() || password.text.isNotBlank()
}
