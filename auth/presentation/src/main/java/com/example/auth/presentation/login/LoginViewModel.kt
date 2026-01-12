package com.example.auth.presentation.login

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _events = Channel<LoginEvents>()
    val events = _events.receiveAsFlow()

    fun onActions(actions: LoginActions) {
        when (actions) {
            LoginActions.OnLoginClick -> login()
            LoginActions.OnRegisterClick -> onRegisterClick()
            LoginActions.OnTogglePasswordVisibility -> onTogglePasswordVisibility()
            is LoginActions.UpdateEmailValue -> updateEmail(email = actions.email)
            is LoginActions.UpdatePasswordValue -> updatePassword(password = actions.password)
        }
    }

    private fun login() {
        val email = _state.value.email
        val password = _state.value.password

    }

    private fun updateEmail(email: TextFieldValue) {
        _state.update { newState ->
            newState.copy(
                email = email
            )
        }
    }

    private fun updatePassword(password: TextFieldValue) {
        _state.update { newState ->
            newState.copy(
                password = password
            )
        }
    }

    private fun onRegisterClick() {
        viewModelScope.launch {
            _events.send(LoginEvents.Register)
        }
    }

    private fun onTogglePasswordVisibility() {
        val passwordVisibility = _state.value.isPasswordVisible
        _state.update { newState ->
            newState.copy(
                isPasswordVisible = !passwordVisibility
            )
        }
    }
}