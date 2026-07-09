package com.codandotv.worldcupbestteamsimulator.data

import com.codandotv.worldcupbestteamsimulator.domain.WorldCupRepository
import com.codandotv.worldcupbestteamsimulator.domain.model.Player
import io.ktor.http.encodeURLParameter
import kotlinx.serialization.json.Json
import worldcupbestteamsimulator.shared.generated.resources.Res

class WorldCupRepositoryImpl : WorldCupRepository {
    private val json = Json { ignoreUnknownKeys = true }
    private var players: List<Player>? = null

    private suspend fun getPlayers(): List<Player> {
        if (players == null) {
            players = runCatching {
                val bytes = Res.readBytes("files/world_cup_2026_teams.json")
                return json.decodeFromString<List<Player>>(bytes.decodeToString()).map {
                    it.copy(
                        avatarUrl = proxyAvatarUrl(it.avatarUrl)
                    )
                }
            }.getOrNull()
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

    override suspend fun searchPlayerByName(name: String): List<Player> {
        val players = getPlayers()
        return players.filter { it.name.contains(name, ignoreCase = true) }
    }
}
