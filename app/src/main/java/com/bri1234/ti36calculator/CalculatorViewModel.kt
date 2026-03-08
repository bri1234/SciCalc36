package com.bri1234.ti36calculator

import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    val simulator = Ti36Simulator()

    val displayState = CalculatorDisplayState()

    fun onButtonPressed(button: CalculatorButton) {
        simulator.buttonPressed(button)
    }
}
