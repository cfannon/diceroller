package com.example.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.diceroller.diceroller.DiceRollerScreenLayout
import com.example.diceroller.diceroller.DiceRollerViewModel
import com.example.diceroller.history.HistoryScreenLayout
import com.example.diceroller.history.HistoryScreenViewModel
import com.example.diceroller.ui.theme.DiceRollerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()
    val diceRollerViewModel: DiceRollerViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = AppDestinations.DICE_ROLLER_ROUTE
    ) {
        composable(AppDestinations.DICE_ROLLER_ROUTE) {
            DiceRollerScreenLayout(
                viewModel = diceRollerViewModel,
                onNavigationToHistoryClick = {
                    navController.navigate(AppDestinations.HISTORY_ROUTE)
                }
            )
        }
        composable(AppDestinations.HISTORY_ROUTE) {
            HistoryScreenLayout(
                viewModel = HistoryScreenViewModel(),
                onNavigationToDiceRollerClick = {
                    navController.navigate(AppDestinations.DICE_ROLLER_ROUTE)
                }
            )
        }
    }
}
