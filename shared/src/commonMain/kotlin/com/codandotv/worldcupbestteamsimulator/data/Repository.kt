package com.codandotv.worldcupbestteamsimulator.data

import com.codandotv.worldcupbestteamsimulator.data.model.Player
import kotlinx.serialization.json.Json
import worldcupbestteamsimulator.shared.generated.resources.Res

class WorldCupRepositoryImpl : WorldCupRepository {
    private val json = Json { ignoreUnknownKeys = true }
    private var players: List<Player>? = null

    private suspend fun getPlayers(): List<Player> {
        if (players == null) {
            players = runCatching {
                val bytes = Res.readBytes("files/world_cup_2026_teams.json")
                return json.decodeFromString(bytes.decodeToString())
            }.getOrNull()
        }
        return players ?: emptyList()
    }

    override suspend fun searchPlayerByName(name: String): List<Player> {
        return if (name.isNotEmpty()) {
            val players = getPlayers()
            players.filter { it.name.contains(name, ignoreCase = true) }
        } else {
            emptyList()
        }
    }
}

interface WorldCupRepository {
    suspend fun searchPlayerByName(name: String): List<Player>
}
