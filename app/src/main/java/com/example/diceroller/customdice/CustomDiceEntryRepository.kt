package com.example.diceroller.customdice

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface CustomDiceEntryRepository {

    val fullCustomDiceList : StateFlow<List<CustomDiceEntry>>

    fun saveEntry(entry: CustomDiceEntry)

    fun getAll() : List<CustomDiceEntry>

    fun deleteEntry()
}

object CustomDiceEntryRepositoryImpl : CustomDiceEntryRepository {
    private var customDiceList = listOf<CustomDiceEntry>()
    private val _fullCustomDiceList = MutableStateFlow(customDiceList)
    override val fullCustomDiceList = _fullCustomDiceList.asStateFlow()

    override fun saveEntry(entry: CustomDiceEntry) {
        customDiceList += entry
        _fullCustomDiceList.update { customDiceList }
    }

    override fun getAll() : List<CustomDiceEntry> {
        return customDiceList
    }

    //TODO: Implement "Delete specific entry"
    // TEMP - Currently deletes last entry in list
    override fun deleteEntry() {
        customDiceList -= customDiceList.last()
        _fullCustomDiceList.update { customDiceList }
    }

}
