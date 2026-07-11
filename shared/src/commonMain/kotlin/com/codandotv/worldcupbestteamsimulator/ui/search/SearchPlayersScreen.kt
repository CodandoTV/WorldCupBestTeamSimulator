package com.codandotv.worldcupbestteamsimulator.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codandotv.worldcupbestteamsimulator.LocalNavController
import com.codandotv.worldcupbestteamsimulator.domain.model.PlayersByTeamItem
import com.codandotv.worldcupbestteamsimulator.ui.search.widgets.CountrySectionItem
import com.codandotv.worldcupbestteamsimulator.ui.search.widgets.PlayerSelectionItem
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchPlayersScreen(
    viewModel: SearchPlayersViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val navigator = LocalNavController.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TextField(
                            placeholder = {
                                Text("search by name")
                            },
                            value = uiState.query,
                            onValueChange = {
                                viewModel.onTextFieldValueChanged(it)
                            },
                            modifier = Modifier.weight(1f)
                        )

                        AssistChip(
                            onClick = {},
                            label = {
                                Text(
                                    "# ${uiState.selectedPlayersCount}",
                                )
                            }
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigator.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                },
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
                items(uiState.results!!, key = {
                    when (it) {
                        is PlayersByTeamItem.PlayerItem -> {
                            it.player.name
                        }

                        is PlayersByTeamItem.TeamItem -> {
                            it.countryName
                        }
                    }
                }) { item ->
                    when (item) {
                        is PlayersByTeamItem.PlayerItem -> {
                            PlayerSelectionItem(
                                playerItem = item,
                                onSelectedChange = { player, value ->
                                    viewModel.onPlayerSelection(
                                        player = player,
                                        value = value,
                                    )
                                },
                            )
                        }

                        is PlayersByTeamItem.TeamItem -> {
                            CountrySectionItem(
                                countryItem = item,
                            )
                        }
                    }
                }
            }
        }
    }
}