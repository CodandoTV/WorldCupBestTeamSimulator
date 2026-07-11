package com.codandotv.worldcupbestteamsimulator.ui.home

import com.codandotv.worldcupbestteamsimulator.domain.model.Player

data class PlayerRowItem(
    val isSelected: Boolean,
    val player: Player,
    val svgImageId: String,
)

data class HomeScreenUiState(
    val currentSelectedPlayer: PlayerRowItem? = null,
    val players: List<PlayerRowItem>,
)