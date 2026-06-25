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

import android.content.ClipData
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bri1234.scicalc36.R
import com.bri1234.scicalc36.viewModels.CalculatorViewModel

@Composable
fun CalculatorView(calculatorViewModel: CalculatorViewModel) {
    val clipboard = LocalClipboard.current
    val context = LocalContext.current
    val displayCopiedText = stringResource(R.string.display_copied_to_clipboard)

    Column(modifier = Modifier.fillMaxSize()) {

        CalculatorDisplayView(
            calculatorViewModel.displayState.value,
            Modifier.fillMaxWidth()
                .weight(1.5f)
                .padding(12.dp, 12.dp, 12.dp, 12.dp),
            onLongPress = { displayText ->
                clipboard.nativeClipboard.setPrimaryClip(
                    ClipData.newPlainText("display", displayText)
                )
                Toast.makeText(context, displayCopiedText, Toast.LENGTH_SHORT).show()
            }
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
