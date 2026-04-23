package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.views.DisplayLabels
import org.junit.Assert.assertEquals

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
    "2nd" to listOf(CalculatorButton.SECOND),
    "3rd" to listOf(CalculatorButton.THIRD),
    "1/x" to listOf(CalculatorButton.ONE_DIV_X),
    "x^2" to listOf(CalculatorButton.X_SQUARED),
    "sqrt" to listOf(CalculatorButton.SQRT_X),
    "hyp" to listOf(CalculatorButton.HYP),
    "sin" to listOf(CalculatorButton.SIN),
    "cos" to listOf(CalculatorButton.COS),
    "tan" to listOf(CalculatorButton.TAN),
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
    val count = if (mantissa.contains('.')) NUM_MANTISSA_DIGITS + 1 else NUM_MANTISSA_DIGITS
    val mantissaStr1 = mantissa.padStart(count, ' ')
    val exponentStr1 = exponent.padStart(NUM_EXPONENT_DIGITS, ' ')

    var mantissaStr2 = display.displayMantissa.concatToString()
    if (display.displayDecimalPointPos >= 0) {
        val pos = display.displayDecimalPointPos + 1
        mantissaStr2 = mantissaStr2.substring(0, pos) + "." + mantissaStr2.substring(pos)
    }

    val exponentStr2 = display.displayExponent.concatToString()

    assertEquals("Expected mantissa: '$mantissaStr1', actual mantissa: '$mantissaStr2'",
        mantissaStr1, mantissaStr2)

    assertEquals("Expected exponent: '$exponentStr1', actual exponent: '$exponentStr2'",
        exponentStr1, exponentStr2)
}

fun CalculatorSimulator.assertDisplayLabel(displayLabel : DisplayLabels, isVisible: Boolean) {
    val data = getDisplayData()

    assertEquals("Expected display label: $isVisible but is: ${data.displayLabels.contains(displayLabel)}",
        isVisible, data.displayLabels.contains(displayLabel))
}

