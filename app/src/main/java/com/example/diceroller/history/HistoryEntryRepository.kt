package com.example.diceroller.history

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface HistoryEntryRepository {

    val rollHistoryList: StateFlow<List<HistoryEntry>>

    fun save(entry: HistoryEntry)

    fun getAll() : List<HistoryEntry>

    fun clear()
}

// Singleton Implementation
object HistoryEntryRepositoryImpl : HistoryEntryRepository {
    private var historyList = listOf<HistoryEntry>()
    private val _rollHistoryList = MutableStateFlow(historyList)
    override val rollHistoryList = _rollHistoryList.asStateFlow()

    override fun save(entry: HistoryEntry) {
        historyList += entry
        _rollHistoryList.update { historyList }
    }

    override fun getAll() : List<HistoryEntry> {
        return historyList
    }

    override fun clear() {
        historyList = emptyList()
        _rollHistoryList.update { historyList }
    }

}
