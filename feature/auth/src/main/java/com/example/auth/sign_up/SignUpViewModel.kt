package com.example.auth.sign_up

import com.example.utils.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel()
class SignUpViewModel @Inject constructor(

) : BaseViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: SignUpEvent) = when (event) {
        is SignUpEvent.ChangeEmail -> {
            _uiState.update { old ->
                old.copy(email = event.email)
            }
        }

        is SignUpEvent.ChangePassword -> {
            _uiState.update { old ->
                old.copy(password = event.password)
            }
        }
    }

}