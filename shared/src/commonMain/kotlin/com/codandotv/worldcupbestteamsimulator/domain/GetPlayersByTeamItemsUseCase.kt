package com.codandotv.worldcupbestteamsimulator.domain

import com.codandotv.worldcupbestteamsimulator.domain.model.PlayersByTeamItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private fun String.normalizeAccents(): String = this.lowercase()
    .replace(Regex("[àáâãä]"), "a")
    .replace(Regex("[èéêë]"), "e")
    .replace(Regex("[ìíîï]"), "i")
    .replace(Regex("[òóôõö]"), "o")
    .replace(Regex("[ùúûü]"), "u")
    .replace("ç", "c")
    .replace("ñ", "n")

class GetPlayersByTeamItemsUseCase(
    private val repository: WorldCupRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    suspend fun getPlayersByTeam(name: String): List<PlayersByTeamItem> =
        withContext(defaultDispatcher) {
            val normalizedQuery = name.normalizeAccents()
            val groupedByCountry = repository.getPlayers()
                .filter {
                    it.name.normalizeAccents().contains(normalizedQuery) ||
                        it.countryName.normalizeAccents().contains(normalizedQuery)
                }
                .groupBy { it.countryName }
            val items = mutableListOf<PlayersByTeamItem>()
            groupedByCountry.forEach {
                items.add(PlayersByTeamItem.TeamItem(countryName = it.key))
                items.addAll(
                    it.value.map { player ->
                        PlayersByTeamItem.PlayerItem(
                            player = player,
                            isSelected = repository.isPlayerSelected(playerName = player.name)
                        )
                    }
                )
            }
            items
        }
}
