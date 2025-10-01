package com.example.diceroller.customdice

import com.example.diceroller.DiceRollerApplication
import com.example.diceroller.data.CustomDiceEntity
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

// In Memory Implementation
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

// On Device/Local Storage Implementation
class LocalCustomDiceEntryRepository(private val diceApp : DiceRollerApplication) : CustomDiceEntryRepository {

    private val customDiceDao = diceApp.db.customDiceDao()
    private val _fullCustomDiceList = MutableStateFlow(getAll())
    override val fullCustomDiceList = _fullCustomDiceList.asStateFlow()

    override fun saveEntry(entry: CustomDiceEntry) {
        customDiceDao.insert(entry.toCustomDiceEntity())
        _fullCustomDiceList.update { getAll() }
    }

    override fun getAll(): List<CustomDiceEntry> {
        return customDiceDao.getAll().map { it.toCustomDiceEntry() }
    }

    override fun deleteEntry() {
        customDiceDao.delete(customDiceDao.getAll().last())
        _fullCustomDiceList.update { getAll() }
    }

    private fun CustomDiceEntity.toCustomDiceEntry() : CustomDiceEntry {
        return CustomDiceEntry(
            customDiceName = customDiceName,
            dice = dice,
            rollType = rollType,
            quantity = quantity,
            diceModifier = diceModifier
        )
    }

    private fun CustomDiceEntry.toCustomDiceEntity() : CustomDiceEntity {
        return CustomDiceEntity(
            id = null,
            customDiceName = customDiceName,
            dice = dice,
            rollType = rollType,
            quantity = quantity,
            diceModifier = diceModifier
        )
    }
}
