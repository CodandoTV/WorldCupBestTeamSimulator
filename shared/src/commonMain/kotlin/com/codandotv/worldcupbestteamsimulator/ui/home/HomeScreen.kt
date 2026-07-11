package com.codandotv.worldcupbestteamsimulator.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.codandotv.worldcupbestteamsimulator.LocalNavController
import com.codandotv.worldcupbestteamsimulator.SEARCH_ROUTE
import com.github.codandotv.jujubasvg.core.JujubaSVG
import com.github.codandotv.jujubasvg.core.commander.Command
import com.github.codandotv.jujubasvg.core.rememberJujubaCommander
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.compose.viewmodel.koinViewModel
import worldcupbestteamsimulator.shared.generated.resources.Res

private const val SOCCER_FIELD_ID = "soccer_field"

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    var svgText by remember { mutableStateOf<String?>(null) }

    val commander = rememberJujubaCommander()

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

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
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
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                )
        ) {
            LazyRow(
                modifier = Modifier.fillMaxWidth()
                    .selectableGroup()
                    .height(64.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.players) {
                    val isSelected = uiState.currentSelectedPlayer == it
                    Box(
                        modifier = Modifier.size(80.dp)
                            .selectable(
                                selected = isSelected,
                                onClick = {
                                    viewModel.onSelect(it)
                                }
                            )
                            .background(Color.LightGray)
                    ) {
                        AsyncImage(
                            it.player.avatarUrl,
                            contentDescription = it.player.name,
                            modifier = Modifier.size(64.dp)
                                .clip(CircleShape)
                                .align(Alignment.Center),
                        )

                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = "check circle"
                            )
                        }
                    }
                }
            }

            if (svgText != null) {
                JujubaSVG(
                    svgText = svgText!!,
                    commander = commander,
                    onElementClick = {
                        if (it.id == uiState.currentSelectedPlayer?.svgImageId) {
                            coroutineScope.launch {
                                commander.execute(
                                    Command.RemoveNode(it.id)
                                )
                                viewModel.onImageRemovedFromSVG(it.id)
                            }
                        } else {
                            uiState.currentSelectedPlayer?.let { playerRowItem ->
                                if (viewModel.wasImageAlreadyAdded(playerRowItem.svgImageId)) {
                                    coroutineScope.launch {
                                        commander.execute(
                                            Command.RemoveNode(playerRowItem.svgImageId)
                                        )
                                        viewModel.onImageRemovedFromSVG(playerRowItem.svgImageId)
                                    }
                                }

                                coroutineScope.launch {
                                    commander.execute(
                                        Command.AddRoundedImage(
                                            imageId = playerRowItem.svgImageId,
                                            elementId = SOCCER_FIELD_ID,
                                            heightInPx = 60,
                                            widthInPx = 60,
                                            coordinate = it.rootCoordinate.copy(
                                                x = it.rootCoordinate.x - 30,
                                                y = it.rootCoordinate.y - 30
                                            ),
                                            imageUrl = playerRowItem.player.avatarUrl
                                        )
                                    )
                                    viewModel.onImageAddedToSVG(playerRowItem.svgImageId)
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}
