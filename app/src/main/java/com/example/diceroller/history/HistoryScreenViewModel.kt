package com.example.diceroller.history

import androidx.lifecycle.ViewModel
import com.example.diceroller.Dice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HistoryScreenViewModel(val repo: HistoryEntryRepository = HistoryEntryRepositoryImpl) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getHistory()
    }

    private fun getHistory() {
        _uiState.value = HistoryUiState(repo.getAll())
    }

    // Clear all History
    fun clearHistory() {
        repo.clear()
    }
}

data class HistoryUiState(
    val historyList: List<HistoryEntry> = emptyList()
)

data class HistoryEntry(
    val dice : Dice = Dice.D20,
    val rollResult: String = "13 : 13 (+ 1 Modifier)"
)
