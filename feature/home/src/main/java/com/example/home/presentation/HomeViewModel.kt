package com.example.home.presentation

import com.example.domain.Channel
import com.example.home.FeatureHomeRepository
import com.example.utils.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: FeatureHomeRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadContent()
    }

    fun onEvent(event: HomeEvent) = when (event) {
        is HomeEvent.RefreshContent -> { refreshContent() }
        is HomeEvent.OpenBottomSheet -> { openBottomSheet(type = event.type) }
        is HomeEvent.CloseBottomSheet -> { closeBottomSheet() }
        is HomeEvent.AddNewChannel -> { addChannel(name = event.nameNewChannel) }
    }

    private fun addChannel(name: String) {
        repository.addNewChannel(name)
            .addOnSuccessListener {
                loadContent()
            }
    }

    private fun refreshContent() {
        _uiState.update { it.copy(refreshing = true) }
        getChannels()
    }

    private fun loadContent() {
        _uiState.update { it.copy(loading = true) }
        getChannels()
    }

    private fun getChannels() {
        repository.getChannels()
            .addOnSuccessListener { data ->
                val list = data.children.map {
                    Channel(id = it.key!!, name = it.value.toString())
                }
                _uiState.update { state ->
                    state.copy(
                        loading = false,
                        refreshing = false,
                        channels = list
                    )
                }
            }
            .addOnFailureListener {
                _uiState.update { state ->
                    state.copy(loading = false, refreshing = false,)
                }
                showSnackbar(message = it.message.toString())
            }
    }

    private fun openBottomSheet(type: HomeUiState.BottomSheetType) {
        _uiState.update {
            it.copy(bottomSheetState = HomeUiState.BottomSheetState(isOpen = true, type = type))
        }
    }

    private fun closeBottomSheet() {
        _uiState.update {
            it.copy(bottomSheetState = HomeUiState.BottomSheetState(isOpen = false))
        }
    }

}