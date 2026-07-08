package com.codandotv.worldcupbestteamsimulator.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val name: String,
    val position: String,
    @SerialName("jersey_number")
    val jerseyNumber: Int,
    @SerialName("avatar_url")
    val avatarUrl: String
)
