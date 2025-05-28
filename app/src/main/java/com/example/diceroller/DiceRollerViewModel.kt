package com.example.diceroller

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.abs

data class UiState(
    val diceValue: Dice? = null,
    val diceQuantity: Int = 1,
    val diceModifier: Int = 0,
    var diceModifierResult: String = "+ 0",
    val diceRollPlusModifier: String = "0 + 0",
    val finalResult: String = "0",
    val finalResultColor: Color = Color.Black,
    val finalResultsBreakdown: String = "",
    val rollType: RollTypeState = RollTypeState.Standard,
    val errorMessage: String? = null
)

enum class RollTypeState(val drawableResource: Int, val label: String) {
    Disadvantage(R.drawable.icon_doublearrowdown, "Disadvantage Roll"),
    Standard(R.drawable.icon_checkcircle, "Standard Roll"),
    Advantage(R.drawable.icon_doublearrowup, "Advantage Roll")
}

private const val AdvantageDiceQuantity = 2

class DiceRollerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val generator = TestNumberGenerator()

    private val diceModifierAbsolute
        get() = abs(_uiState.value.diceModifier)

    private var resultsBreakdown = ""

    fun onDiceSelectionClick(selectedDice: Dice) {
        _uiState.update {
            it.copy(diceValue = selectedDice)
        }
    }

    fun onDiceRollClick() = _uiState.update { currentState ->
        resultsBreakdown = ""

        if (currentState.diceValue == null) {
            currentState.copy(errorMessage = "Please select a dice before rolling.")
        } else {

            // Get results of dice roll
            val diceRollTotal = when (currentState.rollType) {
                RollTypeState.Advantage -> rollWithAdvantage(currentState)
                RollTypeState.Disadvantage -> rollWithDisadvantage(currentState)
                RollTypeState.Standard -> rollWithStandard(currentState)
            }

            // Add Modifiers
            val diceRollPlusModifierUpdate = buildResultsWithModifier(currentState, diceRollTotal)

            // Get final results
            val finalResultUpdate = diceRollTotal + currentState.diceModifier
            val finalResultValue = "$finalResultUpdate"
            val finalResultsBreakdownUpdate = "$finalResultUpdate : $resultsBreakdown Modifier)"

            Log.d("ResultsBreakdown", resultsBreakdown)

            // Update state to display results
            currentState.copy(
                diceRollPlusModifier = diceRollPlusModifierUpdate,
                finalResult = finalResultValue,
                finalResultsBreakdown = finalResultsBreakdownUpdate,
                finalResultColor = updateResultColor(currentState, diceRollTotal),
                errorMessage = null
            )
        }
    }

    private fun rollWithStandard(currentState: UiState) : Int {
        var diceRollTotal = 0
        for (i in 1..currentState.diceQuantity) {
            val currentRoll = generator.rollDice(currentState.diceValue?.max ?: 0)
            diceRollTotal += currentRoll
            if (resultsBreakdown.isBlank()) {
                resultsBreakdown = "$currentRoll"
            } else {
                resultsBreakdown += " + $currentRoll"
            }

            Log.d("ResultsBreakdown", resultsBreakdown)
        }
        return diceRollTotal
    }

    private fun rollWithAdvantage(currentState: UiState): Int {
        val diceRollResultsList = List(AdvantageDiceQuantity) {
            generator.rollDice(currentState.diceValue?.max ?: 0)
        }
        resultsBreakdown = diceRollResultsList.joinToString(" vs. ")
        return diceRollResultsList.max()
    }

    private fun rollWithDisadvantage(currentState: UiState) : Int {
         val diceRollResultsList = List(AdvantageDiceQuantity) {
            generator.rollDice(currentState.diceValue?.max ?: 0)
        }
        resultsBreakdown = diceRollResultsList.joinToString(" vs. ")
        return diceRollResultsList.min()
    }

    private fun buildResultsWithModifier(currentState: UiState, diceRollTotal: Int) : String {
        val diceRollPlusModifierUpdate : String
        if (currentState.diceModifier < 0) {
            diceRollPlusModifierUpdate = "$diceRollTotal - $diceModifierAbsolute"
            resultsBreakdown = "$resultsBreakdown ( - $diceModifierAbsolute"
        } else {
            diceRollPlusModifierUpdate = "$diceRollTotal + ${currentState.diceModifier}"
            resultsBreakdown = "$resultsBreakdown ( + ${currentState.diceModifier}"
        }
        return diceRollPlusModifierUpdate
    }

    // Determines FinalResultColor if minimum or maximum value is rolled
    private fun updateResultColor(currentState: UiState, diceRollTotal: Int) : Color {
        var diceMinValue = 1
        var diceMaxValue = (currentState.diceValue?.max ?: 0)

        if (currentState.rollType == RollTypeState.Standard) {
            diceMinValue *= currentState.diceQuantity
            diceMaxValue *= currentState.diceQuantity
        }

        return when (diceRollTotal) {
            diceMaxValue -> Color.Green

            diceMinValue -> Color.Red

            else -> Color.Black
        }
    }

    fun applyRollTypeClick(rollState: RollTypeState) = _uiState.update {
        when (rollState) {
            RollTypeState.Advantage -> {
                onDiceSelectionClick(Dice.D20)
                it.copy(
                    diceQuantity = 2,
                    rollType = rollState
                )
            }

            RollTypeState.Standard -> {
                it.copy(
                    diceQuantity = 1,
                    rollType = rollState
                )
            }

            RollTypeState.Disadvantage -> {
                onDiceSelectionClick(Dice.D20)
                it.copy(
                    diceQuantity = 2,
                    rollType = rollState
                )
            }
        }
    }

    fun onDiceQuantityDownClick() = _uiState.update { currentState ->
        val diceQuantityCurrent = currentState.diceQuantity
        val diceQuantityUpdate = if (diceQuantityCurrent > 1) diceQuantityCurrent-1 else 1
        if (currentState.rollType == RollTypeState.Standard) {
            currentState.copy(diceQuantity = diceQuantityUpdate)
        } else currentState

    }

    fun onDiceQuantityUpClick() = _uiState.update { currentState ->
        if (currentState.rollType == RollTypeState.Standard) {
            currentState.copy(diceQuantity = currentState.diceQuantity + 1)
        } else currentState
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
