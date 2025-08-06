package com.example.diceroller.history

interface HistoryEntryRepository {

    fun save(entry: HistoryEntry)

    fun getAll() : List<HistoryEntry>

    fun clear()
}

// Singleton Implementation
object HistoryEntryRepositoryImpl : HistoryEntryRepository {
    private val historyList = mutableListOf<HistoryEntry>()

    override fun save(entry: HistoryEntry) {
        historyList.add(entry)
    }

    override fun getAll() : List<HistoryEntry> {
        return historyList
    }

    override fun clear() {
        historyList.clear()
    }

}
