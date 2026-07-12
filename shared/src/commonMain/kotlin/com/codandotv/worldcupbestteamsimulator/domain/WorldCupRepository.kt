package com.codandotv.worldcupbestteamsimulator.domain

import com.codandotv.worldcupbestteamsimulator.domain.model.Player

interface WorldCupRepository {
    suspend fun getPlayers(): List<Player>
    fun selectPlayer(player: Player)
    fun selectedPlayers(): List<Player>
    fun isPlayerSelected(playerName: String): Boolean
    fun unselectPlayer(player: Player)
}
