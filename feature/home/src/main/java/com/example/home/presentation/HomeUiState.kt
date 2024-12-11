package com.example.home.presentation

import com.example.domain.Channel

data class HomeUiState(
    val loading: Boolean = false,
    val refreshing: Boolean = false,
    val channels: List<Channel> = emptyList(),
    val bottomSheetState: BottomSheetState = BottomSheetState()
) {

    class BottomSheetState(
        val isOpen: Boolean = false,
        val type: BottomSheetType = BottomSheetType.ADD_CHANNEL
    )

    enum class BottomSheetType {
        ADD_CHANNEL
    }

}