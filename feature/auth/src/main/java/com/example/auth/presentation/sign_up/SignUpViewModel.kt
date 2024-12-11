package com.example.auth.presentation.sign_up

import com.example.auth.FeatureAuthRepository
import com.example.utils.presentation.BaseViewModel
import com.google.firebase.auth.UserProfileChangeRequest
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel(assistedFactory = SignUpViewModel.Factory::class)
class SignUpViewModel @AssistedInject constructor(
    private val repository: FeatureAuthRepository,
    @Assisted private val email: String,
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

        is SignUpEvent.OnSignUpClicked -> {
            signUp()
        }
    }

    private fun signUp() {
        val state = uiState.value
        _uiState.update { it.copy(loading = true) }
        repository.signUp(
            email = state.email,
            password = state.password
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result.user?.let {
                    it.updateProfile(
                        UserProfileChangeRequest.Builder()
                            .setDisplayName(state.fullName)
                            .build()
                    ).addOnCompleteListener {
                        launchNextScreen()
                    }
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