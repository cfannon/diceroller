package com.example.diceroller

import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
                    AppName()
                }
            }
        }
    }
}

@Composable
fun AppName(modifier: Modifier = Modifier) {
    Column (modifier.fillMaxHeight()) {

        // App Title
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

        // Dice Selection Title
        Row (Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Choose your weapon!"
            )
        }

        // Dice Selection Buttons - "d2", "d4", "d6", "d8"
        Row (
            Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 5.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { /*TODO*/ },
                shape = RectangleShape,
                modifier = modifier
                    .height(70.dp)
                    .width(70.dp)
            ) {
                Text(
                    text = "d2",
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = { /*TODO*/ },
                shape = RectangleShape,
                modifier = modifier
                    .height(70.dp)
                    .width(70.dp)
            ) {
                Text(
                    text = "d4"
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = { /*TODO*/ },
                shape = RectangleShape,
                modifier = modifier
                    .height(70.dp)
                    .width(70.dp)
            ) {
                Text(
                    text = "d6"
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = { /*TODO*/ },
                shape = RectangleShape,
                modifier = modifier
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
                onClick = { /*TODO*/ },
                shape = RectangleShape,
                modifier = modifier
                    .height(70.dp)
                    .width(70.dp)
            ) {
                Text(
                    text = "d10", Modifier.width(60.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = { /*TODO*/ },
                shape = RectangleShape,
                modifier = modifier
                    .height(70.dp)
                    .width(70.dp)
            ) {
                Text(
                    text = "d12"
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = { /*TODO*/ },
                shape = RectangleShape,
                modifier = modifier
                    .height(70.dp)
                    .width(70.dp)
            ) {
                Text(
                    text = "d20"
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = { /*TODO*/ },
                shape = RectangleShape,
                modifier = modifier
                    .height(70.dp)
                    .width(70.dp)
            ) {
                Text(text = "d100")
            }
        }

        // Number of Dice to Roll and Modifier
        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = "-   1d   +",
                Modifier.width(125.dp).height(40.dp).border(5.dp, Color.Red, RectangleShape),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "+ #",
                Modifier.width(125.dp).height(40.dp).border(5.dp, Color.Red, RectangleShape),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = { /*TODO*/ },
                shape = RectangleShape,
                modifier = modifier
                    .height(40.dp)
                    .width(40.dp)
            ) {
                Text(text = "X")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Primary Action Button - "Roll"
        Row (
            Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button (
                    onClick = { /*TODO*/ },
                    shape = RectangleShape,
                    modifier = modifier
                        .height(50.dp)
                        .width(200.dp)
                ) {
                    Text(
                        text = "Roll!",
                        fontSize = 25.sp,

                    )
            }
        }

        Spacer(modifier = Modifier.height(100.dp))

        // Results
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
                    Text(text = "Results: Total")
                }
                Row() {
                    Text(text = "Results Breakdown")
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    DiceRollerTheme {
        AppName()
    }
}