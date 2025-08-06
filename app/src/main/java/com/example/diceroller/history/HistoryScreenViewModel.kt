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
    fun onClearHistoryClick() {
        repo.clear()
    }
}

data class HistoryUiState(
    val historyList: List<HistoryEntry> = emptyList()
)

data class HistoryEntry(
    val dice : Dice = Dice.D10,
    val finalRollResult: String = "16" + " :",
    val rollResultBreakdown: String = "7 + 8 (+ 1 Modifier)"
)
