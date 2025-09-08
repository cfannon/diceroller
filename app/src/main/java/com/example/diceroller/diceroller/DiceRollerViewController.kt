package com.example.diceroller.diceroller

import kotlinx.coroutines.flow.StateFlow

interface DiceRollerViewController {
    val uiState: StateFlow<UiState>
    fun onDiceSelectionClick(selectedDice: Dice)
    fun onDiceRollClick()
    fun applyRollTypeClick(rollState: RollTypeState)
    fun onDiceQuantityDownClick()
    fun onDiceQuantityUpClick()
    fun onDiceModifierDownClick()
    fun onDiceModifierUpClick()
    fun onResetClick()
}