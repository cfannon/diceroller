package com.example.diceroller.diceroller

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diceroller.R
import com.example.diceroller.ui.theme.DiceRollerTheme

enum class Dice(val max: Int, val drawableResource: Int, val label: String) {
    D2(2, R.drawable.d2, "d2"),
    D4(4, R.drawable.d4, "d4"),
    D6(6, R.drawable.d6,"d6"),
    D8(8, R.drawable.d8, "d8"),
    D10(10, R.drawable.d10, "d10"),
    D12(12, R.drawable.d12, "d12"),
    D20(20, R.drawable.d20, "d20"),
    D100(100, R.drawable.d10, "d100")
}

@Composable
fun DiceSelectionSection(viewModel: DiceRollerViewModel, uiState: UiState) {
    Column() {
        DiceSelection(selectedDice = uiState.diceValue) { selectedDiceValue ->
            viewModel.onDiceSelectionClick(selectedDiceValue)
        }

        Spacer(modifier = Modifier.height(12.dp))

        ModifierButtons(
            rollTypeState = uiState.rollType,
            onRollTypeClick = viewModel::applyRollTypeClick,
            onDiceQuantityDownClick = viewModel::onDiceQuantityDownClick,
            onDiceQuantityUpClick = viewModel::onDiceQuantityUpClick,
            diceQuantity = uiState.diceQuantity,
            onDiceModifierDownClick = viewModel::onDiceModifierDownClick,
            onDiceModifierUpClick = viewModel::onDiceModifierUpClick,
            diceModifierResult = uiState.diceModifierResult,
        )
    }
}

@Composable
fun DiceSelection(selectedDice: Dice?, onClick: (Dice) -> Unit) {
    val values = Dice.entries.toTypedArray()
    val middle = values.size / 2

    // Dice Selection Buttons - "d2", "d4", "d6", "d8"
    DiceButtonRow(start = 0, finish = middle,  selectedDice = selectedDice , onClick = onClick)
    // Dice Selection Buttons - "d10", "d12", "d20", "d100"
    DiceButtonRow(start = middle, finish = values.size, selectedDice = selectedDice ,onClick = onClick)
}

@Composable
fun ModifierButtons(
    modifier: Modifier = Modifier,
    rollTypeState: RollTypeState,
    onRollTypeClick: (RollTypeState) -> Unit,
    onDiceQuantityDownClick: () -> Unit,
    onDiceQuantityUpClick: () -> Unit,
    diceQuantity: Int,
    onDiceModifierDownClick: () -> Unit,
    onDiceModifierUpClick: () -> Unit,
    diceModifierResult: String,
) {

    Row(
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ){
        // Roll Type Card
        Card(
            shape = RectangleShape,
            modifier = Modifier
                .height(40.dp)
                .width(115.dp)
        ) {
            RollTypeRow(selected = rollTypeState, onClick = onRollTypeClick)
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Dice Quantity Card
        // - Should not be able to go below 1 dice
        Card(
            shape = RectangleShape,
            modifier = Modifier
                .height(40.dp)
                .width(115.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_subtractbox),
                    contentDescription = "Subtract dice",
                    modifier = modifier
                        .clickable { onDiceQuantityDownClick() }
                        .height(36.dp)
                        .width(36.dp)
                        .padding(2.dp)
                )

                Text(
                    text = "${diceQuantity}d",
                    Modifier
                        .width(40.dp),
                    textAlign = TextAlign.Center,
                )

                Icon(
                    painter = painterResource(R.drawable.icon_addbox),
                    contentDescription = "Add dice",
                    modifier = modifier
                        .clickable { onDiceQuantityUpClick() }
                        .height(36.dp)
                        .width(36.dp)
                        .padding(2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Dice Modifier Card
        Card(
            shape = RectangleShape,
            modifier = Modifier
                .height(40.dp)
                .width(115.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_subtractbox),
                    contentDescription = "Subtract modifier",
                    modifier = modifier
                        .clickable { onDiceModifierDownClick() }
                        .height(36.dp)
                        .width(36.dp)
                        .padding(2.dp)
                )

                Text(
                    text = diceModifierResult,
                    Modifier
                        .width(40.dp),
                    textAlign = TextAlign.Center,
                )

                Icon(
                    painter = painterResource(R.drawable.icon_addbox),
                    contentDescription = "Add modifier",
                    modifier = modifier
                        .clickable { onDiceModifierUpClick() }
                        .height(36.dp)
                        .width(36.dp)
                        .padding(2.dp)
                )
            }
        }
    }
}

@Composable
fun DiceSelectionButton(
    modifier: Modifier = Modifier,
    dice: Dice,
    isSelected: Boolean,
    onClick: (Dice) -> Unit
) {
    val unselectedColor = MaterialTheme.colorScheme.primary
    val selectedColor = MaterialTheme.colorScheme.secondary

    Icon(
        painter = painterResource(dice.drawableResource),
        contentDescription = dice.label,
        tint = if (isSelected) selectedColor else unselectedColor,
        modifier = modifier
            .clickable { onClick(dice) }
    )
}

@Composable
fun DiceButtonRow (
    start: Int,
    finish: Int,
    selectedDice: Dice?,
    onClick: (Dice) -> Unit
) {
    val values = Dice.entries.toTypedArray()

    Row(
        Modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
                bottom = 4.dp,
                start = 16.dp,
                end = 16.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val equalWidthModifier = Modifier.weight(1f)
        for (i in start until finish) {
            val diceValue = values[i]
            DiceSelectionButton(
                equalWidthModifier,
                dice = diceValue,
                isSelected = selectedDice == diceValue
            ) { onClick(diceValue) }
        }
    }
}

@Composable
fun RollTypeSelectionButton(
    modifier: Modifier = Modifier,
    roll: RollTypeState,
    isSelected: Boolean,
    onClick: (RollTypeState) -> Unit
) {
    val unselectedColor = MaterialTheme.colorScheme.primary
    val selectedColor = MaterialTheme.colorScheme.secondary

    Icon(
        painter = painterResource(roll.drawableResource),
        contentDescription = roll.label,
        tint = if (isSelected) selectedColor else unselectedColor,
        modifier = modifier
            .clickable { onClick(roll) }
            .height(32.dp)
            .width(32.dp)
            .padding(2.dp)
    )
}

@Composable
fun RollTypeRow(
    selected: RollTypeState,
    onClick: (RollTypeState) -> Unit
) {
    val rollTypes = RollTypeState.entries.toTypedArray()

    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in rollTypes.indices){
            val rollTypeValue = rollTypes[i]
            RollTypeSelectionButton(
                roll = rollTypeValue,
                isSelected = rollTypeValue == selected
            ) { onClick(rollTypeValue) }
        }
    }
}

@Preview
@Composable
fun DiceSelectionPreview() {
    DiceRollerTheme {
        DiceSelectionButton(Modifier, Dice.D20, false) {
        }
    }
}
