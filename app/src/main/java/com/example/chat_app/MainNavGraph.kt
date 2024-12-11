package com.example.chat_app

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.auth.presentation.sign_in.SignInScreen
import com.example.auth.presentation.sign_up.SignUpScreen
import com.example.chat.presentation.ChatScreen
import com.example.home.presentation.HomeScreen
import com.example.navigation.AuthGraph
import com.example.navigation.HomeGraph
import com.example.navigation.LocalNavController
import com.example.utils.event.ObserveAsEvent
import com.example.utils.event.SnackbarController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNavGraph(
    firebaseAuth: FirebaseAuth
) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    ObserveAsEvent(
        flow = SnackbarController.event,
        key1 = snackbarHostState
    ) { event ->
        coroutineScope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            val result = snackbarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.snackbarAction?.buttonName,
                duration = SnackbarDuration.Short,
                withDismissAction = true
            )

            if (result == SnackbarResult.ActionPerformed) {
                event.snackbarAction?.action?.invoke()
            }
        }
    }

    val currentUser = firebaseAuth.currentUser
    val startDestination = if (currentUser == null) AuthGraph else HomeGraph

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) {
        CompositionLocalProvider(
            LocalNavController provides navController
        ) {
            NavHost(
                navController = navController,
                startDestination = startDestination,
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
                        val args = it.toRoute<AuthGraph.SignUpRoute>()
                        SignUpScreen(email = args.email)
                    }
                }

                navigation<HomeGraph>(
                    startDestination = HomeGraph.HomeRoute
                ) {
                    composable<HomeGraph.HomeRoute> {
                        HomeScreen()
                    }

                    composable<HomeGraph.ChatRoute> {
                        val args = it.toRoute<HomeGraph.ChatRoute>()
                        ChatScreen(channelId = args.channelId)
                    }
                }
            }
        }
    }

}