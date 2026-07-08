package com.codandotv.worldcupbestteamsimulator.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val country: String,
    val players: List<Player>
)
