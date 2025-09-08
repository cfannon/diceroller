package com.example.diceroller.customdice

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.diceroller.diceroller.Dice
import com.example.diceroller.diceroller.DiceRollerViewController
import com.example.diceroller.diceroller.DiceRollerViewModel
import com.example.diceroller.diceroller.RollTypeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.abs

class CustomDiceScreenViewModel(
    private val customDiceRepo: CustomDiceEntryRepository = CustomDiceEntryRepositoryImpl,
    private val diceRollerViewModel: DiceRollerViewModel = DiceRollerViewModel()
) : ViewModel(), DiceRollerViewController by diceRollerViewModel {

    var enteredCustomDiceName : String by mutableStateOf("")

    val customDiceList = customDiceRepo.fullCustomDiceList
    private val _customDiceUIState = MutableStateFlow(CustomDiceUiState())
    val customDiceUIState = _customDiceUIState.asStateFlow()

    // Save to create a custom dice entry
    fun onSaveCustomDiceClick() {
        customDiceRepo.saveEntry(
            CustomDiceEntry(
                customDiceName = enteredCustomDiceName,
                dice = uiState.value.diceValue ?: Dice.D20,
                rollType = uiState.value.rollType,
                quantity = uiState.value.diceQuantity,
                diceModifier = uiState.value.diceModifier
            )
        )
    }

    fun onCustomDiceRollClick(entry: CustomDiceEntry) = _customDiceUIState.update {
        diceRollerViewModel.onResetClick()
        diceRollerViewModel.onDiceSelectionClick(entry.dice)
        diceRollerViewModel.applyRollTypeClick(entry.rollType)

        for ( i in 1 until entry.quantity) {
            diceRollerViewModel.onDiceQuantityUpClick()
        }

        val setDiceModifier = entry.diceModifier
        if (setDiceModifier < 0) {
            for (i in 1..abs(setDiceModifier)) {
                diceRollerViewModel.onDiceModifierDownClick()
            }
        } else {
            for (i in 1..setDiceModifier) {
                diceRollerViewModel.onDiceModifierUpClick()
            }
        }

        diceRollerViewModel.onDiceRollClick()

        CustomDiceUiState(
            customDiceFinalResult = diceRollerViewModel.uiState.value.finalResult,
            customDiceResultsBreakdown = diceRollerViewModel.uiState.value.finalResultsBreakdown
        )
    }

    fun onDeleteCustomDiceClick() {
        customDiceRepo.deleteEntry()
    }
}

class CustomDiceUiState(
    val customDiceFinalResult : String = "0",
    val customDiceResultsBreakdown : String = "0 ( + 0 Modifier)"
)

data class CustomDiceEntry(
    val customDiceName : String = "Custom Dice # 1",
    val dice : Dice = Dice.D6,
    val rollType : RollTypeState = RollTypeState.Standard,
    val quantity : Int = 2,
    val diceModifier : Int = 5
)
