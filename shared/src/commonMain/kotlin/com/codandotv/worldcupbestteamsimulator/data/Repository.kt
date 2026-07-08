package com.codandotv.worldcupbestteamsimulator.data

import com.codandotv.worldcupbestteamsimulator.data.model.Country
import kotlinx.serialization.json.Json
import worldcupbestteamsimulator.shared.generated.resources.Res

class WorldCupRepository {
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getCountries(): List<Country> {
        val bytes = Res.readBytes("files/world_cup_2026_teams.json")
        return json.decodeFromString(bytes.decodeToString())
    }
}
