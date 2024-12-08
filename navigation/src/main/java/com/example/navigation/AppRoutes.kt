package com.example.navigation

import kotlinx.serialization.Serializable

@Serializable
data object AuthGraph {

    @Serializable
    data object SignInRoute

    @Serializable
    data object SignUpRoute

}

@Serializable
data object HomeGraph {

    @Serializable
    data object HomeRoute

    @Serializable
    data object ChatRoute

}