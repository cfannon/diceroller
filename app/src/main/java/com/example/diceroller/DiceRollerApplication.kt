package com.example.diceroller

import android.app.Application
import com.example.diceroller.data.AppDB

class DiceRollerApplication : Application() {
    val db : AppDB by lazy {
        AppDB.getInstance(this)
    }
}
