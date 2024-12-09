package com.example.auth.sign_in

import com.example.utils.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(

) : BaseViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: SignInEvent) = when (event) {
        is SignInEvent.ChangeEmail -> {
            _uiState.update { old ->
                old.copy(email = event.email)
            }
        }

        is SignInEvent.ChangePassword -> {
            _uiState.update { old ->
                old.copy(password = event.password)
            }
        }
    }

}