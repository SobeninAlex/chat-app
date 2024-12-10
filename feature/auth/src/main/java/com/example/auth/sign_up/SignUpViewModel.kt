package com.example.auth.sign_up

import com.example.utils.event.EmailController
import com.example.utils.presentation.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = SignUpViewModel.Factory::class)
class SignUpViewModel @AssistedInject constructor(
    @Assisted private val email: String
) : BaseViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(email: String): SignUpViewModel
    }

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { oldState ->
            oldState.copy(email = email)
        }
    }

    fun onEvent(event: SignUpEvent) = when (event) {
        is SignUpEvent.ChangeEmail -> {
            _uiState.update { old ->
                old.copy(email = event.email)
            }
            sendChangeEmailEvent(event.email)
        }

        is SignUpEvent.ChangePassword -> {
            _uiState.update { old ->
                old.copy(password = event.password)
            }
        }

        is SignUpEvent.ChangeConfirmPassword -> {
            _uiState.update { oldState ->
                oldState.copy(confirmPassword = event.password)
            }
        }

        is SignUpEvent.ChangeFullName -> {
            _uiState.update { oldState ->
                oldState.copy(fullName = event.fullName)
            }
        }
    }

    private fun sendChangeEmailEvent(email: String) {
        viewModelScope.launch {
            EmailController.sendEvent(email)
        }
    }

}