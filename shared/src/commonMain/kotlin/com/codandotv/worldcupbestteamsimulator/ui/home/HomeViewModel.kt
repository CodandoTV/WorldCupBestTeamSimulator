package com.codandotv.worldcupbestteamsimulator.ui.home

import androidx.lifecycle.ViewModel
import com.codandotv.worldcupbestteamsimulator.domain.WorldCupRepository
import com.codandotv.worldcupbestteamsimulator.domain.model.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val repository: WorldCupRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        HomeScreenUiState(
            null,
            emptyList(),
        )
    )
    val uiState: StateFlow<HomeScreenUiState> = _uiState

    private val svgImageIds = mutableListOf<String>()

    fun refresh() {
        svgImageIds.clear()

        _uiState.update {
            it.copy(
                players = repository.selectedPlayers().map {
                    PlayerRowItem(
                        isSelected = false,
                        svgImageId = convertToId(it),
                        player = it,
                    )
                }
            )
        }
    }

    fun onSelect(playerRowItem: PlayerRowItem) {
        _uiState.update {
            it.copy(
                currentSelectedPlayer = playerRowItem
            )
        }
    }

    fun clearSelection() {
        _uiState.update {
            it.copy(
                currentSelectedPlayer = null,
            )
        }
    }

    fun onImageAddedToSVG(imageId: String) {
        svgImageIds.add(imageId)
    }

    fun onImageRemovedFromSVG(imageId: String) {
        svgImageIds.remove(imageId)
    }

    fun wasImageAlreadyAdded(imageId: String): Boolean {
        return svgImageIds.contains(imageId)
    }

    private fun convertToId(player: Player): String {
        val name = player.name.replace(' ', '_')
        val countryName = player.countryName.replace(' ', '_')
        val jerseyNumber = player.jerseyNumber.toString()
        return "$name-$countryName-$jerseyNumber"
    }
}