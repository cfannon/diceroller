package com.example.diceroller.diceroller

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diceroller.R
import com.example.diceroller.ui.theme.DiceRollerTheme

@Composable
fun DiceRollerScreenLayout(
    modifier: Modifier = Modifier,
    viewModel: DiceRollerViewModel = DiceRollerViewModel(),
) {
    Column (modifier.fillMaxHeight()) {

        val context = LocalContext.current
        val uiState by viewModel.uiState.collectAsState()

        HeaderSection()

        Subtitle()

        DiceSelectionSection(viewModel, uiState)

        Spacer(modifier = Modifier.height(12.dp))

        PrimaryActionButtons(
            onRollButtonClick = viewModel::onDiceRollClick,
            onResetClick = viewModel::onResetClick
        )

        LaunchedEffect(uiState.errorMessage) {
            uiState.errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        ResultsSection(
            finalResult = uiState.finalResult,
            finalResultColor = uiState.finalResultColor,
            diceRollPlusModifier = uiState.diceRollPlusModifier
        )

        Spacer(modifier = Modifier.height(24.dp))

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
            fontSize = 50.sp
        )
    }
}

@Composable
fun Subtitle() {
    Row (
        Modifier
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Choose your weapon!"
        )
    }
}

@Composable
fun PrimaryActionButtons(
    modifier: Modifier = Modifier,
    onRollButtonClick: () -> Unit,
    onResetClick: () -> Unit
) {
    Row (
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.weight(1f))

        // Roll Button
        Button (
            onClick = onRollButtonClick,
            shape = RectangleShape,
            modifier = Modifier
                .height(50.dp)
                .width(150.dp)
                .weight(1f)
        ) {
            Text(
                text = "Roll!",
                fontSize = 25.sp
            )
        }

        /*  Reset Button:
                - Should clear any selected Dice Button
                - Should reset Dice Quantity to 1
                - Should reset Dice Modifier to 0
                - Should clear previously displayed roll
                - Should reset Roll Type to Standard
        */
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_disabled),
                contentDescription = "Reset all",
                tint = MaterialTheme.colorScheme.error,
                modifier = modifier
                    .clickable { onResetClick() }
                    .height(48.dp)
                    .width(48.dp)
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
                fontSize = 88.sp
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
                .border(4.dp, Color.Black, RectangleShape)
                .height(120.dp)
                .width(350.dp)
                .padding(12.dp),
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
private fun AppPreview() {
    DiceRollerTheme {
        DiceRollerScreenLayout(
            viewModel = DiceRollerViewModel()
        )
    }
}
