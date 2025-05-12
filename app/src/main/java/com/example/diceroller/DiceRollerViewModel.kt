package com.example.diceroller

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.abs

data class UiState(
    val diceValue: Int = 0,
    val diceQuantity: Int = 1,
    val diceModifier: Int = 0,
    var diceModifierResult: String = "+ 0",
    val diceRollPlusModifier: String = "0 + 0",
    val finalResult: String = "0",
    var finalResultColor: Color = Color.Black,
    val finalResultsBreakdown: String = ""
)

class DiceRollerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val generator = TestNumberGenerator()

    private val diceModifierAbsolute
        get() = abs(_uiState.value.diceModifier)

    private var resultsBreakdown = ""

    fun onDiceSelectionClick(selectedDiceValue: DiceValue) {
        _uiState.update {
            it.copy(diceValue = selectedDiceValue.selectedValue)
        }
    }

    fun onDiceRollClick() = _uiState.update { currentState ->
        var diceRollTotal = 0
        resultsBreakdown = ""
        for (i in 1..currentState.diceQuantity) {
            val currentRoll = generator.rollDice(currentState.diceValue)
            diceRollTotal += currentRoll
            if (resultsBreakdown.isBlank()) {
                resultsBreakdown = "$currentRoll"
            } else {
                resultsBreakdown += " + $currentRoll"
            }

            Log.d("ResultsBreakdown", resultsBreakdown)
        }

        // Changes FinalResultColor if minimum or maximum value is rolled
        val diceMinValue = currentState.diceQuantity * 1
        val diceMaxValue = currentState.diceQuantity * currentState.diceValue
        when (diceRollTotal) {
            diceMaxValue -> {
                currentState.finalResultColor = Color.Green
            }
            diceMinValue -> {
                currentState.finalResultColor = Color.Red
            }
            else -> {
                currentState.finalResultColor = Color.Black
            }
        }

        val diceRollPlusModifierUpdate : String
        if (currentState.diceModifier < 0) {
            diceRollPlusModifierUpdate = "$diceRollTotal - $diceModifierAbsolute"
            resultsBreakdown = "$resultsBreakdown - $diceModifierAbsolute"
        } else {
            diceRollPlusModifierUpdate = "$diceRollTotal + ${currentState.diceModifier}"
            resultsBreakdown = "$resultsBreakdown + ${currentState.diceModifier}"
        }
        val finalResultUpdate = diceRollTotal + currentState.diceModifier
        val finalResultValue = "$finalResultUpdate"
        val finalResultsBreakdownUpdate = "$finalResultUpdate : $resultsBreakdown"

        Log.d("ResultsBreakdown", resultsBreakdown)
        currentState.copy(
            diceRollPlusModifier = diceRollPlusModifierUpdate,
            finalResult = finalResultValue,
            finalResultsBreakdown = finalResultsBreakdownUpdate
        )
    }

    fun onDiceQuantityDownClick() {
        _uiState.update {
            val diceQuantityCurrent = it.diceQuantity
            val diceQuantityUpdate = if (diceQuantityCurrent > 1) diceQuantityCurrent-1 else 1
            it.copy(diceQuantity = diceQuantityUpdate)
        }
    }

    fun onDiceQuantityUpClick() {
        _uiState.update {
            it.copy(diceQuantity = it.diceQuantity+1)
        }
    }

    fun onDiceModifierDownClick() {
        _uiState.update {
            val diceModifierUpdate = it.diceModifier-1
            val diceModifierResultUpdate =
                if (diceModifierUpdate < 0) {
                    "- ${abs(diceModifierUpdate)}"
                } else {
                    "+ ${abs(diceModifierUpdate)}"
                }
            it.copy(diceModifier = diceModifierUpdate, diceModifierResult = diceModifierResultUpdate)
        }
    }

    fun onDiceModifierUpClick() {
        _uiState.update {
            val diceModifierUpdate = it.diceModifier+1
            val diceModifierResultUpdate =
                if (diceModifierUpdate < 0) {
                    "- ${abs(diceModifierUpdate)}"
                } else {
                    "+ ${abs(diceModifierUpdate)}"
                }
            it.copy(diceModifier = diceModifierUpdate, diceModifierResult = diceModifierResultUpdate)
        }
    }

    fun onResetClick() {
        _uiState.update { UiState() }
        resultsBreakdown = ""
    }

}
