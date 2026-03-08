package com.bri1234.ti36calculator.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.bri1234.ti36calculator.CalculatorButton
import com.bri1234.ti36calculator.CalculatorDisplayState
import com.bri1234.ti36calculator.Ti36Simulator

/**
 * ViewModel for the TI-36 calculator app.
 * Manages the state of the calculator display and handles button presses.
 */
class CalculatorViewModel : ViewModel() {
    val simulator = Ti36Simulator()

    private val _displayState = mutableStateOf(CalculatorDisplayState())
    val displayState: State<CalculatorDisplayState> = _displayState

    fun onButtonPressed(button: CalculatorButton) {
        simulator.buttonPressed(button)
    }
}