package com.example.auth.sign_up

sealed interface SignUpEvent {

    data class ChangeEmail(val email: String) : SignUpEvent

    data class ChangePassword(val password: String) : SignUpEvent

}