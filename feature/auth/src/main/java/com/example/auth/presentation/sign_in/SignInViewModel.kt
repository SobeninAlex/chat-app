package com.example.auth.presentation.sign_in

import com.example.auth.FeatureAuthRepository
import com.example.utils.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: FeatureAuthRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState = _uiState.asStateFlow()

    //todo: test
    init {
        _uiState.update { it.copy(refresh = true) }
    }

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

        is SignInEvent.OnSignInClicked -> {
            signIn()
        }
    }

    private fun signIn() {
        _uiState.update { it.copy(loading = true) }
        repository.signIn(
            email = uiState.value.email,
            password = uiState.value.password
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result.user?.let {
                    launchNextScreen()
                    return@addOnCompleteListener
                }
                _uiState.update { it.copy(loading = false) }
                showSnackbar(message = task.exception?.message.toString())
            } else {
                _uiState.update { it.copy(loading = false) }
                showSnackbar(message = task.exception?.message.toString())
            }
        }
    }

}