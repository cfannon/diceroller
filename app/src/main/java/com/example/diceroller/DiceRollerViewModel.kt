package com.example.diceroller

import androidx.lifecycle.ViewModel

class DiceRollerViewModel : ViewModel() {

    var diceValue = 0

    fun onDiceSelectionClick(selectedDiceValue: Int) {
        diceValue = selectedDiceValue

    }

    fun onDiceQuantityUpClick() {


    }

    fun onDiceQuantityDownClick() {

    }


}