package com.example.auth.presentation.register

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.domain.AuthRepository
import com.example.auth.domain.UserDataValidator
import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.core.presentation.ui.UiText
import com.example.core.presentation.ui.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.auth.presentation.R

class RegisterViewModel(
    private val userDataValidator: UserDataValidator,
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    private val _events = Channel<RegisterEvents>(Channel.UNLIMITED)
    val events = _events.receiveAsFlow()

    fun onAction(action: RegisterActions) {
        when (action) {
            RegisterActions.OnLoginClick -> login()
            RegisterActions.OnRegisterClick -> register()
            RegisterActions.OnTogglePasswordVisibilityClick -> toggleClick()
            is RegisterActions.UpdateEmailValue -> updateEmailValue(email = action.email)
            is RegisterActions.UpdatePasswordValue -> updatePasswordValue(password = action.password)
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
            _state.update { newState ->
                newState.copy(isRegistering = true)
            }
            val result = authRepository.register(
                email = _state.value.email.text,
                password = _state.value.password.text
            )
            _state.update { newState ->
                newState.copy(isRegistering = false)
            }
            when (result) {
                is Result.Success -> {
                    _state.update { newState->
                        newState.copy(
                            email = TextFieldValue(),
                            password = TextFieldValue()
                        )
                    }
                    _events.send(RegisterEvents.RegisterSuccessfully)
                }

                is Result.Error -> {
                    if (result.error == DataError.Network.CONFLICT) {
                        _events.send(RegisterEvents.Error(UiText.StringResource(R.string.error_email_exists)))
                    } else {
                        _events.send(RegisterEvents.Error(result.error.asUiText()))
                    }
                }
            }
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