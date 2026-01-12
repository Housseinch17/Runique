package com.example.runique

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.SessionStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val sessionStorage: SessionStorage
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state.onStart {
        getAuthStatus()
    }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            MainState()
        )

    fun getAuthStatus() {
        _state.update { newState ->
            newState.copy(
                isCheckingAuth = true
            )
        }
        viewModelScope.launch {
            val sessionStatus = sessionStorage.get()
            val isLoggedIn = sessionStatus != null
            _state.update { newState ->
                newState.copy(
                    isLoggedIn = isLoggedIn,
                    isCheckingAuth = false
                )
            }
        }
    }
}