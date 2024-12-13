package com.example.home.presentation

sealed interface HomeEvent {

    data object RefreshContent : HomeEvent

    data class OpenBottomSheet(val type: HomeUiState.BottomSheetType) : HomeEvent

    data object CloseBottomSheet : HomeEvent

    data class AddNewChannel(val nameNewChannel: String) : HomeEvent

    data class SearchChannel(val querySearch: String) : HomeEvent

}