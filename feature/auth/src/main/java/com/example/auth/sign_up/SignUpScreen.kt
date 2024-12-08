package com.example.auth.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.resourse.R
import com.example.utils.presentation.compose.LottieSimpleAnimation

@Composable
fun SignUpScreen() {
    val viewModel = hiltViewModel<SignUpViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SignUpContent(
        uiState = uiState,
        event = viewModel::onEvent
    )
}

@Composable
private fun SignUpContent(
    uiState: SignUpUiState,
    event: (SignUpEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieSimpleAnimation(
                modifier = Modifier.fillMaxWidth().height(120.dp),
                animation = R.raw.anim_1
            )

             OutlinedTextField(
                 value = uiState.email,
                 onValueChange = { event(SignUpEvent.ChangeEmail(it)) },
             )
        }
    }
}