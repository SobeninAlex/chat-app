package com.example.auth.presentation.sign_up

data class SignUpUiState(
    val loading: Boolean = false,
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = ""
) {

    val errorConfirm: Boolean
        get() = password != confirmPassword

    val enabledSignUpButton: Boolean
        get() {
            if (fullName.isBlank()) return false
            if (email.isBlank()) return false
            if (password.isBlank()) return false
            if (confirmPassword.isBlank()) return false
            if (errorConfirm) return false
            return true
        }

    companion object {
        val FAKE = SignUpUiState(
            fullName = "Alex Rider",
            email = "test@test.com",
            password = "123qwe",
            confirmPassword = "123qwe"
        )
    }

}