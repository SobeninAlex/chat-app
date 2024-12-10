package com.example.auth.sign_up

data class SignUpUiState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = ""
) {

    val errorConfirm: Boolean
        get() = password == confirmPassword

    companion object {
        val FAKE = SignUpUiState(
            fullName = "Alex Rider",
            email = "test@test.com",
            password = "123qwe",
            confirmPassword = "123qwe"
        )
    }

}