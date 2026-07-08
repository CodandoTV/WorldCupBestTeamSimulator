package com.codandotv.worldcupbestteamsimulator.ui.search

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codandotv.worldcupbestteamsimulator.data.WorldCupRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchPlayersViewModel(
    private val repository: WorldCupRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        SearchScreenUiState(
            results = null
        )
    )
    val uiState: StateFlow<SearchScreenUiState> = _uiState

    init {
        viewModelScope.launch {
            _uiState.debounce(250L).collect {
                onSearchBy(it.query.text)
            }
        }
    }

    private suspend fun onSearchBy(query: String) {
        val players = repository.searchPlayerByName(
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
    }
}