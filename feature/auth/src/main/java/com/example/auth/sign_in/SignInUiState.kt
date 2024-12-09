package com.example.auth.sign_in

data class SignInUiState(
    val email: String = "",
    val password: String = ""
) {

    companion object {
        val FAKE = SignInUiState(
            email = "test@test.com",
            password = "123qwe"
        )
    }

}