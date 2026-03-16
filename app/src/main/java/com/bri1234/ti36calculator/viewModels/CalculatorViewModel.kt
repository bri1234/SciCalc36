package com.bri1234.ti36calculator.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.bri1234.ti36calculator.CalculatorButton
import com.bri1234.ti36calculator.CalculatorDisplayData
import com.bri1234.ti36calculator.Ti36Simulator

/**
 * ViewModel for the TI-36 calculator app.
 * Manages the state of the calculator display and handles button presses.
 */
class CalculatorViewModel(
    val simulator: Ti36Simulator = Ti36Simulator()
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
        _displayState.value = simulator.getDisplayState()
    }
}