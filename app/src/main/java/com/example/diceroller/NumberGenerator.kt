package com.example.diceroller

import kotlin.random.Random

interface NumberGenerator {

    fun rollDice(max: Int) : Int
}

class TestNumberGenerator : NumberGenerator {
    override fun rollDice(max: Int): Int {
        return (1..max).random()
    }
}