package com.codandotv.worldcupbestteamsimulator.ui.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codandotv.worldcupbestteamsimulator.ui.search.widget.PlayerSelectionItem
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchPlayersScreen(
    viewModel: SearchPlayersViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        value = uiState.query,
                        onValueChange = {
                            viewModel.onTextFieldValueChanged(it)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 12.dp)
                .fillMaxSize()
        ) {
            uiState.results?.let {
                items(uiState.results!!) { item ->
                    PlayerSelectionItem(
                        player = item,
                        onSelectedChange = {},
                        selected = false,
                    )
                }
            }
        }
    }
}