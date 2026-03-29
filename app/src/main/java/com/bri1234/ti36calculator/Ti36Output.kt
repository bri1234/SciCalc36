package com.bri1234.ti36calculator

import kotlin.math.abs
import kotlin.math.floor
import com.bri1234.ti36calculator.utils.ObserverSubject
import java.util.Locale

enum class DisplayNumberFormat {
    FLOAT,
    SCIENTIFIC,
    ENGINEERING,
    FIX,
    DECIMAL,
    OCTAL,
    HEXADECIMAL,
    BINARY,
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

        if ((abs(value) >= 1e100) || ((abs(value) <= 1e-100) && (value != 0.0)))
            throw IllegalArgumentException("Value is out of range for display")

        when (displayNumberFormat) {
            DisplayNumberFormat.FLOAT,
            DisplayNumberFormat.SCIENTIFIC,
            DisplayNumberFormat.ENGINEERING,
            DisplayNumberFormat.FIX,
            DisplayNumberFormat.DECIMAL -> printValueFloat(value)
            DisplayNumberFormat.OCTAL -> printValueOct(value)
            DisplayNumberFormat.HEXADECIMAL -> printValueHex(value)
            DisplayNumberFormat.BINARY -> printValueBin(value)
        }

        onPrint(Unit)
    }

    private fun printValueBin(value: Double) {
        printInteger(value, 2, 10)
    }

    private fun printValueOct(value: Double) {
        printInteger(value, 8, 30)
    }

    private fun printValueHex(value: Double) {
        printInteger(value, 16, 40)
    }

    private fun printInteger(value : Double, radix : Int, numBits : Int) {
        val maxVal = 1L shl numBits
        var valueInt = value.toLong()

        if ((value > (maxVal / 2 - 1)) || (value < -(maxVal / 2)))
            throw IllegalArgumentException("Value is too large to display as hexadecimal in radix $radix with $numBits bits")

        if (valueInt < 0)
            valueInt += maxVal

        check(valueInt >= 0)

        var valueStr = valueInt.toString(radix).uppercase(Locale.ROOT)
        if (valueStr.length >= NUM_MANTISSA_DIGITS)
            throw IllegalArgumentException("Value is too large to display as integer")

        // right align mantissa
        valueStr = valueStr.padStart(NUM_MANTISSA_DIGITS, ' ')

        // copy the mantissa and exponent characters to the display
        valueStr.toCharArray().copyInto(display.mantissa)

        display.exponent.fill(' ')
        display.decimalPointPos = -1
    }

    private fun printValueFloat(value: Double) {
        // TODO: rounding error because of output string is longer than the number of
        //  digits in the mantissa.

        // convert the number to a string depending on the display format
        val formatStr = when (displayNumberFormat) {
            DisplayNumberFormat.FLOAT, DisplayNumberFormat.DECIMAL -> {
                if (abs(value) >= 1E10 || (value != 0.0 && abs(value) <= 1E-7))
                    "% .10e"
                else
                    "% .9f"
            }
            DisplayNumberFormat.SCIENTIFIC, DisplayNumberFormat.ENGINEERING -> "% .10e"
            DisplayNumberFormat.FIX -> "% .3f"
            else -> throw IllegalStateException("Invalid number format for float display")
        }

        var valueStr = String.format(Locale.ROOT, formatStr, value)

        // get exponent
        val exponentPos = valueStr.indexOf('e')
        var exponentValue = 0
        if (exponentPos != -1) {
            exponentValue = valueStr.substring(exponentPos + 1).toInt()
            valueStr = valueStr.removeRange(exponentPos, valueStr.length)
        }

        // find decimal point
        val decimalPointPos = valueStr.indexOf('.')
        if (decimalPointPos != -1) {
            // remove trailing zeros after the decimal point
            var idx = valueStr.length - 1
            while (idx > decimalPointPos && valueStr[idx] == '0')
                idx--

            valueStr = valueStr.removeRange(idx + 1, valueStr.length)

            display.decimalPointPos = decimalPointPos - 1
            valueStr = valueStr.removeRange(decimalPointPos, decimalPointPos + 1)
        } else {
            display.decimalPointPos = valueStr.length - 1
        }

        check(display.decimalPointPos < NUM_MANTISSA_DIGITS)

        // adjust exponent for engineering notation
        if (displayNumberFormat == DisplayNumberFormat.ENGINEERING) {
            if (exponentValue % 3 != 0) {
                val newExponentValue = floor(exponentValue / 3.0).toInt() * 3
                check(newExponentValue < exponentValue)

                display.decimalPointPos += (exponentValue - newExponentValue)
                while (valueStr.length <= display.decimalPointPos)
                    valueStr = valueStr + '0'

                exponentValue = newExponentValue
            }
        }

        check(display.decimalPointPos < NUM_MANTISSA_DIGITS)

        // right align mantissa
        if (valueStr.length < NUM_MANTISSA_DIGITS) {
            val lenBefore = valueStr.length
            valueStr = valueStr.padStart(NUM_MANTISSA_DIGITS, ' ')
            display.decimalPointPos += (valueStr.length - lenBefore)
        }

        check(display.decimalPointPos < NUM_MANTISSA_DIGITS)

        if (valueStr.length > NUM_MANTISSA_DIGITS)
            valueStr = valueStr.substring(0, NUM_MANTISSA_DIGITS)

        // copy the mantissa and exponent characters to the display
        valueStr.toCharArray().copyInto(display.mantissa)

        if (displayNumberFormat == DisplayNumberFormat.ENGINEERING
            || displayNumberFormat == DisplayNumberFormat.SCIENTIFIC
            || exponentValue != 0) {

            var exponentStr = String.format(Locale.ROOT, "%02d", abs(exponentValue))
            exponentStr = if (exponentValue < 0) "-$exponentStr" else " $exponentStr"

            check(exponentStr.length == NUM_EXPONENT_DIGITS)
            exponentStr.toCharArray().copyInto(display.exponent)
        }
        else {
            display.exponent.fill(' ')
        }
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
