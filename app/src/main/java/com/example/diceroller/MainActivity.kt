package com.example.diceroller

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun DiceRollerApp(modifier: Modifier = Modifier) {
    Column (modifier.fillMaxHeight()) {

        val context = LocalContext.current
        val generator = TestNumberGenerator()
        var rollResult by remember { mutableStateOf(0) }
        var diceValue by remember { mutableStateOf(0) }


        HeaderSection()

        Subtitle()

        DiceSelection { selectedDiceValue ->
            diceValue = selectedDiceValue
            Toast.makeText(context, "Dice Selection : $selectedDiceValue", Toast.LENGTH_SHORT).show()
        }

        ModifierButtons()

        Spacer(modifier = Modifier.height(10.dp))

        PrimaryRollButton {
            rollResult = generator.rollDice(diceValue)
            Toast.makeText(context, "Roll Result : $rollResult", Toast.LENGTH_SHORT).show()
        }

        Spacer(modifier = Modifier.height(100.dp))

        ResultsSection(rollResult)
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
fun DiceSelection(onClick: (Int) -> Unit) {
    // Dice Selection Buttons - "d2", "d4", "d6", "d8"
    Row (
        Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { onClick(2) },
            shape = RectangleShape,
            modifier = Modifier
                .height(70.dp)
                .width(70.dp)
        ) {
            Text(
                text = "d2",
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Button(
            onClick = { onClick(4) },
            shape = RectangleShape,
            modifier = Modifier
                .height(70.dp)
                .width(70.dp)
        ) {
            Text(
                text = "d4"
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Button(
            onClick = { onClick(6) },
            shape = RectangleShape,
            modifier = Modifier
                .height(70.dp)
                .width(70.dp)
        ) {
            Text(
                text = "d6"
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Button(
            onClick = { onClick(8) },
            shape = RectangleShape,
            modifier = Modifier
                .height(70.dp)
                .width(70.dp)
        ) {
            Text(
                text = "d8"
            )
        }
    }

    // Dice Selection Buttons - "d10", "d12", "d20", "d100"
    Row (
        Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { onClick(10) },
            shape = RectangleShape,
            modifier = Modifier
                .height(70.dp)
                .width(70.dp)
        ) {
            Text(
                text = "d10", Modifier.width(60.dp)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Button(
            onClick = { onClick(12) },
            shape = RectangleShape,
            modifier = Modifier
                .height(70.dp)
                .width(70.dp)
        ) {
            Text(
                text = "d12"
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Button(
            onClick = { onClick(20) },
            shape = RectangleShape,
            modifier = Modifier
                .height(70.dp)
                .width(70.dp)
        ) {
            Text(
                text = "d20"
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Button(
            onClick = { onClick(100) },
            shape = RectangleShape,
            modifier = Modifier
                .height(70.dp)
                .width(70.dp)
        ) {
            Text(text = "d100")
        }
    }
}

@Composable
fun ModifierButtons() {
    var diceQuantity by remember { mutableStateOf(1) }
    var diceModifier by remember { mutableStateOf(0) }

    Row(
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,

    ){
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
                    onClick = { diceQuantity-- },
                    shape = RectangleShape,
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                        .padding(2.dp),
                ) {
                    Text(text = "-")
                }

                Text(
                    text = "${diceQuantity}d",
                    Modifier
                        .width(60.dp)
                        .height(40.dp),
                    textAlign = TextAlign.Center,
                )

                Button(
                    onClick = { diceQuantity++ },
                    shape = RectangleShape,
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                        .padding(2.dp),
                ) {
                    Text(text = "+")
                }
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

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
                    onClick = { diceModifier-- },
                    shape = RectangleShape,
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                        .padding(2.dp),
                ) {
                    Text(text = "-")
                }

                Text(
                    text = "+ ${diceModifier}",
                    Modifier
                        .width(60.dp)
                        .height(40.dp),
                    textAlign = TextAlign.Center,
                )

                Button(
                    onClick = { diceModifier++ },
                    shape = RectangleShape,
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                        .padding(2.dp),
                ) {
                    Text(text = "+")
                }
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        Button(
            onClick = { /*TODO*/ },
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
                fontSize = 25.sp,

                )
        }
    }
}

@Composable
fun ResultsSection(total: Int) {
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
                Text(text = "Results: $total")
            }
            Row() {
                Text(text = "Results Breakdown")
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