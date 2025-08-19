package com.example.diceroller.customdice

interface CustomDiceEntryRepository {

    fun saveEntry(entry: CustomDiceEntry)

    fun getAll() : List<CustomDiceEntry>

    fun deleteEntry(entry: CustomDiceEntry)
}

object CustomDiceEntryRepositoryImpl : CustomDiceEntryRepository {
    private val customDiceList = mutableListOf<CustomDiceEntry>()

    override fun saveEntry(entry: CustomDiceEntry) {
        customDiceList.add(entry)
    }

    override fun getAll() : List<CustomDiceEntry> {
        return customDiceList
    }

    override fun deleteEntry(entry: CustomDiceEntry) {
        TODO("Not yet implemented")
    }

}
