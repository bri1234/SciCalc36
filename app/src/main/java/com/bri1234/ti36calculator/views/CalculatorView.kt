/*
 * Ti36Calculator - A TI-36 calculator simulator for Android.
 * Copyright (C) 2026 Torsten Brischalle <torsten@brischalle.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://gnu.org>.
 */

package com.bri1234.ti36calculator.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bri1234.ti36calculator.viewModels.CalculatorViewModel

@Composable
fun CalculatorView(calculatorViewModel: CalculatorViewModel) {

    Column(modifier = Modifier.fillMaxSize()) {

        CalculatorDisplayView(
            calculatorViewModel.displayState.value,
            Modifier.fillMaxWidth()
                .weight(1.5f)
                .padding(12.dp, 12.dp, 12.dp, 12.dp)
        )

        CalculatorButtonsView(
            Modifier
                .fillMaxWidth()
                .weight(9f)
                .padding(8.dp, 0.dp, 8.dp, 8.dp),
            calculatorViewModel::onButtonPressed
        )
    }
}
