/*
 * SciCalc 36 - A classic-style scientific calculator inspired by traditional handheld calculator workflows.
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
 * along with this program.  If not, see <https://gnu.org>.
 */

package com.bri1234.scicalc36.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bri1234.scicalc36.CALCULATOR_BUTTON_LIST
import com.bri1234.scicalc36.CALCULATOR_SPECIAL_BUTTON_LIST
import com.bri1234.scicalc36.R
import com.bri1234.scicalc36.enums.CalculatorButton
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit

/** Used to scale the font size to the current font scale, so that the text size remains
 * consistent across different font scales. */
@Composable
private fun fixedSp(value: Float): TextUnit {
    val fontScale = LocalDensity.current.fontScale
    return (value / fontScale * 1.1).sp
}

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
    buttonProperties : com.bri1234.scicalc36.CalculatorButtonProperties,
    modifier: Modifier,
    onButtonPressed: (CalculatorButton) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CalculatorButtonHeading(buttonProperties)

        Button(
            onClick = { onButtonPressed(buttonProperties.button) },
            modifier = Modifier.fillMaxWidth().weight(1f),
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
                    fontSize = fixedSp(16f),
                    lineHeight = fixedSp(16f),
                    color = buttonProperties.test2ndColor,
                    maxLines = 1,
                    softWrap = false
                )
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
                Text(
                    text = buttonProperties.text1st,
                    fontSize = fixedSp(18f),
                    lineHeight = fixedSp(18f),
                    color = buttonProperties.test1stColor,
                    maxLines = 1,
                    softWrap = false,
                    fontWeight = if (buttonProperties.text1stIsBold) FontWeight.Bold else FontWeight.Normal,
                )
            }
        }
    }
}

@Composable
private fun CalculatorButtonHeading(buttonProperties: com.bri1234.scicalc36.CalculatorButtonProperties) {

    if (buttonProperties.text4th.isEmpty()) {
        // center text3 if text4 is empty
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = buttonProperties.text3rd,
                fontSize = fixedSp(16f),
                lineHeight = fixedSp(16f),
                color = buttonProperties.test3rdColor,
                maxLines = 1,
                softWrap = false,
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
            fontSize = fixedSp(16f),
            lineHeight = fixedSp(16f),
            color = buttonProperties.test3rdColor,
            maxLines = 1,
            softWrap = false,
            modifier = Modifier.weight(1f).padding(start = 5.dp),
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = buttonProperties.text4th,
            fontSize = fixedSp(16f),
            lineHeight = fixedSp(16f),
            color = buttonProperties.test4thColor,
            maxLines = 1,
            softWrap = false,
            modifier = Modifier.weight(1f).padding(end = 5.dp),
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
    onButtonPressed: (CalculatorButton) -> Unit
) {
    var showAboutDialog by remember { mutableStateOf(false) }

    Column (modifier = modifier) {
        CalculatorTopButtonRow(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(30.dp, 40.dp),
            onInfoPressed = { showAboutDialog = true },
            onButtonPressed = onButtonPressed
        )

        GridLayout(
            columns = 5,
            rows = 8,
            modifier = Modifier.fillMaxSize(),
            gridCellInfos = CALCULATOR_BUTTON_LIST,
        ) {
            CALCULATOR_BUTTON_LIST.forEach { buttonProperties ->
                CalculatorButton(
                    buttonProperties = buttonProperties,
                    Modifier.padding(4.dp).fillMaxSize(),
                    onButtonPressed = onButtonPressed
                )
            }
        }
    }

    if (showAboutDialog) {
        AboutDialog(onDismiss = { showAboutDialog = false })
    }
}

@Composable
private fun CalculatorTopButtonRow(
    modifier: Modifier,
    onInfoPressed: () -> Unit,
    onButtonPressed: (CalculatorButton) -> Unit
) {
    val appName = stringResource(R.string.app_name)

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(4f)
                .fillMaxHeight()
                .padding(start = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = appName,
                color = TEXT_3RD_COLOR,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Spacer(modifier = Modifier.width(8.dp))
            Surface(
                onClick = onInfoPressed,
                modifier = Modifier.size(28.dp),
                shape = CircleShape,
                color = BUTTON_COLOR_1,
                contentColor = TEXT_1ST_COLOR_1
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "i",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        SimpleCalculatorButton(
            buttonProperties = CALCULATOR_SPECIAL_BUTTON_LIST.first(),
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(4.dp, 0.dp, 4.dp, 0.dp),
            onButtonPressed = onButtonPressed
        )
    }
}

@Composable
private fun SimpleCalculatorButton(
    buttonProperties : com.bri1234.scicalc36.CalculatorButtonProperties,
    modifier: Modifier,
    onButtonPressed: (CalculatorButton) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { onButtonPressed(buttonProperties.button) },
            modifier = Modifier.fillMaxSize(),
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
                    fontSize = fixedSp(18f),
                    lineHeight = fixedSp(18f),
                    color = buttonProperties.test1stColor,
                    maxLines = 1,
                    softWrap = false,
                    fontWeight = if (buttonProperties.text1stIsBold) FontWeight.Bold else FontWeight.Normal,
                )
            }
        }
    }
}
