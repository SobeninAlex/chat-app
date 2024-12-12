package com.example.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.home.presentation.components.AddChannelBottomSheet
import com.example.home.presentation.components.channelsBlock
import com.example.navigation.HomeGraph
import com.example.navigation.LocalNavController
import com.example.resourse.MainColor
import com.example.resourse.WhiteColor
import com.example.utils.presentation.compose.ActionIconButton
import com.example.utils.presentation.compose.LoadingBox
import com.example.utils.presentation.compose.PullRefreshLayout
import com.example.utils.presentation.compose.SimpleTopBar
import com.example.utils.presentation.setupSystemBarStyle

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    context.setupSystemBarStyle(
        statusBarColor = Color.Transparent,
        navigationBarColor = MaterialTheme.colorScheme.primaryContainer,
        isLightIcons = true
    )

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
    val navController = LocalNavController.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SimpleTopBar(
                title = "Channels",
                containerColor = MainColor,
                titleContentColor = WhiteColor
            )
        },
        floatingActionButton = {
            ActionIconButton(
                icon = Icons.Outlined.Add,
                onClick = {
                    event(HomeEvent.OpenBottomSheet(type = HomeUiState.BottomSheetType.ADD_CHANNEL))
                }
            )
        }
    ) { paddingValues ->
        LoadingBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            isLoading = uiState.loading
        ) {
            PullRefreshLayout(
                modifier = Modifier.fillMaxSize(),
                isRefreshing = uiState.refreshing,
                onRefresh = { event(HomeEvent.RefreshContent) },
                refreshContent = { /*todo*/ }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    channelsBlock(
                        channels = uiState.channels,
                        onChannelClick = { channel ->
                            navController.navigate(HomeGraph.ChatRoute(channel.id))
                        }
                    )
                }
            }
        }
    }

    if (uiState.bottomSheetState.isOpen) {
        AddChannelBottomSheet(
            onDismissRequest = { event(HomeEvent.CloseBottomSheet) },
            onAddChannel = { newChannelName ->
                event(HomeEvent.AddNewChannel(newChannelName))
            }
        )
    }
}
