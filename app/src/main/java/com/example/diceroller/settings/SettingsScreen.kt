package com.example.diceroller.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diceroller.R

@Composable
fun SettingsScreenLayout(
    modifier: Modifier = Modifier,
    viewModel: SettingsScreenViewModel = SettingsScreenViewModel(),
    onNavigationToDiceRollerClick: () -> Unit
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
                .padding(top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            // Back Navigation Button
            Row(
                modifier = Modifier.weight(.25f),
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_chevron_left),
                    contentDescription = "Back",
                    modifier = modifier
                        .clickable(onClick = { onNavigationToDiceRollerClick() })
                        .height(36.dp)
                        .width(36.dp)
                )
            }

            Text(
                text = "Settings",
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f),

                )

            Spacer(modifier = Modifier.weight(.25f))
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SettingsScreenPreview() {
    SettingsScreenLayout(
        onNavigationToDiceRollerClick = {}
    )
}
