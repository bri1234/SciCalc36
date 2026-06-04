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

package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.enums.CalculatorButton
import com.bri1234.ti36calculator.enums.DisplayIndicators
import org.junit.Assert.assertEquals

val strToDisplayLabel = mapOf(
    "M" to DisplayIndicators.MEMORY,
    "2nd" to DisplayIndicators.SECOND,
    "3rd" to DisplayIndicators.THIRD,
    "HYP" to DisplayIndicators.HYP,
    "BIN" to DisplayIndicators.BIN,
    "OCT" to DisplayIndicators.OCT,
    "HEX" to DisplayIndicators.HEX,
    "STAT" to DisplayIndicators.STAT,
    "DEG" to DisplayIndicators.DEG,
    "RAD" to DisplayIndicators.RAD,
    "GRAD" to DisplayIndicators.GRAD,
    "x" to DisplayIndicators.X,
    "r" to DisplayIndicators.R,
    "()" to DisplayIndicators.PARENTHESES,
)

private val strFirstFuncToButton = mapOf(
    // row 1
    "ac/on" to CalculatorButton.AC_ON,
    // row 2
    "3rd" to CalculatorButton.THIRD,
    "hyp" to CalculatorButton.HYP,
    "log" to CalculatorButton.LOG,
    "ln" to CalculatorButton.LN,
    "ce/c" to CalculatorButton.CE_C,
    // row 3
    "2nd" to CalculatorButton.SECOND,
    "sin" to CalculatorButton.SIN,
    "d" to CalculatorButton.SIN,
    "cos" to CalculatorButton.COS,
    "e" to CalculatorButton.COS,
    "tan" to CalculatorButton.TAN,
    "f" to CalculatorButton.TAN,
    "y^x" to CalculatorButton.Y_POW_X,
    // row 4
    "x<>y" to CalculatorButton.X_SWAP_Y,
    "1/x" to CalculatorButton.ONE_DIV_X,
    "a" to CalculatorButton.ONE_DIV_X,
    "x^2" to CalculatorButton.X_SQUARED,
    "b" to CalculatorButton.X_SQUARED,
    "sqrt" to CalculatorButton.SQRT_X,
    "c" to CalculatorButton.SQRT_X,
    "/" to CalculatorButton.DIVIDE,
    // row 5
    "s+" to CalculatorButton.SUM_PLUS,
    "ee" to CalculatorButton.EE,
    "(" to CalculatorButton.LEFT_PARENTHESES,
    ")" to CalculatorButton.RIGHT_PARENTHESES,
    "*" to CalculatorButton.MULTIPLY,
    // row 6
    "sto" to CalculatorButton.STORE,
    "7" to CalculatorButton.SEVEN,
    "8" to CalculatorButton.EIGHT,
    "9" to CalculatorButton.NINE,
    "-" to CalculatorButton.MINUS,
    // row 7
    "rcl" to CalculatorButton.RECALL,
    "4" to CalculatorButton.FOUR,
    "5" to CalculatorButton.FIVE,
    "6" to CalculatorButton.SIX,
    "+" to CalculatorButton.PLUS,
    // row 8
    "ab/c" to CalculatorButton.A_B_C,
    "1" to CalculatorButton.ONE,
    "2" to CalculatorButton.TWO,
    "3" to CalculatorButton.THREE,
    "=" to CalculatorButton.EQUAL,
    // row 9
    "->" to CalculatorButton.BACK,
    "0" to CalculatorButton.ZERO,
    "." to CalculatorButton.DOT,
    "+/-" to CalculatorButton.PLUS_MINUS,
)

private val strSecondFuncToButton = mapOf(
    // row 1
    // row 2
    "drg" to CalculatorButton.HYP,
    "10^x" to CalculatorButton.LOG,
    "e^x" to CalculatorButton.LN,
    "fix" to CalculatorButton.CE_C,
    // row 3
    "asin" to CalculatorButton.SIN,
    "acos" to CalculatorButton.COS,
    "atan" to CalculatorButton.TAN,
    "yrootx" to CalculatorButton.Y_POW_X,
    // row 4
    "csr" to CalculatorButton.X_SWAP_Y,
    "frq" to CalculatorButton.ONE_DIV_X,
    "x_" to CalculatorButton.X_SQUARED,
    "sxn-1" to CalculatorButton.SQRT_X,
    "sxn" to CalculatorButton.DIVIDE,
    // row 5
    "s-" to CalculatorButton.SUM_PLUS,
    "n" to CalculatorButton.EE,
    "y_" to CalculatorButton.LEFT_PARENTHESES,
    "syn-1" to CalculatorButton.RIGHT_PARENTHESES,
    "syn" to CalculatorButton.MULTIPLY,
    // row 6
    "sx" to CalculatorButton.STORE,
    "sx2" to CalculatorButton.SEVEN,
    "sy" to CalculatorButton.EIGHT,
    "sy2" to CalculatorButton.NINE,
    "sxy" to CalculatorButton.MINUS,
    // row 7
    "sum" to CalculatorButton.RECALL,
    "itc" to CalculatorButton.FOUR,
    "slp" to CalculatorButton.FIVE,
    "x'" to CalculatorButton.SIX,
    "y'" to CalculatorButton.PLUS,
    // row 8
    "d/c" to CalculatorButton.A_B_C,
    ">in" to CalculatorButton.ONE,
    ">gal" to CalculatorButton.TWO,
    ">lb" to CalculatorButton.THREE,
    ">dd" to CalculatorButton.EQUAL,
    // row 9
    "npr" to CalculatorButton.BACK,
    ">°f" to CalculatorButton.ZERO,
    ">oz" to CalculatorButton.DOT,
    "p>r" to CalculatorButton.PLUS_MINUS,
)

private val strThirdFuncToButton = mapOf(
    // row 1
    // row 2
    "drg>" to CalculatorButton.HYP,
    "x!" to CalculatorButton.LOG,
    "cbrt" to CalculatorButton.LN,
    "const" to CalculatorButton.CE_C,
    // row 3
    "%" to CalculatorButton.Y_POW_X,
    // row 4
    "stat1" to CalculatorButton.X_SWAP_Y,
    "pi" to CalculatorButton.DIVIDE,
    // row 5
    "stat2" to CalculatorButton.SUM_PLUS,
    "dec" to CalculatorButton.EE,
    "hex" to CalculatorButton.LEFT_PARENTHESES,
    "oct" to CalculatorButton.RIGHT_PARENTHESES,
    "bin" to CalculatorButton.MULTIPLY,
    // row 6
    "and" to CalculatorButton.STORE,
    "or" to CalculatorButton.SEVEN,
    "xor" to CalculatorButton.EIGHT,
    "xnor" to CalculatorButton.NINE,
    "not" to CalculatorButton.MINUS,
    // row 7
    "exc" to CalculatorButton.RECALL,
    "cor" to CalculatorButton.FOUR,
    "flo" to CalculatorButton.FIVE,
    "sci" to CalculatorButton.SIX,
    "eng" to CalculatorButton.PLUS,
    // row 8
    "f<>dc" to CalculatorButton.A_B_C,
    ">cm" to CalculatorButton.ONE,
    ">l" to CalculatorButton.TWO,
    ">kg" to CalculatorButton.THREE,
    ">dms" to CalculatorButton.EQUAL,
    // row 9
    "ncr" to CalculatorButton.BACK,
    ">°c" to CalculatorButton.ZERO,
    ">g" to CalculatorButton.DOT,
    "r>p" to CalculatorButton.PLUS_MINUS,
)

private val strHexNumbers = mapOf(
    "A" to CalculatorButton.ONE_DIV_X,
    "B" to CalculatorButton.X_SQUARED,
    "C" to CalculatorButton.SQRT_X,
    "D" to CalculatorButton.SIN,
    "E" to CalculatorButton.COS,
    "F" to CalculatorButton.TAN,
)

/**
 * Simulates a sequence of calculator button presses from a space-separated input string.
 *
 * Tokens are matched against the first, second, and third function labels. Second and third
 * function tokens automatically press the corresponding modifier key before the target button.
 *
 * @param inputStr Space-separated button/function tokens to execute.
 * @return Unit.
 */
fun CalculatorCore.input(inputStr: String) {
    if (inputStr.isBlank()) return

    val tokenList = inputStr.lowercase().split(" ")
    for (token in tokenList) {
        val button1 = strFirstFuncToButton[token]
        if (button1 != null) {
            buttonPressed(button1)
            continue
        }

        val button2 = strSecondFuncToButton[token]
        if (button2 != null) {
            buttonPressed(CalculatorButton.SECOND)
            buttonPressed(button2)
            continue
        }

        val button3 = strThirdFuncToButton[token]
        if (button3 != null) {
            buttonPressed(CalculatorButton.THIRD)
            buttonPressed(button3)
            continue
        }

        val buttonHex = strHexNumbers[token]
        if (buttonHex != null) {
            check(isNumberModeHex()) { "Hexadecimal input is not allowed when not in HEX mode: $token" }
            buttonPressed(buttonHex)
            continue
        }

        throw IllegalArgumentException("Unknown token: $token")
    }
}

/**
 * Asserts that the calculator display shows the expected mantissa and exponent strings.
 *
 * The expected values are padded to the display width before comparison.
 *
 * @param mantissa Expected mantissa string without leading display padding.
 * @param exponent Expected exponent string without leading display padding.
 * @return Unit.
 */
fun CalculatorCore.assertDisplay(mantissa: String, exponent: String) {
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

/**
 * Asserts the complete set of currently visible display labels from string labels.
 *
 * Every provided label string must resolve to a display label. Labels listed in [expectedDisplayLabelsStr]
 * are expected to be visible, and all other display labels are expected to be hidden.
 *
 * @param expectedDisplayLabelsStr Space-separated string labels expected to be visible.
 * @return Unit.
 */
fun CalculatorCore.assertDisplayLabels(expectedDisplayLabelsStr: String) {
    val displayData = getDisplayData()

    val expectedDisplayLabels = expectedDisplayLabelsStr.split(" ").map {
        expectedDisplayLabel -> strToDisplayLabel[expectedDisplayLabel] ?: throw IllegalArgumentException("Unknown display label: $expectedDisplayLabel")
    }.toSet()

    for (displayIndicator in DisplayIndicators.entries) {
        val shallBeVisible = displayIndicator in expectedDisplayLabels
        val isVisible = displayIndicator in displayData.displayIndicators

        assertEquals("Expected display label: $shallBeVisible but is: $isVisible",
            shallBeVisible, isVisible)
    }
}

/**
 * Executes a list of test steps and verifies the display state after each step.
 *
 * Each step sends its input sequence, checks the numeric display, and then checks every
 * display label against the labels listed as visible for that step.
 *
 * @return Unit.
 */
fun CalculatorCore.testStep(input: String, mantissa: String, exponent: String, displayLabels: String) {
    input(input)
    assertDisplay(mantissa, exponent)
    assertDisplayLabels(displayLabels)
}
