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

package com.bri1234.ti36calculator.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.bri1234.ti36calculator.CalculatorButton
import com.bri1234.ti36calculator.CalculatorDisplayData
import com.bri1234.ti36calculator.CalculatorCore

/**
 * ViewModel for the TI-36 calculator app.
 * Manages the state of the calculator display and handles button presses.
 */
class CalculatorViewModel(
    val simulator: CalculatorCore = CalculatorCore()
) : ViewModel() {
    private val _displayState = mutableStateOf(CalculatorDisplayData())
    val displayState: State<CalculatorDisplayData> = _displayState

    init {
        updateDisplayState()
    }

    fun onButtonPressed(button: CalculatorButton) {
        simulator.buttonPressed(button)
        updateDisplayState()
    }

    private fun updateDisplayState() {
        _displayState.value = simulator.getDisplayData()
    }
}