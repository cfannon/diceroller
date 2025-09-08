package com.example.diceroller.navigation

import com.example.diceroller.AppDestinations
import com.example.diceroller.R


sealed class BottomNavigationBarItems(
    val route: String,
    val title: String,
    val icon: Int
) {
    data object DiceRoller : BottomNavigationBarItems(
        route = AppDestinations.DICE_ROLLER_ROUTE,
        title = "Dice Roller",
        icon =  R.drawable.icon_dice
    )

    data object CustomDice : BottomNavigationBarItems(
        route = AppDestinations.DICE_CUSTOM_ROUTE,
        title = "Custom Dice",
        icon =  R.drawable.icon_list
    )

    data object History : BottomNavigationBarItems(
        route = AppDestinations.HISTORY_ROUTE,
        title = "History",
        icon = R.drawable.icon_history
    )

    data object Settings : BottomNavigationBarItems(
        route = AppDestinations.SETTINGS_ROUTE,
        title = "Settings",
        icon = R.drawable.icon_settings
    )
}

val bottomNavigationItems = listOf(
    BottomNavigationBarItems.DiceRoller,
    BottomNavigationBarItems.CustomDice,
    BottomNavigationBarItems.History,
    BottomNavigationBarItems.Settings
)
