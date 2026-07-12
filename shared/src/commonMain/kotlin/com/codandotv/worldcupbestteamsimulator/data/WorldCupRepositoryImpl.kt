package com.codandotv.worldcupbestteamsimulator.data

import com.codandotv.worldcupbestteamsimulator.domain.WorldCupRepository
import com.codandotv.worldcupbestteamsimulator.domain.model.Player
import io.ktor.http.encodeURLParameter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import worldcupbestteamsimulator.shared.generated.resources.Res

class WorldCupRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : WorldCupRepository {
    private val json = Json { ignoreUnknownKeys = true }
    private var players: List<Player>? = null

    private val selectedPlayers: MutableList<Player> = mutableListOf()

    private suspend fun loadPlayers(): List<Player> {
        if (players == null) {
            players = withContext(ioDispatcher) {
                runCatching {
                    val bytes = Res.readBytes("files/world_cup_2026_teams.json")
                    json.decodeFromString<List<Player>>(bytes.decodeToString()).map {
                        it.copy(avatarUrl = proxyAvatarUrl(it.avatarUrl))
                    }
                }.getOrNull()
            }
        }
        return players ?: emptyList()
    }

    fun proxyAvatarUrl(url: String): String {
        val baseUrl = url
            .removePrefix("http://")
            .removePrefix("https://")
            .encodeURLParameter()
        return "https://wsrv.nl/?url=$baseUrl&w=96&h=96&fit=cover&output=webp&q=80"
    }

    override suspend fun getPlayers(): List<Player> = loadPlayers()

    override fun selectPlayer(player: Player) {
        if(selectedPlayers.contains(player).not()) {
            selectedPlayers.add(player)
        }
    }

    override fun selectedPlayers(): List<Player> {
        return selectedPlayers.toList()
    }

    override fun isPlayerSelected(playerName: String): Boolean {
        return selectedPlayers.any {
            it.name.equals(playerName, ignoreCase = true)
        }
    }

    override fun unselectPlayer(player: Player) {
        selectedPlayers.remove(player)
    }
}
