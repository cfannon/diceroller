package com.example.diceroller

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DiceRollerViewModel : ViewModel() {

    private val _diceValue = MutableStateFlow(0)
    val diceValue = _diceValue.asStateFlow()

    private val generator = TestNumberGenerator()

    private val _finalResult = MutableStateFlow(0)
    val finalResult = _finalResult.asStateFlow()

    private val _diceQuantity = MutableStateFlow(1)
    val diceQuantity = _diceQuantity.asStateFlow()

    fun onDiceSelectionClick(selectedDiceValue: Int) {
        _diceValue.update { selectedDiceValue }

    }

    fun onDiceRollClick() {
        // Final Result = Selected Dice Value Roll + (Selected Dice Value per Dice Quantity) + Dice Modifier
        var diceRollTotal = 0
        for (i in 1..diceQuantity.value) {
            diceRollTotal += generator.rollDice(diceValue.value)
        }
        _finalResult.update { diceRollTotal }
//        + diceModifierValue
    }

    fun onDiceQuantityDownClick() {
        _diceQuantity.update { if (it > 1) it-1 else 1 }
    }

    fun onDiceQuantityUpClick() {
        _diceQuantity.update { it+1 }
    }




}