package com.bri1234.ti36calculator

class CalculatorDisplayState {
    val digitsLarge = CharArray(11) { ' ' }
    var decimalPointIndex = -1

    val digitsSmall = CharArray(3) { ' ' }

    val displayLabels = mutableSetOf(DisplayLabel.DEG)
}
