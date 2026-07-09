package com.codandotv.worldcupbestteamsimulator.domain

import com.codandotv.worldcupbestteamsimulator.domain.model.PlayersByTeamItem


class GetPlayersByTeamItemsUseCase(
    private val repository: WorldCupRepository
) {
    suspend fun getPlayersByTeam(name: String): List<PlayersByTeamItem> {
        val groupedByCountry = repository.searchPlayerByName(name).groupBy { it.countryName }
        val items = mutableListOf<PlayersByTeamItem>()
        groupedByCountry.forEach {
            items.add(PlayersByTeamItem.TeamItem(countryName = it.key))

            items.addAll(
                it.value.map { player ->
                    PlayersByTeamItem.PlayerItem(
                        player = player,
                        isSelected = repository.isPlayerSelected(
                            playerName = player.name
                        )
                    )
                }
            )
        }
        return items
    }
}
