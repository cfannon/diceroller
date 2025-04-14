package com.example.diceroller

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.abs

class DiceRollerViewModel : ViewModel() {

    private val generator = TestNumberGenerator()

    private val _diceValue = MutableStateFlow(0)
    val diceValue = _diceValue.asStateFlow()

    private val _diceQuantity = MutableStateFlow(1)
    val diceQuantity = _diceQuantity.asStateFlow()

    private val _diceModifier = MutableStateFlow(0)
    val diceModifier = _diceModifier.asStateFlow()

    private val _diceModifierResult = MutableStateFlow(0)
    val diceModifierResult = _diceModifierResult.asStateFlow()

    private val _diceRollResult = MutableStateFlow(0)
    val diceRollResult = _diceRollResult.asStateFlow()

    private val _diceRollPlusModifier = MutableStateFlow("0 + 0")
    val diceRollPlusModifier = _diceRollPlusModifier.asStateFlow()

    private val _finalResult = MutableStateFlow(0)
    val finalResult = _finalResult.asStateFlow()

    fun onDiceSelectionClick(selectedDiceValue: Int) {
        _diceValue.update { selectedDiceValue }
    }

    fun onDiceRollClick() {
        // Final Result = Selected Dice Value Roll + (Selected Dice Value per Dice Quantity) + Dice Modifier
        var diceRollTotal = 0
        for (i in 1..diceQuantity.value) {
            diceRollTotal += generator.rollDice(diceValue.value)
        }

        if (diceModifier.value < 0) {
            _diceRollPlusModifier.update {"$diceRollTotal ${diceModifier.value}"}
        } else {
            _diceRollPlusModifier.update {"$diceRollTotal + ${diceModifier.value}"}
        }
        _diceModifierResult.update { diceModifier.value }
        _finalResult.update { diceRollTotal + diceModifier.value }
    }

    fun onDiceQuantityDownClick() {
        _diceQuantity.update { if (it > 1) it-1 else 1 }
    }

    fun onDiceQuantityUpClick() {
        _diceQuantity.update { it+1 }
    }

    fun onDiceModifierDownClick() {
        _diceModifier.update { it-1 }
    }

    fun onDiceModifierUpClick() {
        _diceModifier.update { it+1 }
    }

    fun onResetClick() {
        _diceValue.update { 0 }
        _diceQuantity.update { 1 }
        _diceModifier.update { 0 }
        _diceModifierResult.update { 0 }
        _diceRollResult.update { 0 }
        _diceRollPlusModifier.update { "0 + 0"}
        _finalResult.update { 0 }
    }
}
