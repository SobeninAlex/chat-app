package com.example.auth.sign_up

data class SignUpUiState(
    val email: String = "",
    val password: String = ""
) {

    companion object {
        val FAKE = SignUpUiState(
            email = "test@test.com",
            password = "123qwe"
        )
    }

}