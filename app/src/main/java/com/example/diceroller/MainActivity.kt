package com.example.diceroller

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diceroller.ui.theme.DiceRollerTheme
import kotlin.math.abs

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
                    DiceRollerApp()
                }
            }
        }
    }
}

@Composable
fun DiceRollerApp(viewModel: DiceRollerViewModel = DiceRollerViewModel()) {
    DiceRollerLayout(viewModel = viewModel)
}

@Composable
fun DiceRollerLayout(modifier: Modifier = Modifier, viewModel: DiceRollerViewModel = DiceRollerViewModel()) {
    Column (modifier.fillMaxHeight()) {

        val context = LocalContext.current
        val uiState by viewModel.uiState.collectAsState()

        HeaderSection()

        Subtitle()

        DiceSelection { selectedDiceValue ->
            viewModel.onDiceSelectionClick(selectedDiceValue)
            Toast.makeText(context, "Dice Selection : $selectedDiceValue", Toast.LENGTH_SHORT).show()
        }

        ModifierButtons(
            onDiceQuantityDownClick = viewModel::onDiceQuantityDownClick,
            onDiceQuantityUpClick = viewModel::onDiceQuantityUpClick,
            diceQuantity = uiState.diceQuantity,
            onDiceModifierDownClick = viewModel::onDiceModifierDownClick,
            onDiceModifierUpClick = viewModel::onDiceModifierUpClick,
            diceModifierResult = uiState.diceModifierResult,
            onResetClick = viewModel::onResetClick
        )

        Spacer(modifier = Modifier.height(10.dp))

        PrimaryRollButton {
            if (uiState.diceValue == 0) {
                Toast.makeText(context, "Please select a dice before rolling.", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.onDiceRollClick()
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        ResultsSection(finalResult = uiState.finalResult, finalResultColor = uiState.finalResultColor, diceRollPlusModifier = uiState.diceRollPlusModifier)

        Spacer(modifier = Modifier.height(25.dp))

        ResultsBreakdownSection(finalResultsBreakdown = uiState.finalResultsBreakdown)
    }
}

@Composable
fun HeaderSection() {
    Row (
        Modifier
            .fillMaxWidth()
            .padding(25.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text (
            text = "DICE ROLLER",
            fontSize = 40.sp
        )
    }
}

@Composable
fun Subtitle() {
    Row (Modifier
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Choose your weapon!"
        )
    }
}

@Composable
fun DiceSelection(onClick: (DiceValue) -> Unit) {
    // Dice Selection Buttons - "d2", "d4", "d6", "d8"
    Row (
        Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        DiceSelectionButton(diceValue = DiceValue.D2, onClick = { DiceValue.D2 } )

        Spacer(modifier = Modifier.width(10.dp))

        DiceSelectionButton(diceValue = DiceValue.D4, onClick = { DiceValue.D4 } )

        Spacer(modifier = Modifier.width(10.dp))

        DiceSelectionButton(diceValue = DiceValue.D6, onClick = { DiceValue.D6 } )

        Spacer(modifier = Modifier.width(10.dp))

        DiceSelectionButton(diceValue = DiceValue.D8, onClick = { DiceValue.D8 } )
    }

    // Dice Selection Buttons - "d10", "d12", "d20", "d100"
    Row (
        Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        DiceSelectionButton(diceValue = DiceValue.D10, onClick = { DiceValue.D10 } )

        Spacer(modifier = Modifier.width(10.dp))

        DiceSelectionButton(diceValue = DiceValue.D12, onClick = { DiceValue.D12 } )

        Spacer(modifier = Modifier.width(10.dp))

        DiceSelectionButton(diceValue = DiceValue.D20, onClick = { DiceValue.D20 } )

        Spacer(modifier = Modifier.width(10.dp))

        DiceSelectionButton(diceValue = DiceValue.D100, onClick = { DiceValue.D100 } )
    }
}

@Composable
fun ModifierButtons(
    onDiceQuantityDownClick: () -> Unit,
    onDiceQuantityUpClick: () -> Unit,
    diceQuantity: Int,
    onDiceModifierDownClick: () -> Unit,
    onDiceModifierUpClick: () -> Unit,
    diceModifierResult: String,
    onResetClick: () -> Unit
) {

    Row(
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ){
        /*
         Dice Quantity:
         - Should not be able to go below 1 dice
         */
        Card(
            shape = RectangleShape,
            modifier = Modifier
                .height(40.dp)
                .width(125.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { onDiceQuantityDownClick() },
                    shape = RectangleShape,
                    modifier = Modifier
                        .height(32.dp)
                        .width(32.dp)
                        .padding(2.dp),
                ) {
                    Text(text = "-")
                }

                Text(
                    text = "${diceQuantity}d",
                    Modifier
                        .width(50.dp),
                    textAlign = TextAlign.Center,
                )

                Button(
                    onClick = { onDiceQuantityUpClick() },
                    shape = RectangleShape,
                    modifier = Modifier
                        .height(32.dp)
                        .width(32.dp)
                        .padding(2.dp),
                ) {
                    Text(text = "+")
                }
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        // Dice Modifier
        Card(
            shape = RectangleShape,
            modifier = Modifier
                .height(40.dp)
                .width(125.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { onDiceModifierDownClick() },
                    shape = RectangleShape,
                    modifier = Modifier
                        .height(32.dp)
                        .width(32.dp)
                        .padding(2.dp),
                ) {
                    Text(text = "-")
                }

                Text(
                    text = diceModifierResult,
                    Modifier
                        .width(50.dp),
                    textAlign = TextAlign.Center,
                )

                Button(
                    onClick = { onDiceModifierUpClick() },
                    shape = RectangleShape,
                    modifier = Modifier
                        .height(32.dp)
                        .width(32.dp)
                        .padding(2.dp),
                ) {
                    Text(text = "+")
                }
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        /*
        Reset Button:
        - Should clear any selected Dice Button
        - Should reset Dice Quantity to 1
        - Should reset Dice Modifier to 0
        - Should clear previously displayed roll
         */
        Button(
            onClick = { onResetClick() },
            shape = RectangleShape,
            modifier = Modifier.wrapContentSize()
//                .height(40.dp)
//                .width(40.dp)
        ) {
            Text(text = "X")
        }
    }
}

@Composable
fun PrimaryRollButton(onClick: () -> Unit) {
    Row (
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button (
            onClick = onClick,
            shape = RectangleShape,
            modifier = Modifier
                .height(50.dp)
                .width(150.dp)
        ) {
            Text(
                text = "Roll!",
                fontSize = 25.sp
                )
        }
    }
}

@Composable
fun ResultsSection(finalResult: String, finalResultColor: Color, diceRollPlusModifier: String) {
    Row (
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = finalResult,
                color = finalResultColor,
                fontSize = 80.sp
            )
            Text(
                text = diceRollPlusModifier,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun ResultsBreakdownSection(finalResultsBreakdown: String) {
    Row(
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center) {
        Column(
            Modifier
                .border(8.dp, Color.Black, RectangleShape)
                .height(200.dp)
                .width(300.dp)
                .padding(20.dp),
        ) {
            Row() {
                Text(text = "Results Breakdown:")
            }
            Row() {
                Text(text = finalResultsBreakdown)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    DiceRollerTheme {
        DiceRollerApp()
    }
}
