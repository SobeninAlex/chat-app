package com.example.home.presentation

import com.example.utils.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : BaseViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadContent()
    }

    fun onEvent(event: HomeEvent) {

    }

    private fun refresh() {

    }

    private fun loadContent() {

    }

}