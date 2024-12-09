package com.example.auth.sign_in

import com.example.auth.sign_up.SignUpEvent

sealed interface SignInEvent {

    data class ChangeEmail(val email: String) : SignInEvent

    data class ChangePassword(val password: String) : SignInEvent

}