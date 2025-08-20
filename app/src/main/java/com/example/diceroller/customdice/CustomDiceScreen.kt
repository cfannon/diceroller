package com.example.diceroller.customdice

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diceroller.R

@Composable
fun CustomDiceScreenLayout(
    modifier: Modifier = Modifier,
    viewModel: CustomDiceScreenViewModel = CustomDiceScreenViewModel()
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        val uiState by viewModel.uiState.collectAsState()

        Column() {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Custom Dice",
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_add),
                    contentDescription = "Add custom dice",
                    modifier = modifier
                        .clickable { viewModel.onCreateCustomDiceClick() }
                        .height(32.dp)
                        .width(32.dp)
                        .padding(2.dp)
                )
                Text(
                    text = "Create a custom preset dice",
                )
            }

            // Displays list of custom rolls if any have been made, otherwise displays message
            if (uiState.finalCustomDiceList.isEmpty()) {
                Row(
                    Modifier.fillMaxWidth(),
                ) {
                    Text(
                        "Customize a preset dice roll to get started.",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            } else {
                LazyColumn(
                    Modifier.fillMaxWidth()
                ) {
                    items(uiState.finalCustomDiceList) { entry ->
                        CustomDiceItem(entry = entry)
                    }
                    item {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Rearrange",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary,
                                modifier = modifier
                                    .clickable { } //TODO: Click to make items able to be rearranged
                            )

                            Text(
                                text = "Delete Entry",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary,
                                modifier = modifier
                                    .clickable { viewModel.onDeleteCustomDiceClick() }
                                //TODO: Click to make items able to be selected and deletable
                            )
                        }
                    }
                }
            }
        }

        // Results
        Column() {
            ResultsSection(
                finalResult = uiState.tempFinalResult
            )
        }
    }
}

@Composable
fun CustomDiceItem(
    entry: CustomDiceEntry
) {
    Card(
        modifier = Modifier
            .height(56.dp)
            .padding(4.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { } //TODO: Click to roll custom dice
        ) {
            Icon(
                painter = painterResource(entry.dice.drawableResource),
                contentDescription = entry.dice.label,
                modifier = Modifier
                    .size(48.dp, 48.dp)
                    .padding(4.dp)
            )

            CustomDiceItemLabel(entry = entry)
        }
    }
}

@Composable
fun CustomDiceItemLabel(
    modifier: Modifier = Modifier,
    entry: CustomDiceEntry
) {
    Row(
        Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = entry.customDiceName,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
        )

        Text(
            text = entry.quantity.toString() + entry.dice.label + " + " + entry.diceModifier.toString(),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
fun ResultsSection(
    finalResult: String,
//    finalResultColor: Color,
//    diceRollPlusModifier: String
) {
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
//                color = finalResultColor,
                fontSize = 88.sp
            )
//            Text(
//                text = diceRollPlusModifier,
//                fontSize = 20.sp
//            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomDiceItemPreview() {
    CustomDiceItem(entry = CustomDiceEntry()
    )
}

@Preview(showBackground = true)
@Composable
private fun CustomDiceItemLabelPreview() {
    CustomDiceItemLabel(entry = CustomDiceEntry()
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CustomDiceScreenPreview() {
    CustomDiceScreenLayout(
//        onAddCustomDiceClick = {}
    )
}
