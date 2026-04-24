package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.views.DisplayLabels
import org.junit.Assert.assertEquals

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
    "cos" to CalculatorButton.COS,
    "tan" to CalculatorButton.TAN,
    "y^x" to CalculatorButton.Y_POW_X,
    // row 4
    "x<>y" to CalculatorButton.X_SWAP_Y,
    "1/x" to CalculatorButton.ONE_DIV_X,
    "x^2" to CalculatorButton.X_SQUARED,
    "sqrt" to CalculatorButton.SQRT_X,
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

fun CalculatorSimulator.input(inputStr: String) {
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

        throw IllegalArgumentException("Unknown token: $token")
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

