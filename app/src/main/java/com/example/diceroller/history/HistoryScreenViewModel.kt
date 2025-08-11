package com.example.diceroller.history

import androidx.lifecycle.ViewModel
import com.example.diceroller.diceroller.Dice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HistoryScreenViewModel(val repo: HistoryEntryRepository = HistoryEntryRepositoryImpl) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getHistory()
    }

    private fun getHistory() {
        _uiState.update { HistoryUiState(repo.getAll()) }
    }

    // Clear all History
    fun onClearHistoryClick() {
        repo.clear()
        getHistory()
    }
}

data class HistoryUiState(
    val historyList: List<HistoryEntry> = emptyList(),
    val newToOldSortedHistoryList: List<HistoryEntry> = historyList.reversed()
)

data class HistoryEntry(
    val dice : Dice = Dice.D20,
    val finalRollResult: String = "376" + " :",
    val rollResultBreakdown: String = "7 + 8 (+ 1 Modifier)"
)
