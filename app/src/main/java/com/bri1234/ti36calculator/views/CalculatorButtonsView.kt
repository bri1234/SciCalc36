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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Displays a single calculator button with its properties.
 * The button has two lines of text: text1st and text2nd.
 * Above the button, there are two additional lines of text: text3rd and text4th.
 * If text4th is empty, text3rd is centered. Otherwise, text3rd is left-aligned and text4th is right-aligned.
 * @param buttonProperties The properties of the calculator button, including text and colors.
 * @param modifier The modifier to be applied to the button layout.
 */
@Composable
private fun CalculatorButton(
    buttonProperties : com.bri1234.ti36calculator.CalculatorButtonProperties,
    modifier: Modifier,
    onButtonPressed: (com.bri1234.ti36calculator.CalculatorButton) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CalculatorButtonHeading(buttonProperties)

        Button(
            onClick = { onButtonPressed(buttonProperties.button) },
            modifier = modifier, // Modifier.fillMaxSize(),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(containerColor = buttonProperties.buttonColor),
            contentPadding = PaddingValues(2.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = buttonProperties.text2nd,
                    fontSize = 16.sp,
                    color = buttonProperties.test2ndColor,
                    maxLines = 1
                )
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
                Text(
                    text = buttonProperties.text1st,
                    fontSize = 18.sp,
                    color = buttonProperties.test1stColor,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun CalculatorButtonHeading(buttonProperties: com.bri1234.ti36calculator.CalculatorButtonProperties) {

    if (buttonProperties.text4th.isEmpty()) {
        // center text3 if text4 is empty
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = buttonProperties.text3rd,
                fontSize = 16.sp,
                color = buttonProperties.test3rdColor,
                maxLines = 1
            )
        }

        return
    }

    // align text3 left and text4 right
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = buttonProperties.text3rd,
            fontSize = 16.sp,
            color = buttonProperties.test3rdColor,
            modifier = Modifier
                .weight(1f)
                .padding(start = 5.dp),
            maxLines = 1
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = buttonProperties.text4th,
            fontSize = 16.sp,
            color = buttonProperties.test4thColor,
            modifier = Modifier
                .weight(1f)
                .padding(end = 5.dp),
            maxLines = 1,
            textAlign = TextAlign.End
        )
    }
}

/**
 * Displays the calculator buttons in a grid layout.
 * The grid has 5 columns and fills the available space.
 */
@Composable
fun CalculatorButtonsView(
    modifier: Modifier = Modifier,
    onButtonPressed: (com.bri1234.ti36calculator.CalculatorButton) -> Unit
) {
    Column (modifier = modifier) {
        GridLayout(
            columns = 5,
            rows = 1,
            modifier = Modifier.fillMaxWidth().heightIn(30.dp, 40.dp),
            gridCellInfos = _root_ide_package_.com.bri1234.ti36calculator.CALCULATOR_SPECIAL_BUTTON_LIST,
        ) {
            _root_ide_package_.com.bri1234.ti36calculator.CALCULATOR_SPECIAL_BUTTON_LIST.forEach { buttonProperties ->
                SimpleCalculatorButton(
                    buttonProperties = buttonProperties,
                    Modifier.padding(4.dp, 0.dp, 4.dp, 0.dp),
                    onButtonPressed = onButtonPressed
                )
            }
        }

        GridLayout(
            columns = 5,
            rows = 8,
            modifier = Modifier.fillMaxSize(),
            gridCellInfos = _root_ide_package_.com.bri1234.ti36calculator.CALCULATOR_BUTTON_LIST,
        ) {
            _root_ide_package_.com.bri1234.ti36calculator.CALCULATOR_BUTTON_LIST.forEach { buttonProperties ->
                CalculatorButton(
                    buttonProperties = buttonProperties,
                    Modifier.padding(4.dp).fillMaxSize(),
                    onButtonPressed = onButtonPressed
                )
            }
        }
    }
}

@Composable
private fun SimpleCalculatorButton(
    buttonProperties : com.bri1234.ti36calculator.CalculatorButtonProperties,
    modifier: Modifier,
    onButtonPressed: (com.bri1234.ti36calculator.CalculatorButton) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { onButtonPressed(buttonProperties.button) },
            modifier = modifier,
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(containerColor = buttonProperties.buttonColor),
            contentPadding = PaddingValues(2.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = buttonProperties.text1st,
                    fontSize = 18.sp,
                    color = buttonProperties.test1stColor,
                    maxLines = 1
                )
            }
        }
    }
}
