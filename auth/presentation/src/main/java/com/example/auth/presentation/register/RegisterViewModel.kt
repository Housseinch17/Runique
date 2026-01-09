package com.example.auth.presentation.register

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.domain.UserDataValidator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userDataValidator: UserDataValidator
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    private val _events = Channel<RegisterEvents>(Channel.UNLIMITED)
    val events = _events.receiveAsFlow()

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnLoginClick -> login()
            RegisterAction.OnRegisterClick -> register()
            RegisterAction.OnTogglePasswordVisibilityClick -> toggleClick()
            is RegisterAction.UpdateEmailValue -> updateEmailValue(email = action.email)
            is RegisterAction.UpdatePasswordValue -> updatePasswordValue(password = action.password)
        }
    }

    private fun updateEmailValue(email: TextFieldValue) {
        _state.update { newState ->
            newState.copy(
                email = email,
                isEmailValid = userDataValidator.isValidEmail(email.text)
            )
        }
    }

    private fun updatePasswordValue(password: TextFieldValue) {
        _state.update { newState ->
            newState.copy(
                password = password,
                passwordValidationState = userDataValidator.validatePassword(password.text)
            )
        }
    }

    private fun login() {
        viewModelScope.launch {
            _events.send(RegisterEvents.LogIn)
        }
    }

    private fun register() {
        viewModelScope.launch {
            _events.send(RegisterEvents.Register)
        }
    }

    private fun toggleClick() {
        val isPasswordVisible = _state.value.isPasswordVisible
        _state.update { newState ->
            newState.copy(
                isPasswordVisible = !isPasswordVisible
            )
        }
    }
}