package com.codandotv.worldcupbestteamsimulator.domain.model

sealed class PlayersByTeamItem {
    data class TeamItem(
        val countryName: String,
    ) : PlayersByTeamItem()

    data class PlayerItem(
        val player: Player,
        val isSelected: Boolean = false,
    ) : PlayersByTeamItem()
}
