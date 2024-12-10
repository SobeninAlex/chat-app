package com.example.auth.sign_up

sealed interface SignUpEvent {

    data class ChangeFullName(val fullName: String) : SignUpEvent

    data class ChangeEmail(val email: String) : SignUpEvent

    data class ChangePassword(val password: String) : SignUpEvent

    data class ChangeConfirmPassword(val password: String) : SignUpEvent

}