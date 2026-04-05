package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.utils.ObserverSubject
import java.util.Locale
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

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
            DisplayNumberFormat.DECIMAL -> printValueFloat(value)
            DisplayNumberFormat.SCIENTIFIC -> printValueScientific(value)
            DisplayNumberFormat.ENGINEERING -> printValueEngineering(value)
            DisplayNumberFormat.OCTAL -> printValueOct(value)
            DisplayNumberFormat.HEXADECIMAL -> printValueHex(value)
            DisplayNumberFormat.BINARY -> printValueBin(value)
            DisplayNumberFormat.FIX -> printValueFix(value)
        }

        onPrint(Unit)
    }

    private fun printValueFix(value: Double) {
        display.mantissa.fill('-')
        display.exponent.fill(' ')
        display.decimalPointPos = -1
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

    private fun getExponent(value: Double): Int {
        return if (value == 0.0) 0 else floor(log10(abs(value))).toInt()
    }

    private fun printMantissa(mantissa : Double) {
        val mantissaExponent = getExponent(mantissa)
        val numDigitsBeforeDecimal = if (mantissaExponent >= 0) mantissaExponent + 1 else 1
        val numDigitsAfterDecimal = (NUM_MANTISSA_DIGITS - 1) - numDigitsBeforeDecimal
        val formatStr = "% .${numDigitsAfterDecimal}f"
        var valueStr = String.format(Locale.ROOT, formatStr, mantissa)

        check(valueStr[0] == '-' || valueStr[0] == ' ')

        // find decimal point
        val decimalPointPos = valueStr.indexOf('.')
        if (decimalPointPos != -1) {
            check(valueStr.length <= NUM_MANTISSA_DIGITS + 1)

            // remove trailing zeros after the decimal point
            var idx = valueStr.length - 1
            while (idx > decimalPointPos && valueStr[idx] == '0')
                idx--

            valueStr = valueStr.removeRange(idx + 1, valueStr.length)

            display.decimalPointPos = decimalPointPos - 1
            valueStr = valueStr.removeRange(decimalPointPos, decimalPointPos + 1)
        } else {
            check(valueStr.length <= NUM_MANTISSA_DIGITS)

            display.decimalPointPos = valueStr.length - 1
        }

        check(display.decimalPointPos < NUM_MANTISSA_DIGITS)

        // right align mantissa
        if (valueStr.length < NUM_MANTISSA_DIGITS) {
            val lenBefore = valueStr.length
            valueStr = valueStr.padStart(NUM_MANTISSA_DIGITS, ' ')
            display.decimalPointPos += (valueStr.length - lenBefore)
        }

        check(display.decimalPointPos < NUM_MANTISSA_DIGITS)
        check(valueStr.length == NUM_MANTISSA_DIGITS)

        // copy the mantissa and exponent characters to the display
        valueStr.toCharArray().copyInto(display.mantissa)
    }

    private fun printExponent(exponent: Int) {
        var exponentStr = String.format(Locale.ROOT, "%02d", abs(exponent))
        exponentStr = if (exponent < 0) "-$exponentStr" else " $exponentStr"

        check(exponentStr.length == NUM_EXPONENT_DIGITS)
        exponentStr.toCharArray().copyInto(display.exponent)
    }

    private fun printValueFloat(value: Double) {
        if ((abs(value) >= 1E10) || (value != 0.0 && abs(value) < 1E-7)) {
            printValueScientific(value)
            return
        }

        printMantissa(value)
        display.exponent.fill(' ')
    }

    private fun printValueScientific(value: Double) {

        val exponent = getExponent(value)
        val mantissa =  value / 10.0.pow(exponent.toDouble())

        printMantissa(mantissa)
        printExponent(exponent)
    }

    private fun printValueEngineering(value: Double) {

        var exponent = getExponent(value)

        if (exponent < 0) {
            val e = abs(exponent) % 3
            exponent -= if (e == 0) 0 else (3 - e)
        } else {
            exponent -= exponent % 3
        }

        val mantissa =  value / 10.0.pow(exponent.toDouble())

        printMantissa(mantissa)
        printExponent(exponent)
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
