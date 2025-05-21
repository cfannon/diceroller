package com.example.diceroller

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diceroller.ui.theme.DiceRollerTheme

enum class DiceValue(val selectedValue: Int, val drawableResource: Int, val label: String) {
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
fun DiceSelectionButton(modifier: Modifier = Modifier, diceValue: DiceValue, isSelected: Boolean, onClick: (DiceValue) -> Unit) {
    val unselectedColor = MaterialTheme.colorScheme.primary
    val selectedColor = MaterialTheme.colorScheme.secondary

    Icon(
        painter = painterResource(diceValue.drawableResource),
        contentDescription = diceValue.label,
        tint = if (isSelected) selectedColor else unselectedColor,
        modifier = modifier
            .clickable { onClick(diceValue) }
    )
}

@Composable
fun DiceButtonRow (start: Int, finish: Int, selectedDiceValue: DiceValue?, onClick: (DiceValue) -> Unit) {
    val values = DiceValue.entries.toTypedArray()

    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 4.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val equalWidthModifier = Modifier.weight(1f)
        for (i in start until finish) {
            val diceValue = values[i]
            DiceSelectionButton(
                equalWidthModifier,
                diceValue = diceValue,
                isSelected = selectedDiceValue == diceValue
            ) { onClick(diceValue) }
        }
    }
}

@Preview
@Composable
fun DiceSelectionPreview() {
    DiceRollerTheme {
        DiceSelectionButton(Modifier, DiceValue.D20, false) {
        }
    }
}
