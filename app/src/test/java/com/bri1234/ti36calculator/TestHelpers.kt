package com.bri1234.ti36calculator

fun CalculatorSimulator.input(vararg buttons: CalculatorButton) {
    for (button in buttons) {
        buttonPressed(button)
    }
}

private val strToButtons = mapOf(
    "+" to listOf(CalculatorButton.PLUS),
    "-" to listOf(CalculatorButton.MINUS),
    "*" to listOf(CalculatorButton.MULTIPLY),
    "/" to listOf(CalculatorButton.DIVIDE),
    "=" to listOf(CalculatorButton.EQUAL),
    "0" to listOf(CalculatorButton.ZERO),
    "1" to listOf(CalculatorButton.ONE),
    "2" to listOf(CalculatorButton.TWO),
    "3" to listOf(CalculatorButton.THREE),
    "4" to listOf(CalculatorButton.FOUR),
    "5" to listOf(CalculatorButton.FIVE),
    "6" to listOf(CalculatorButton.SIX),
    "7" to listOf(CalculatorButton.SEVEN),
    "8" to listOf(CalculatorButton.EIGHT),
    "9" to listOf(CalculatorButton.NINE),
    "." to listOf(CalculatorButton.DOT),
    "e" to listOf(CalculatorButton.EE),
    "+/-" to listOf(CalculatorButton.PLUS_MINUS),
    "log" to listOf(CalculatorButton.LOG),
    "10^x" to listOf(CalculatorButton.SECOND, CalculatorButton.LOG),
    "x!" to listOf(CalculatorButton.THIRD, CalculatorButton.LOG),
    "ln" to listOf(CalculatorButton.LN),
    "e^x" to listOf(CalculatorButton.SECOND, CalculatorButton.LN),
    "cbrt" to listOf(CalculatorButton.THIRD, CalculatorButton.LN),
    "y^x" to listOf(CalculatorButton.Y_POW_X),
    "yrootx" to listOf(CalculatorButton.SECOND, CalculatorButton.Y_POW_X),
    "%" to listOf(CalculatorButton.THIRD, CalculatorButton.Y_POW_X),
)

fun CalculatorSimulator.input(inputStr: String) {
    val tokenList = inputStr.lowercase().split(" ")
    for (token in tokenList) {
        val buttons = strToButtons[token] ?: throw IllegalArgumentException("Unknown token: $token")
        for (button in buttons) {
            buttonPressed(button)
        }
    }
}

fun CalculatorSimulator.assertDisplay(mantissa: String, exponent: String) {
}

