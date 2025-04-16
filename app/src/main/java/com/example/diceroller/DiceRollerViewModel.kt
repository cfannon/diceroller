package com.example.diceroller

import android.util.Log
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

    private var diceModifierAbsolute = abs(diceModifier.value)

    private val _diceModifierResult = MutableStateFlow("+ $diceModifierAbsolute")
    val diceModifierResult = _diceModifierResult.asStateFlow()

    private val _diceRollPlusModifier = MutableStateFlow("0 + 0")
    val diceRollPlusModifier = _diceRollPlusModifier.asStateFlow()

    private val _finalResult = MutableStateFlow(0)
    val finalResult = _finalResult.asStateFlow()

    private var resultsBreakdown = ""
    private val _finalResultsBreakdown = MutableStateFlow(resultsBreakdown)
    val finalResultsBreakdown = _finalResultsBreakdown.asStateFlow()

    fun onDiceSelectionClick(selectedDiceValue: Int) {
        _diceValue.update { selectedDiceValue }
    }

    fun onDiceRollClick() {
        var diceRollTotal = 0
        resultsBreakdown = ""
        for (i in 1..diceQuantity.value) {
            val currentRoll = generator.rollDice(diceValue.value)
            diceRollTotal += currentRoll
            if (resultsBreakdown.isBlank()) {
                resultsBreakdown = "$currentRoll"
            } else {
                resultsBreakdown += " + $currentRoll"
            }

            Log.d("ResultsBreakdown", resultsBreakdown)
        }

        if (diceModifier.value < 0) {
            _diceRollPlusModifier.update {"$diceRollTotal - $diceModifierAbsolute"}
            resultsBreakdown = "$resultsBreakdown - $diceModifierAbsolute"
        } else {
            _diceRollPlusModifier.update {"$diceRollTotal + ${diceModifier.value}"}
            resultsBreakdown = "$resultsBreakdown + ${diceModifier.value}"
        }
        _finalResult.update { diceRollTotal + diceModifier.value }
        _finalResultsBreakdown.update { "${finalResult.value} : $resultsBreakdown" }

        Log.d("ResultsBreakdown", resultsBreakdown)
    }

    fun onDiceQuantityDownClick() {
        _diceQuantity.update { if (it > 1) it-1 else 1 }
    }

    fun onDiceQuantityUpClick() {
        _diceQuantity.update { it+1 }
    }

    fun onDiceModifierDownClick() {
        _diceModifier.update { it-1 }
        diceModifierAbsolute = abs(diceModifier.value)
        if (diceModifier.value < 0) {
            _diceModifierResult.update { "- $diceModifierAbsolute" }
        } else {
            _diceModifierResult.update { "+ $diceModifierAbsolute" }
        }
        Log.d("DiceModifierAbsolute", "$diceModifierAbsolute")
    }

    fun onDiceModifierUpClick() {
        _diceModifier.update { it+1 }
        diceModifierAbsolute = abs(diceModifier.value)
        if (diceModifier.value < 0) {
            _diceModifierResult.update { "- $diceModifierAbsolute" }
        } else {
            _diceModifierResult.update { "+ $diceModifierAbsolute" }
        }
        Log.d("DiceModifierAbsolute", "$diceModifierAbsolute")
    }

    fun onResetClick() {
        _diceValue.update { 0 }
        _diceQuantity.update { 1 }
        _diceModifier.update { 0 }
        _diceModifierResult.update { "+ 0" }
        _diceRollPlusModifier.update { "0 + 0"}
        _finalResult.update { 0 }
        resultsBreakdown = ""
    }

}
