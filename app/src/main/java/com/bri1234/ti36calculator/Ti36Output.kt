package com.bri1234.ti36calculator

import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.log10
import com.bri1234.ti36calculator.utils.ObserverSubject

enum class DisplayNumberFormat {
    FLOAT,
    SCIENTIFIC,
    ENGINEERING,
    OCTAL,
    HEXADECIMAL,
    BINARY
}

class Ti36Output(val display: Ti36Display) {

    val onPrint: ObserverSubject<Unit> = ObserverSubject()

    private var displayNumberFormat: DisplayNumberFormat = DisplayNumberFormat.FLOAT

    fun reset() {
        displayNumberFormat = DisplayNumberFormat.FLOAT
    }

    /**
     * Prints a double value to the display, formatted according to the current number format setting.
     * Handles special cases for NaN and Infinity.
     * @param value The double value to print on the display.
     */
    fun printValue(value: Double) {

        if (value.isNaN()) {
            printNan()
            return
        }

        if (value.isInfinite()) {
            printInf(value > 0)
            return
        }

        when (displayNumberFormat) {
            DisplayNumberFormat.FLOAT -> printValueFloat(value)
            DisplayNumberFormat.SCIENTIFIC -> printValueScientific(value)
            DisplayNumberFormat.ENGINEERING -> printValueEngineering(value)
            DisplayNumberFormat.OCTAL -> printValueOct(value)
            DisplayNumberFormat.HEXADECIMAL -> printValueHex(value)
            DisplayNumberFormat.BINARY -> printValueBin(value)
        }

        onPrint(Unit)
    }

    private fun printValueOct(value: Double) {
    }

    private fun printValueHex(value: Double) {
    }

    private fun printValueBin(value: Double) {
    }

    private fun printValueScientific(value: Double) {
    }

    private fun printValueEngineering(value: Double) {
    }

    private fun printValueFloat(value: Double) {
        val exponent = if (value != 0.0) floor(log10(abs(value))) else 1.0

        if ((exponent >= 10) || (exponent <= -10)) {
            printValueScientific(value)
            return
        }

        var valueStr = value.toBigDecimal().stripTrailingZeros().toPlainString()
        val decimalPointPos = valueStr.indexOf('.')

        if (decimalPointPos != -1) {
            display.decimalPointPos = decimalPointPos - 1
            valueStr = valueStr.removeRange(decimalPointPos, decimalPointPos + 1)
        } else {
            display.decimalPointPos = valueStr.length - 1
        }

        check(display.decimalPointPos < NUM_MANTISSA_DIGITS)

        if (valueStr.length < NUM_MANTISSA_DIGITS) {
            val lenBefore = valueStr.length
            valueStr = valueStr.padStart(NUM_MANTISSA_DIGITS, ' ')
            display.decimalPointPos += (valueStr.length - lenBefore)
        }

        check(display.decimalPointPos < NUM_MANTISSA_DIGITS)

        if (valueStr.length > NUM_MANTISSA_DIGITS)
            valueStr = valueStr.substring(0, NUM_MANTISSA_DIGITS)

        valueStr.toCharArray().copyInto(display.mantissa)
        display.exponent.fill(' ')
    }

    /**
     * Prints "Error" to the display, with leading and trailing spaces to fill the mantissa.
     */
    fun printError() {
        "    Error  ".toCharArray().copyInto(display.mantissa)
        display.decimalPointPos = -1
        display.exponent.fill(' ')
    }

    /**
     *  Prints "nAn" to the display, with leading and trailing spaces to fill the mantissa.
     */
    private fun printNan() {
        "    nAn    ".toCharArray().copyInto(display.mantissa)
        display.decimalPointPos = -1
        display.exponent.fill(' ')
    }

    /**
     *  Prints "InF" or "-InF" to the display, with leading and trailing spaces to fill the mantissa.
     */
    private fun printInf(isPositive: Boolean) {
        val infStr = if (isPositive) "    InF    " else "   -InF    "
        infStr.toCharArray().copyInto(display.mantissa)

        display.decimalPointPos = -1
        display.exponent.fill(' ')
    }

    /**
     * Sets the number format for displaying values.
     * The TI-36 supports FLOAT, SCIENTIFIC, ENGINEERING, OCTAL, HEXADECIMAL, and BINARY formats.
     */
    fun setNumberFormat(numberFormat: DisplayNumberFormat) {
        displayNumberFormat = numberFormat
    }

}
