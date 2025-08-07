package com.example.diceroller.diceroller

interface NumberGenerator {

    fun rollDice(max: Int) : Int
}

class TestNumberGenerator : NumberGenerator {
    override fun rollDice(max: Int): Int {
        return (1..max).random()
    }
}