package com.example.diceroller.customdice

import androidx.lifecycle.ViewModel
import com.example.diceroller.diceroller.Dice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CustomDiceScreenViewModel(
    private val customDiceRepo: CustomDiceEntryRepository = CustomDiceEntryRepositoryImpl) : ViewModel()
{

    private val _uiState = MutableStateFlow(CustomDiceUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getCustomDiceList()
    }

    private fun getCustomDiceList() {
        _uiState.update { CustomDiceUiState(customDiceRepo.getAll()) }
    }

    fun onDeleteCustomDiceClick() {

    }

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

    }


}

class CustomDiceUiState(
    val customDiceList : List<CustomDiceEntry> = emptyList(),
    val tempFinalResult : String = "0"
)

data class CustomDiceEntry(
    val customDiceName : String = "Custom Dice # 1",
    val dice : Dice = Dice.D6,
    val quantity : Int = 2,
    val diceModifier : Int = 5
)
