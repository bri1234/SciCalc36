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

import com.bri1234.ti36calculator.enums.CalculatorNumberMode
import com.bri1234.ti36calculator.enums.DisplayNumberFormat
import com.bri1234.ti36calculator.enums.Presentation
import com.bri1234.ti36calculator.enums.RectangularPolarView
import com.bri1234.ti36calculator.utils.ObserverSubject
import java.util.Locale
import kotlin.math.floor
import kotlin.math.abs
import kotlin.math.log10
import kotlin.math.pow

const val NUM_MANTISSA_DIGITS = 11
const val NUM_EXPONENT_DIGITS = 3

private const val NUM_HEX_BITS = 40
private const val NUM_OCT_BITS = 30
private const val NUM_BIN_BITS = 10

/**
 * Class representing the numeric display of the TI-36 calculator. It manages the mantissa, exponent,
 * and decimal point position, and provides methods to print values in various formats.
 */
class CalculatorNumericDisplay(val state: CalculatorState)  {

    /** An observer subject that is triggered whenever a value is printed to the display.
     * */
    val onPrint: ObserverSubject<Unit> = ObserverSubject()

    val displayMantissa = CharArray(NUM_MANTISSA_DIGITS) { ' ' }
    var displayDecimalPointPos: Int = -1
    val displayExponent = CharArray(NUM_EXPONENT_DIGITS) { ' ' }

    /** Number of digits after decimal point if fix point notation is used.
     * If negative, it means that the number format is not fixed point.
     */
    var numberOfDigitsAfterDecimalPoint: Int = -1

    /** The current number format for displaying values. The default is FLOAT, which is the
     * standard format for the TI-36.
     */
    private var displayNumberFormat: DisplayNumberFormat = DisplayNumberFormat.FLOAT

    /**
     * Resets the display to its default state, clearing all labels and setting the numeric value to 0.
     * The default label is DEG for angle unit.
     */
    fun reset() {
        displayMantissa.fill(' ')
        displayDecimalPointPos = -1
        displayExponent.fill(' ')

        displayNumberFormat = DisplayNumberFormat.FLOAT
        numberOfDigitsAfterDecimalPoint = -1

    }

    /**
     * Sets the display to show all segments lit up, which is typically used for testing or demonstration purposes.
     * This method fills the mantissa with '8's, sets the decimal point position to 1, and fills the exponent with '8's.
     */
//    fun displayViewAllSegments() {
//        "-8888888888".toCharArray().copyInto(displayMantissa)
//        displayDecimalPointPos = 1
//        "-88".toCharArray().copyInto(displayExponent)
//    }

    /**
     * Converts the current display value (mantissa and exponent) into a numeric double value.
     * @return The numeric value represented by the current display state.
     */
    fun convertDisplayValueToNumeric(): CalculatorValue {
        val v = when (state.calculatorNumberMode) {
            CalculatorNumberMode.HEXADECIMAL -> parseIntegerInput(16, NUM_HEX_BITS)
            CalculatorNumberMode.OCTAL -> parseIntegerInput(8, NUM_OCT_BITS)
            CalculatorNumberMode.BINARY -> parseIntegerInput(2, NUM_BIN_BITS)
            CalculatorNumberMode.DECIMAL -> parseDecimalInput()
        }

        // TODO: parse fraction display value

        return CalculatorValue(v)
    }

    private fun parseDecimalInput(): Double {
        var mantissaStr = displayMantissa.concatToString()
        val exponentStr = displayExponent.concatToString()

        if (displayDecimalPointPos != -1) {
            val pos = displayDecimalPointPos + 1
            mantissaStr = mantissaStr.replaceRange(pos, pos, ".")
        }

        val mantissa = mantissaStr.trim().toDoubleOrNull() ?: 0.0
        val exponent = exponentStr.trim().toIntOrNull() ?: 0

        return mantissa * 10.0.pow(exponent.toDouble())
    }

    private fun parseIntegerInput(radix: Int, numBits: Int): Double {
        val inputStr = displayMantissa.concatToString().trim()
        val value = inputStr.toLongOrNull(radix)
            ?: throw IllegalArgumentException("Invalid input for radix $radix: $inputStr")
        val maxVal = 1L shl numBits
        val signBit = 1L shl (numBits - 1)
        val signedValue = if ((value and signBit) != 0L) value - maxVal else value

        return signedValue.toDouble()
    }

    /**
     * Shows a numeric value in degrees in degrees, minutes, and seconds (DMS) format,
     * @param valueDegrees The numeric value in decimal degrees to be displayed in DMS format.
     */
    fun viewValueAsDegreesMinutesSeconds(valueDegrees: Double) {
        var value = abs(valueDegrees)
        val degrees = floor(value).toInt()
        value -= degrees
        value *= 60
        val minutes = floor(value).toInt()
        value -= minutes
        value *= 60
        val seconds = value
        val sign = if (valueDegrees < 0) "-" else " "

        var dmsString = String.format(Locale.ROOT, "%s%d°%02d'",sign, degrees, minutes)
        val secondsDigitCount = NUM_MANTISSA_DIGITS - dmsString.length
        val secondsFmt = when (secondsDigitCount) {
            5 -> "%05.2f"
            4 -> "%04.1f"
            else -> "%02.0f"
        }

        val secondsStr = String.format(Locale.ROOT, secondsFmt, seconds)

        if (secondsStr.contains('.'))
            dmsString += secondsStr.replace('.', '"')
        else
            dmsString += secondsStr + '"'

        if (dmsString.length > NUM_MANTISSA_DIGITS)
            dmsString = dmsString.substring(0, NUM_MANTISSA_DIGITS)

        dmsString.toCharArray().copyInto(displayMantissa)
        displayDecimalPointPos = -1
        displayExponent.fill(' ')
    }

    /**
     * Prints a double value to the display, formatted according to the current number format setting.
     * Handles special cases for NaN and Infinity.
     * @param value The double value to print on the display.
     */
    fun printValue(value: CalculatorValue) {

        if (value.isFraction && printFraction(value)) {
            onPrint(Unit)
            return
        }

        val doubleVal = value.getDouble()

        if (doubleVal.isNaN()) {
            printNan()
            onPrint(Unit)
            return
        }

        if (doubleVal.isInfinite()) {
            printInf(doubleVal > 0)
            onPrint(Unit)
            return
        }

        if ((abs(doubleVal) >= 1e100) || ((abs(doubleVal) <= 1e-100) && (doubleVal != 0.0)))
            throw IllegalArgumentException("Value is out of range for display")

        when (state.calculatorNumberMode) {
            CalculatorNumberMode.HEXADECIMAL -> printValueHex(doubleVal)
            CalculatorNumberMode.OCTAL -> printValueOct(doubleVal)
            CalculatorNumberMode.BINARY -> printValueBin(doubleVal)
            else -> {
                when (displayNumberFormat) {
                    DisplayNumberFormat.FLOAT -> printValueFloat(doubleVal)
                    DisplayNumberFormat.SCIENTIFIC -> printValueScientific(doubleVal)
                    DisplayNumberFormat.ENGINEERING -> printValueEngineering(doubleVal)
                }
            }
        }

        onPrint(Unit)
    }

    /**
     * Prints [value] as a mixed fraction when it fits into the fraction display.
     *
     * @return `true` when the fraction was printed, or `false` when decimal fallback is required.
     */
    private fun printFraction(value: CalculatorValue): Boolean {
        if (!value.isFraction)
            return false

        val fraction = value.getFraction()
        val fractionString = when (value.presentation) {
            Presentation.FRACTION_MIXED -> {
                val parts = fraction.toMixedFractionParts()

                when {
                    parts.numerator == 0 -> parts.wholePart.toString()
                    parts.wholePart == 0 -> "${parts.numerator};${parts.denominator}"
                    else -> "${parts.wholePart}_${parts.numerator};${parts.denominator}"
                }
            }

            Presentation.FRACTION_IMPROPER -> {
                if (fraction.isInteger) {
                    fraction.numerator.toString()
                } else {
                    "${fraction.numerator};${fraction.denominator}"
                }
            }

            Presentation.DECIMAL -> return false
        }

        if (fractionString.length > NUM_MANTISSA_DIGITS)
            return false

        fractionString.padStart(NUM_MANTISSA_DIGITS, ' ')
            .toCharArray()
            .copyInto(displayMantissa)
        displayDecimalPointPos = -1
        displayExponent.fill(' ')

        return true
    }

    /** Prints a double value in binary format with radix 2 and 10 bits for the integer part.
     * The value is treated as a signed integer, so it can represent values from -512 to 511. */
    private fun printValueBin(value: Double) {
        printInteger(value, 2, NUM_BIN_BITS)
    }

    /** Prints a double value in octal format with radix 8 and 30 bits for the integer part.
     * The value is treated as a signed integer, so it can represent values from -2^29 to 2^29 - 1. */
    private fun printValueOct(value: Double) {
        printInteger(value, 8, NUM_OCT_BITS)
    }

    /** Prints a double value in octal format with radix 16 and 40 bits for the integer part.
     * The value is treated as a signed integer, so it can represent values from -2^39 to 2^39 - 1. */
    private fun printValueHex(value: Double) {
        printInteger(value, 16, NUM_HEX_BITS)
    }

    /**
     * Prints a double value as an integer in the specified radix (base) and number of bits.
     * The value is treated as a signed integer, so it can represent values from -2^(numBits-1) to 2^(numBits-1) - 1.
     */
    private fun printInteger(value : Double, radix : Int, numBits : Int) {
        val maxVal = 1L shl numBits
        var valueInt = value.toLong()

        if ((value > (maxVal / 2 - 1)) || (value < -(maxVal / 2)))
            throw IllegalArgumentException("Value is too large to display as hexadecimal in radix $radix with $numBits bits")

        if (valueInt < 0)
            valueInt += maxVal

        check(valueInt >= 0)

        var valueStr = valueInt.toString(radix)
            .uppercase(Locale.ROOT)
            .replace('B', 'b')
            .replace('D', 'd')
        if (valueStr.length >= NUM_MANTISSA_DIGITS)
            throw IllegalArgumentException("Value is too large to display as integer")

        // right align mantissa
        valueStr = valueStr.padStart(NUM_MANTISSA_DIGITS, ' ')

        // copy the mantissa and exponent characters to the display
        valueStr.toCharArray().copyInto(displayMantissa)

        displayExponent.fill(' ')
        displayDecimalPointPos = -1
    }

    /**
     * Gets the exponent of a double value, which is the integer part of the base-10 logarithm of the absolute value.
     */
    private fun getExponent(value: Double): Int {
        return if (value == 0.0) 0 else floor(log10(abs(value))).toInt()
    }

    /**
     * Prints the mantissa of a double value to the display, formatted according to the current number format setting.
     */
    private fun printMantissa(mantissa : Double) {
        val mantissaExponent = getExponent(mantissa)
        val numDigitsBeforeDecimal = if (mantissaExponent >= 0) mantissaExponent + 1 else 1
        var numDigitsAfterDecimal = (NUM_MANTISSA_DIGITS - 1) - numDigitsBeforeDecimal

        if ((numberOfDigitsAfterDecimalPoint >= 0) && (numDigitsAfterDecimal > numberOfDigitsAfterDecimalPoint))
            numDigitsAfterDecimal = numberOfDigitsAfterDecimalPoint

        val formatStr = "% .${numDigitsAfterDecimal}f"
        var valueStr = String.format(Locale.ROOT, formatStr, mantissa)

        check(valueStr[0] == '-' || valueStr[0] == ' ')

        // find decimal point
        val decimalPointPos = valueStr.indexOf('.')
        if (decimalPointPos != -1) {
            check(valueStr.length <= NUM_MANTISSA_DIGITS + 1)

            if (numberOfDigitsAfterDecimalPoint < 0) {
                // remove trailing zeros after the decimal point
                var idx = valueStr.length - 1
                while (idx > decimalPointPos && valueStr[idx] == '0')
                    idx--

                valueStr = valueStr.removeRange(idx + 1, valueStr.length)
            }

            // remove the decimal point and record its position
            displayDecimalPointPos = decimalPointPos - 1
            valueStr = valueStr.removeRange(decimalPointPos, decimalPointPos + 1)
        } else {
            check(valueStr.length <= NUM_MANTISSA_DIGITS)

            displayDecimalPointPos = valueStr.length - 1
        }

        check(displayDecimalPointPos < NUM_MANTISSA_DIGITS)

        // right align mantissa
        if (valueStr.length < NUM_MANTISSA_DIGITS) {
            val lenBefore = valueStr.length
            valueStr = valueStr.padStart(NUM_MANTISSA_DIGITS, ' ')
            displayDecimalPointPos += (valueStr.length - lenBefore)
        }

        check(displayDecimalPointPos < NUM_MANTISSA_DIGITS)
        check(valueStr.length == NUM_MANTISSA_DIGITS)

        // copy the mantissa and exponent characters to the display
        valueStr.toCharArray().copyInto(displayMantissa)
    }

    /**
     * Prints the exponent of a double value to the display, formatted as a signed two-digit integer with leading zeros if necessary.
     */
    private fun printExponent(exponent: Int) {
        var exponentStr = String.format(Locale.ROOT, "%02d", abs(exponent))
        exponentStr = if (exponent < 0) "-$exponentStr" else " $exponentStr"

        check(exponentStr.length == NUM_EXPONENT_DIGITS)
        exponentStr.toCharArray().copyInto(displayExponent)
    }

    /** Prints a double value in float format, which is the default format for the TI-36.
     * It uses fixed-point notation for values that are not too large or too small, and switches to
     * scientific notation for values that are outside the range of 1E-7 to 1E10. */
    private fun printValueFloat(value: Double) {
        if ((abs(value) >= 1E10) || (value != 0.0 && abs(value) < 1E-7)) {
            printValueScientific(value)
            return
        }

        printMantissa(value)
        displayExponent.fill(' ')
    }

    /** Prints a double value in scientific notation, which is in the form of mantissa x 10^exponent.
     * For example, 12345 would be printed as 1.2345 x 10^4, and 0.00123 would be printed as 1.23 x 10^-3.
     */
    private fun printValueScientific(value: Double) {

        val exponent = getExponent(value)
        val mantissa =  value / 10.0.pow(exponent.toDouble())

        printMantissa(mantissa)
        printExponent(exponent)
    }

    /** Prints a double value in engineering notation, which is similar to scientific notation but the
     * exponent is always a multiple of 3.
     * For example, 12345 would be printed as 12.345 x 10^3, and 0.00123 would be printed as 1.23 x 10^-3.
     */
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
        "    Error  ".toCharArray().copyInto(displayMantissa)
        displayDecimalPointPos = -1
        displayExponent.fill(' ')
    }

    /**
     *  Prints "nAn" to the display, with leading and trailing spaces to fill the mantissa.
     */
    private fun printNan() {
        "    nAn    ".toCharArray().copyInto(displayMantissa)
        displayDecimalPointPos = -1
        displayExponent.fill(' ')
    }

    /**
     *  Prints "InF" or "-InF" to the display, with leading and trailing spaces to fill the mantissa.
     */
    private fun printInf(isPositive: Boolean) {
        val infStr = if (isPositive) "    InF    " else "   -InF    "
        infStr.toCharArray().copyInto(displayMantissa)

        displayDecimalPointPos = -1
        displayExponent.fill(' ')
    }

    /**
     * Sets the number format for displaying values.
     * The TI-36 supports FLOAT, SCIENTIFIC, ENGINEERING, OCTAL, HEXADECIMAL, and BINARY formats.
     */
    fun setNumberFormat(numberFormat: DisplayNumberFormat) {
        displayNumberFormat = numberFormat
    }

}
