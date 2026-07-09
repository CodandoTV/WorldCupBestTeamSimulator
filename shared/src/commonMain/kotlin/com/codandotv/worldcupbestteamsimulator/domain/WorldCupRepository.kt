package com.codandotv.worldcupbestteamsimulator.domain

import com.codandotv.worldcupbestteamsimulator.domain.model.Player

interface WorldCupRepository {
    suspend fun searchPlayerByName(name: String): List<Player>
}
