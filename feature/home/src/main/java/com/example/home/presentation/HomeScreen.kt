package com.example.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeContent(
        uiState = uiState,
        event = viewModel::onEvent
    )
}

@Composable
private fun HomeContent(
    uiState: HomeUiState,
    event: (HomeEvent) -> Unit
) {

}