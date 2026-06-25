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

package com.bri1234.scicalc36

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import com.bri1234.scicalc36.ui.theme.SciCalc36Theme
import com.bri1234.scicalc36.viewModels.CalculatorViewModel
import com.bri1234.scicalc36.views.CASE_COLOR
import com.bri1234.scicalc36.views.CalculatorView

class MainActivity : ComponentActivity() {

    val calculatorViewModel = CalculatorViewModel()

    /**
     * The main activity of the SciCalc 36 calculator app.
     * It sets up the UI using Jetpack Compose and displays the calculator buttons in a grid layout.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            SciCalc36Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = CASE_COLOR
                ) {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .padding(innerPadding)
                                .background(CASE_COLOR)
                        ) {
                            CalculatorView(calculatorViewModel)
                        }
                    }
                }
            }
        }
    }
}
