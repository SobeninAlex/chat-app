package com.example.auth.presentation.sign_in

data class SignInUiState(
    val loading: Boolean = false,
    val refresh: Boolean = false,
    val email: String = "",
    val password: String = ""
) {

    val enabledSignInButton: Boolean
        get() {
            if (email.isBlank()) return false
            if (password.isBlank()) return false
            return true
        }

    companion object {
        val FAKE = SignInUiState(
            email = "test@test.com",
            password = "123qwe"
        )
    }

}