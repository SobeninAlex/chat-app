package com.example.navigation

import kotlinx.serialization.Serializable

@Serializable
data object AuthGraph {

    @Serializable
    data object SignInRoute

    @Serializable
    data class SignUpRoute(
        val email: String
    )

}

@Serializable
data object HomeGraph {

    @Serializable
    data object HomeRoute

    @Serializable
    data class ChatRoute(
        val channelId: String
    )

}