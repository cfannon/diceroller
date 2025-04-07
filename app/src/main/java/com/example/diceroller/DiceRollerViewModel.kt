package com.example.diceroller

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DiceRollerViewModel : ViewModel() {

    private val _diceValue = MutableStateFlow(0)
    val diceValue = _diceValue.asStateFlow()

    fun onDiceSelectionClick(selectedDiceValue: Int) {
        _diceValue.update { selectedDiceValue }

    }

    fun onDiceQuantityUpClick() {


    }

    fun onDiceQuantityDownClick() {

    }


}