package com.example.diceroller.customdice

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diceroller.diceroller.Dice
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CustomDiceScreenViewModel(
    private val customDiceRepo: CustomDiceEntryRepository = CustomDiceEntryRepositoryImpl
) : ViewModel() {

    var enteredCustomDiceName : String by mutableStateOf("")

    val uiState = customDiceRepo.fullCustomDiceList.map { customDiceEntries ->
        CustomDiceUiState(customDiceEntries)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, CustomDiceUiState())

    // Save to create a custom dice entry
    fun onSaveCustomDiceClick() {
        customDiceRepo.saveEntry(
            CustomDiceEntry(
                customDiceName = enteredCustomDiceName,
                dice = Dice.D6,
                quantity = 2,
                diceModifier = 5
            )
        )
    }

    fun onCustomDiceRollClick() {
    }

    fun onDeleteCustomDiceClick() {
        customDiceRepo.deleteEntry()
    }
}

class CustomDiceUiState(
    val finalCustomDiceList : List<CustomDiceEntry> = emptyList(),
    val tempFinalResult : String = "15",
    val tempResultsBreakdown : String = "4 + 6 ( + 5 Modifier)"
)

data class CustomDiceEntry(
    val customDiceName : String = "Custom Dice # 1",
    val dice : Dice = Dice.D6,
    val quantity : Int = 2,
    val diceModifier : Int = 5
)
