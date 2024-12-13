package com.example.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.IntOffset
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
import com.example.utils.presentation.compose.SearchBlockContent
import com.example.utils.presentation.compose.SimpleTopBar
import com.example.utils.presentation.noRippleClickable
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
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SimpleTopBar(
                title = "Channels",
                containerColor = MainColor,
                titleContentColor = WhiteColor,
                elevation = 0.dp
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
        val height = 82.dp
        val appBarMaxHeightPx = with(LocalDensity.current) { height.roundToPx() }
        val connection = remember(appBarMaxHeightPx) {
            CollapsingAppBarNestedScrollConnection(appBarMaxHeightPx)
        }
        val density = LocalDensity.current
        val spaceHeight by remember(density) {
            derivedStateOf {
                with(density) {
                    (appBarMaxHeightPx + connection.appBarOffset).toDp()
                }
            }
        }

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .nestedScroll(connection)
        ) {
            SearchBlockContent(
                height = height,
                onValueChange = { event(HomeEvent.SearchChannel(querySearch = it)) },
                modifier = Modifier.offset { IntOffset(0, connection.appBarOffset) }
            )

            Column {
                Spacer(modifier = Modifier.height(spaceHeight))

                LoadingBox(
                    modifier = Modifier
                        .fillMaxSize()
                        .noRippleClickable {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        },
                    isLoading = uiState.loading
                ) {
                    PullRefreshLayout(
                        modifier = Modifier.fillMaxSize(),
                        isRefreshing = uiState.refreshing,
                        onRefresh = { event(HomeEvent.RefreshContent) },
                        refreshContent = { /*todo*/ }
                    ) {
                        LazyColumn(
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

private class CollapsingAppBarNestedScrollConnection(
    val appBarMaxHeight: Int
) : NestedScrollConnection {

    var appBarOffset: Int by mutableIntStateOf(0)
        private set

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        val delta = available.y.toInt()
        val newOffset = appBarOffset + delta
        val previousOffset = appBarOffset
        appBarOffset = newOffset.coerceIn(-appBarMaxHeight, 0)
        val consumed = appBarOffset - previousOffset
        return Offset(0f, consumed.toFloat())
    }
}
