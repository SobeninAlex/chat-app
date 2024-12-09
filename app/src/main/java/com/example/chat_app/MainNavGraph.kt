package com.example.chat_app

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.auth.sign_in.SignInScreen
import com.example.auth.sign_up.SignUpScreen
import com.example.navigation.AuthGraph
import com.example.navigation.HomeGraph
import com.example.navigation.LocalNavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNavGraph() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) {
        CompositionLocalProvider(
            LocalNavController provides navController
        ) {
            NavHost(
                navController = navController,
                startDestination = AuthGraph,
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        tween(1000)
                    )
                },
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        tween(1000)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(1000)
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(1000)
                    )
                }
            ) {
                navigation<AuthGraph>(
                    startDestination = AuthGraph.SignInRoute
                ) {
                    composable<AuthGraph.SignInRoute> {
                        SignInScreen()
                    }

                    composable<AuthGraph.SignUpRoute> {

                    }
                }

                navigation<HomeGraph>(
                    startDestination = HomeGraph.HomeRoute
                ) {
                    composable<HomeGraph.HomeRoute> {

                    }

                    composable<HomeGraph.ChatRoute> {

                    }
                }
            }
        }
    }

}