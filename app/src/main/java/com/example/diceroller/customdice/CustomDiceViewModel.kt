package com.example.diceroller.customdice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diceroller.diceroller.Dice
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CustomDiceScreenViewModel(private val customDiceRepo: CustomDiceEntryRepository = CustomDiceEntryRepositoryImpl) : ViewModel() {

    val uiState = customDiceRepo.fullCustomDiceList.map { customDiceEntries ->
        CustomDiceUiState(customDiceEntries)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, CustomDiceUiState())

    // Create custom dice entry
    fun onCreateCustomDiceClick() {
        customDiceRepo.saveEntry(
            CustomDiceEntry(
                customDiceName = "Custom Dice # 1",
                dice = Dice.D6,
                quantity = 2,
                diceModifier = 5
            )
        )
    }

    // Temp dice roll function when clicking on custom dice entry
    // Displays "5"
    fun onCustomDiceRollClick() {
        //TODO: Dice roll logic
    }

    fun onDeleteCustomDiceClick() {
        customDiceRepo.deleteEntry()
    }
}

class CustomDiceUiState(
    val finalCustomDiceList : List<CustomDiceEntry> = emptyList(),
    val tempFinalResult : String = "0"
)

data class CustomDiceEntry(
    val customDiceName : String = "Custom Dice # 1",
    val dice : Dice = Dice.D6,
    val quantity : Int = 2,
    val diceModifier : Int = 5
)
