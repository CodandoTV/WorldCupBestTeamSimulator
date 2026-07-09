package com.codandotv.worldcupbestteamsimulator.ui.search

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codandotv.worldcupbestteamsimulator.domain.GetPlayersByTeamItemsUseCase
import com.codandotv.worldcupbestteamsimulator.domain.WorldCupRepository
import com.codandotv.worldcupbestteamsimulator.domain.model.PlayersByTeamItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchPlayersViewModel(
    private val getPlayersByTeamItemsUseCase: GetPlayersByTeamItemsUseCase,
    private val repository: WorldCupRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        SearchScreenUiState(
            results = null
        )
    )
    val uiState: StateFlow<SearchScreenUiState> = _uiState

    init {
        viewModelScope.launch {
            _uiState.debounce(250).collect {
                onSearchBy(it.query.text)
            }
        }
    }

    private suspend fun onSearchBy(query: String) {
        val players = getPlayersByTeamItemsUseCase.getPlayersByTeam(
            name = query
        )

        _uiState.update {
            it.copy(
                results = players
            )
        }
    }

    fun onTextFieldValueChanged(newQuery: TextFieldValue) {
        _uiState.update {
            it.copy(
                query = newQuery
            )
        }

        viewModelScope.launch {
            onSearchBy(query = newQuery.text)
        }
    }

    fun onPlayerSelection(player: PlayersByTeamItem.PlayerItem, value: Boolean) {
        if (value) {
            repository.selectPlayer(player.player)
        } else {
            repository.unselectPlayer(player.player)
        }

        syncPlayerSelectedState(player.player.name)
    }

    private fun syncPlayerSelectedState(playerName: String) {
        _uiState.update {
            val index = it.results?.indexOfFirst { itemByIndex ->
                itemByIndex is PlayersByTeamItem.PlayerItem && itemByIndex.player.name.equals(
                    playerName,
                    true
                )
            } ?: -1
            val oldElement = it.results?.getOrNull(index)
            val currentSelectionValue = repository.isPlayerSelected(playerName)
            val results = it.results?.toMutableList()?.apply {
                if (index != -1 && oldElement is PlayersByTeamItem.PlayerItem) {
                    set(
                        index, oldElement.copy(
                            isSelected = currentSelectionValue
                        )
                    )
                }
            }?.toList()

            it.copy(
                results = results
            )
        }
    }
}
