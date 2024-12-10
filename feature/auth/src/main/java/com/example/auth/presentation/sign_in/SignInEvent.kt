package com.example.auth.presentation.sign_in

sealed interface SignInEvent {

    data class ChangeEmail(val email: String) : SignInEvent

    data class ChangePassword(val password: String) : SignInEvent

    data object OnSignInClicked: SignInEvent

}