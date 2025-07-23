package com.example.diceroller.history

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diceroller.Dice

@Composable
fun HistoryScreenLayout() {

    LazyColumn(
        Modifier.fillMaxWidth()
    ) {
        items(HistoryUiState().historyList) { entry ->
            HistoryItem(entry = entry)
        }
    }

}

@Composable
fun HistoryItem(entry: HistoryEntry) {

    Row(
        Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(entry.dice.drawableResource),
            contentDescription = entry.dice.label,
            modifier = Modifier.size(50.dp, 50.dp)
        )
        HistoryItemLabel(text = entry.rollResult)
    }

}

@Composable
fun HistoryItemLabel(text: String) {
    Text(
        text = text,
        )

}



data class HistoryUiState(
    val historyList: List<HistoryEntry> = List(5) { HistoryEntry() }
)

data class HistoryEntry(
    val dice : Dice = Dice.D20,
    val rollResult: String = "13 : 13 (+ 1 Modifier)",
)

@Preview
@Composable
private fun HistoryItemPreview() {
    HistoryItem(entry = HistoryEntry())
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HistoryScreenPreview() {
    HistoryScreenLayout()
}