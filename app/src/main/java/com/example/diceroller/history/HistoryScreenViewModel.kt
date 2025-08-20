package com.example.diceroller.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diceroller.diceroller.Dice
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HistoryScreenViewModel(val repo: HistoryEntryRepository = HistoryEntryRepositoryImpl) : ViewModel() {

    private var isReversed = true

    val uiState = repo.rollHistoryList.map { entries ->
        HistoryUiState(historyList = applySort(entries))
    }.stateIn(viewModelScope, SharingStarted.Eagerly, HistoryUiState())

    private fun applySort(list: List<HistoryEntry>) : List<HistoryEntry> {
        return if (isReversed) {
            list.reversed()
        } else {
            list
        }
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
    val dice : Dice = Dice.D20,
    val finalRollResult: String = "376" + " :",
    val rollResultBreakdown: String = "7 + 8 (+ 1 Modifier)"
)
