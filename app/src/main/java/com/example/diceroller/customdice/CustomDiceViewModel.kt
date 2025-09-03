package com.example.diceroller.customdice

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diceroller.diceroller.Dice
import com.example.diceroller.diceroller.DiceRollerViewController
import com.example.diceroller.diceroller.DiceRollerViewModel
import com.example.diceroller.diceroller.RollTypeState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CustomDiceScreenViewModel(
    private val customDiceRepo: CustomDiceEntryRepository = CustomDiceEntryRepositoryImpl,
    private val diceRollerViewModel: DiceRollerViewModel = DiceRollerViewModel()
) : ViewModel(), DiceRollerViewController by diceRollerViewModel {

    var enteredCustomDiceName : String by mutableStateOf("")
    private var selectedCustomDice : Dice? = null
    private var customDiceRollType : RollTypeState = RollTypeState.Standard
    private val customDiceQuantity
        get() = uiState.value.diceQuantity
    private val customDiceModifier
        get() = uiState.value.diceModifier


    val customDiceUIState = customDiceRepo.fullCustomDiceList.map { customDiceEntries ->
        CustomDiceUiState(customDiceEntries)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, CustomDiceUiState())

    // Save to create a custom dice entry
    fun onSaveCustomDiceClick() {
        customDiceRepo.saveEntry(
            CustomDiceEntry(
                customDiceName = enteredCustomDiceName,
                dice = selectedCustomDice ?: Dice.D6,
                rollType = customDiceRollType,
                quantity = customDiceQuantity,
                diceModifier = customDiceModifier
            )
        )
    }

    override fun onDiceSelectionClick(selectedDice: Dice) {
        selectedCustomDice = selectedDice
        diceRollerViewModel.onDiceSelectionClick(selectedDice)
    }

    override fun applyRollTypeClick(rollState: RollTypeState) {
        customDiceRollType = rollState
        diceRollerViewModel.applyRollTypeClick(rollState)
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
    val rollType : RollTypeState = RollTypeState.Standard,
    val quantity : Int = 2,
    val diceModifier : Int = 5
)
