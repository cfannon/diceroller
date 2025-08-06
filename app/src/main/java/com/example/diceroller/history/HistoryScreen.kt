package com.example.diceroller.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HistoryScreenLayout(
    modifier: Modifier = Modifier,
    viewModel: HistoryScreenViewModel = HistoryScreenViewModel()
) {

    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        val uiState by viewModel.uiState.collectAsState()

        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Roll History",
                fontSize = 24.sp
            )
        }

        // Displays list of rolls if any have been made
        // Otherwise displays message
        if (uiState.historyList.isEmpty()) {
            Text(
                "No rolls have been made yet.",
                modifier = Modifier.padding(8.dp)
            )
        } else {
            LazyColumn(
                Modifier.fillMaxWidth()
            ) {
                items(uiState.historyList) { entry ->
                    HistoryItem(entry = entry)
                }
            }

            // TODO: 'Clear History' CTA
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
            ) {
                Text(
                    text = "Clear History",
                    fontSize = 12.sp
                )
            }
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
            modifier = Modifier
                .size(48.dp, 48.dp)
                .padding(4.dp)
        )
        HistoryItemLabel(text = entry.rollResult)
    }
}

@Composable
fun HistoryItemLabel(text: String) {
    Text(text = text)
}

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