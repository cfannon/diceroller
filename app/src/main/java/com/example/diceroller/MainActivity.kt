package com.example.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.diceroller.diceroller.DiceRollerScreenLayout
import com.example.diceroller.diceroller.DiceRollerViewModel
import com.example.diceroller.history.HistoryScreenLayout
import com.example.diceroller.history.HistoryScreenViewModel
import com.example.diceroller.navigation.bottomNavigationItems
import com.example.diceroller.presetdice.PresetDiceScreenLayout
import com.example.diceroller.presetdice.PresetDiceScreenViewModel
import com.example.diceroller.settings.SettingsScreenLayout
import com.example.diceroller.settings.SettingsScreenViewModel
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
    val presetDiceScreenViewModel: PresetDiceScreenViewModel = viewModel()
    val historyScreenViewModel: HistoryScreenViewModel = viewModel()
    val settingsScreenViewModel: SettingsScreenViewModel = viewModel()

    Scaffold(
        bottomBar = {
            AppBottomNavigationBar(navController = navController)
        }
    ){ innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppDestinations.DICE_ROLLER_ROUTE,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(AppDestinations.DICE_ROLLER_ROUTE) {
                DiceRollerScreenLayout(
                    viewModel = diceRollerViewModel
                )
            }
            composable(AppDestinations.DICE_PRESET_ROUTE) {
                PresetDiceScreenLayout(
                    viewModel = presetDiceScreenViewModel
                )
            }
            composable(AppDestinations.HISTORY_ROUTE) {
                HistoryScreenLayout(
                    viewModel = historyScreenViewModel
                )
            }
            composable(AppDestinations.SETTINGS_ROUTE) {
                SettingsScreenLayout(
                    viewModel = settingsScreenViewModel
                )
            }
        }
    }
}

@Composable
fun AppBottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        bottomNavigationItems.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(painterResource(screen.icon), contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected =
                    currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
