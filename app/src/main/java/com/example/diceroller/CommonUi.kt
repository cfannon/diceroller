package com.example.diceroller

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diceroller.ui.theme.DiceRollerTheme

enum class DiceValue(val drawableResource: Int, val label: String) {
    D2(R.drawable.d2, "d2"),
    D4(R.drawable.d4, "d4"),
    D6(R.drawable.d6,"d6"),
    D8(R.drawable.d8, "d8"),
    D10(R.drawable.d10, "d10"),
    D12(R.drawable.d12, "d12"),
    D20(R.drawable.d20, "d20"),
    D100(R.drawable.d20, "d100")
}

@Composable
fun DiceSelectionButton(diceValue: DiceValue, onClick: (DiceValue) -> Unit) {
    IconButton(onClick = { onClick(diceValue) }) {
        Icon(painter =
        painterResource(diceValue.drawableResource),
            contentDescription = diceValue.label,
            modifier = Modifier
                .size(70.dp)
        )
    }
}

@Preview
@Composable
fun DiceSelectionPreview() {
    DiceRollerTheme {
        DiceSelectionButton(DiceValue.D20) {
        }
    }
}