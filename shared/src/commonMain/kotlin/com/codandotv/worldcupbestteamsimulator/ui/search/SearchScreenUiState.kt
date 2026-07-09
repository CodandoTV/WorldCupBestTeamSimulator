package com.codandotv.worldcupbestteamsimulator.ui.search

import androidx.compose.ui.text.input.TextFieldValue
import com.codandotv.worldcupbestteamsimulator.domain.model.PlayersByTeamItem

data class SearchScreenUiState(
    val query: TextFieldValue = TextFieldValue(),
    val results: List<PlayersByTeamItem>?,
)