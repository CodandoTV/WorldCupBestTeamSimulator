package com.codandotv.worldcupbestteamsimulator.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.codandotv.worldcupbestteamsimulator.LocalNavController
import com.codandotv.worldcupbestteamsimulator.SEARCH_ROUTE
import com.codandotv.worldcupbestteamsimulator.ui.home.widgets.PlayerBenchItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.compose.viewmodel.koinViewModel
import worldcupbestteamsimulator.shared.generated.resources.Res

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    var svgText by remember { mutableStateOf<String?>(null) }
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default) {
            svgText = Res.readBytes("files/soccer-field.svg").decodeToString()
        }
    }

    LaunchedEffect(svgText) {
        if (svgText != null) {
            viewModel.refresh()
        }
    }

    val navigator = LocalNavController.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigator.navigate(SEARCH_ROUTE)
                },
                content = {
                    Icon(
                        imageVector = Icons.Filled.PlusOne,
                        contentDescription = "add players"
                    )
                }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.currentSelectedPlayer != null) {
                    IconButton(
                        modifier = Modifier.size(48.dp),
                        onClick = {
                            viewModel.clearSelection()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "clear selection"
                        )
                    }
                }
                LazyRow(
                    modifier = Modifier.weight(1f)
                        .selectableGroup(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.players) {
                        val isSelected = it == uiState.currentSelectedPlayer
                        PlayerBenchItem(
                            item = it,
                            modifier = Modifier.selectable(
                                selected = isSelected,
                                onClick = {
                                    viewModel.onSelect(it)
                                }
                            ),
                            isSelected = isSelected,
                        )
                    }
                }
            }

            if (svgText != null) {
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(Color.Magenta)
                )
            }
        }
    }
}
