package com.example.auth.presentation.login

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.domain.AuthRepository
import com.example.auth.domain.UserDataValidator
import com.example.auth.presentation.R
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

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userDataValidator: UserDataValidator
) : ViewModel() {
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
        _state.update { newState ->
            newState.copy(isLoggingIn = true)
        }
        val email = _state.value.email.text
        val password = _state.value.password.text
        viewModelScope.launch {
            val result = authRepository.login(
                email = email,
                password = password
            )
            _state.update { newState ->
                newState.copy(isLoggingIn = false)
            }
            when (result) {
                is Result.Error -> {
                    if (result.error == DataError.Network.UNAUTHORIZED) {
                        _events.send(LoginEvents.Error(UiText.StringResource(R.string.email_password_incorrect)))
                    } else {
                        _events.send(LoginEvents.Error(result.error.asUiText()))
                    }
                }

                is Result.Success -> {
                    _state.update { newState->
                        newState.copy(
                            email = TextFieldValue(),
                            password = TextFieldValue()
                        )
                    }
                    _events.send(LoginEvents.LoginSuccess)
                }
            }
        }
    }

    private fun updateEmail(email: TextFieldValue) {
        _state.update { newState ->
            newState.copy(
                email = email,
                isEmailValid = userDataValidator.isValidEmail(email.text)
            )
        }
    }

    private fun updatePassword(password: TextFieldValue) {
        _state.update { newState ->
            newState.copy(
                password = password,
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