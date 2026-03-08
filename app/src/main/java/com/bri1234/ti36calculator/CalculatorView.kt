package com.bri1234.ti36calculator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CalculatorView(calculatorViewModel: CalculatorViewModel) {

    Column(modifier = Modifier.fillMaxSize()) {

        CalculatorDisplayView(calculatorViewModel.displayState,
            Modifier.fillMaxWidth()
                .weight(1.5f)
                .padding(12.dp, 12.dp, 12.dp, 12.dp))

        CalculatorButtonsView(Modifier
            .fillMaxWidth()
            .weight(9f)
            .padding(8.dp, 0.dp, 8.dp, 8.dp),
            calculatorViewModel::onButtonPressed)
    }
}
