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
import com.bri1234.ti36calculator.utils.ObserverSubject
import java.util.Locale

private enum class DigitInputMode {
    MANTISSA,
    EXPONENT,
    FREQUENCY,
    FRACTION,
    FRACTION_MIXED
}

class CalculatorInput(val state: CalculatorState,
                      val display: CalculatorNumericDisplay) {

    val onEditInputChanged: ObserverSubject<Unit> = ObserverSubject()
    val onEditModeBegin: ObserverSubject<Unit> = ObserverSubject()
    val onEditModeEnd: ObserverSubject<Unit> = ObserverSubject()

    var isEditMode: Boolean = false
        private set

    private var digitInputMode: DigitInputMode = DigitInputMode.MANTISSA
    private var inputPositionMantissa: Int = 0

    /**
     * Resets the input state, exiting edit mode and clearing any temporary input.
     */
    fun reset() {
        endEditMode()
    }

    /**
     * Enters the edit mode for the mantissa, allowing the user to input digits and a decimal point.
     * The exponent is not editable in this mode.
     */
    private fun beginEditMode() {
        isEditMode = true
        digitInputMode = DigitInputMode.MANTISSA

        inputPositionMantissa = 10

        "          0".toCharArray().copyInto(display.displayMantissa)
        display.displayDecimalPointPos = -1
        display.displayExponent.fill(' ')

        onEditModeBegin(Unit)
    }

    /**
     * Exits the edit mode, resetting all related flags and positions.
     */
    fun endEditMode() {
        isEditMode = false
        digitInputMode = DigitInputMode.MANTISSA
        inputPositionMantissa = -1

        onEditModeEnd(Unit)
    }

    /** Returns whether a simple or mixed fraction is currently being entered. */
    fun isFractionEditMode(): Boolean =
        isEditMode && (digitInputMode == DigitInputMode.FRACTION ||
                digitInputMode == DigitInputMode.FRACTION_MIXED)

    /**
     * Inputs a digit into the mantissa or exponent, depending on the current edit mode.
     * @param digit The digit to input (0-9).
     */
    fun inputDigit(digit : Int) {
        when (state.calculatorNumberMode) {
            CalculatorNumberMode.DECIMAL -> if (digit !in 0..9) return
            CalculatorNumberMode.HEXADECIMAL -> if (digit !in 0..15) return
            CalculatorNumberMode.OCTAL -> if (digit !in 0..7) return
            CalculatorNumberMode.BINARY -> if (digit !in 0..1) return
        }

        val digitCh = digit.toString(16).uppercase(Locale.ROOT)[0]

        if (!isEditMode)
            beginEditMode()

        val inputHasChanged = when (digitInputMode) {
            DigitInputMode.MANTISSA -> digitInputModeMantissa(digitCh)
            DigitInputMode.EXPONENT -> digitInputModeExponent(digitCh)
            DigitInputMode.FREQUENCY -> digitInputModeFrequency(digitCh)
            DigitInputMode.FRACTION -> digitInputModeFraction(digitCh)
            DigitInputMode.FRACTION_MIXED -> digitInputModeFractionMixed(digitCh)
        }

        if (inputHasChanged) {
            onEditInputChanged(Unit)
        }
    }

    /**
     * Fraction key pressed.
     */
    fun inputFractionKey() {
        when (state.calculatorNumberMode) {
            CalculatorNumberMode.HEXADECIMAL,
            CalculatorNumberMode.OCTAL,
            CalculatorNumberMode.BINARY -> { return }
            else -> { }
        }

        if (!isEditMode)
            return

        when (digitInputMode) {
            DigitInputMode.EXPONENT,
            DigitInputMode.FREQUENCY -> { return }
            DigitInputMode.MANTISSA -> {
                if (display.displayDecimalPointPos != -1)
                    return

                if (!digitInputModeMantissa(';'))
                    return

                digitInputMode = DigitInputMode.FRACTION
            }
            DigitInputMode.FRACTION -> {
                val separatorIndex = display.displayMantissa.indexOf(';')
                if (separatorIndex == -1 ||
                    (separatorIndex + 1..<NUM_MANTISSA_DIGITS)
                        .none { display.displayMantissa[it].isDigit() } ||
                    inputPositionMantissa < 1) {
                    return
                }

                display.displayMantissa[separatorIndex] = '_'
                check(digitInputModeMantissa(';'))
                digitInputMode = DigitInputMode.FRACTION_MIXED
            }

            DigitInputMode.FRACTION_MIXED -> { return }
        }
    }

    /**
     * Inputs a digit into the mantissa, shifting all existing digits to the left.
     * This method maintains the position of the minus sign if present and adjusts the
     * decimal point position accordingly.
     *
     * @param digitCh The digit character to input.
     * @return `true` if the digit was successfully added, `false` if no more space is available (inputPositionMantissa < 1).
     */
    private fun digitInputModeMantissa(digitCh: Char): Boolean {
        if (inputPositionMantissa < 1)
            return false

        val hasMinus = display.displayMantissa[inputPositionMantissa] == '-'

        for (idx in inputPositionMantissa..<NUM_MANTISSA_DIGITS - 1) {
            display.displayMantissa[idx] = display.displayMantissa[idx + 1]
        }

        display.displayMantissa[NUM_MANTISSA_DIGITS - 1] = digitCh

        inputPositionMantissa--

        if (hasMinus)
            display.displayMantissa[inputPositionMantissa] = '-'

        if (display.displayDecimalPointPos != -1)
            display.displayDecimalPointPos--

        return true
    }

    /**
     * Inputs a digit into the exponent, shifting existing digits to the left.
     * Only decimal digits (0-9) are allowed.
     *
     * @param digitCh The digit character to input.
     * @return `true` if the digit was successfully added.
     * @throws IllegalArgumentException if the input is not a decimal digit.
     */
    private fun digitInputModeExponent(digitCh: Char) : Boolean {
        require(digitCh.isDigit()) { "Invalid digit for exponent input: $digitCh" }

        display.displayExponent[1] = display.displayExponent[2]
        display.displayExponent[2] = digitCh

        return true
    }

    /**
     * Inputs a digit into the frequency display, shifting the previous digits to the left.
     * The frequency display is used for statistic mode 1 and 2.
     * @param digitCh The digit character to input (0-9).
     */
    private fun digitInputModeFrequency(digitCh: Char) : Boolean {
        require(digitCh.isDigit()) { "Invalid digit for frequency input: $digitCh" }

        val m = display.displayMantissa
        val o = NUM_MANTISSA_DIGITS - 5
        require(m[o] == 'F' && m[o + 1] == 'r' && m[o + 2] == ' ' && m[o + 3].isDigit() && m[o + 4].isDigit())
            { "Frequency input can only be used in statistic mode 1 and 2" }

        display.displayMantissa[o + 3] = display.displayMantissa[o + 4]
        display.displayMantissa[o + 4] = digitCh

        return true
    }

    /**
     * Inputs a digit in fraction input mode.
     * @param digitCh The digit character to input (0-9).
     */
    private fun digitInputModeFraction(digitCh: Char) : Boolean {
        require(digitCh.isDigit()) { "Invalid digit for fraction input: $digitCh" }

        if (display.displayMantissa.count { it.isDigit() } >= 8)
            return false

        return digitInputModeMantissa(digitCh)
    }

    /**
     * Inputs a digit in fraction mixed input mode: 5_7;9 (fraction with whole part, nominator, and denominator).
     * @param digitCh The digit character to input (0-9).
     */
    private fun digitInputModeFractionMixed(digitCh: Char) : Boolean {
        require(digitCh.isDigit()) { "Invalid digit for mixed fraction input: $digitCh" }

        if (display.displayMantissa.count { it.isDigit() } >= 8)
            return false

        return digitInputModeMantissa(digitCh)
    }

    /**
     * Returns the entered statistic frequency, or 1 when frequency input is not active.
     *
     * @return The entered two-digit statistic frequency, or 1 outside frequency input mode.
     */
    fun getFrequency(): Int {
        if (digitInputMode != DigitInputMode.FREQUENCY)
            return 1

        val m = display.displayMantissa
        val o = NUM_MANTISSA_DIGITS - 5
        require(m[o] == 'F' && m[o + 1] == 'r' && m[o + 2] == ' ' && m[o + 3].isDigit() && m[o + 4].isDigit())
            { "Frequency input can only be used in statistic mode 1 and 2" }

        return "${m[o + 3]}${m[o + 4]}".toInt()
    }

    /**
     * Returns whether statistic frequency input is currently active.
     *
     * @return `true` while frequency input is active, otherwise `false`.
     */
    fun isFrequencyEditMode(): Boolean {
        return digitInputMode == DigitInputMode.FREQUENCY
    }

    /**
     * Inputs a decimal point into the mantissa, if not already present.
     */
    fun inputDecimalPoint() {
        if (state.calculatorNumberMode != CalculatorNumberMode.DECIMAL)
            return

        if (!isEditMode)
            beginEditMode()

        if ((digitInputMode != DigitInputMode.MANTISSA) || (display.displayDecimalPointPos != -1))
            return

        if (inputPositionMantissa == NUM_MANTISSA_DIGITS - 1)
            inputDigit(0)

        display.displayDecimalPointPos = NUM_MANTISSA_DIGITS - 1

        onEditInputChanged(Unit)
    }

    /**
     * Toggles the sign of the mantissa or exponent, depending on the current edit mode.
     */
    fun inputPlusMinus() {
        if (!isEditMode)
            return

        when (digitInputMode) {
            DigitInputMode.MANTISSA,
            DigitInputMode.FRACTION,
            DigitInputMode.FRACTION_MIXED -> {
                when (display.displayMantissa[inputPositionMantissa]) {
                    ' ' -> display.displayMantissa[inputPositionMantissa] = '-'
                    '-' -> display.displayMantissa[inputPositionMantissa] = ' '
                    else -> error("Invalid state: plus/minus can only be toggled on a space or a minus sign")
                }
            }
            DigitInputMode.EXPONENT -> {
                when (display.displayExponent[0]) {
                    ' ' -> display.displayExponent[0] = '-'
                    '-' -> display.displayExponent[0] = ' '
                    else -> error("Invalid state: plus/minus can only be toggled on a space or a minus sign")
                }
            }
            else -> {
                throw IllegalStateException("Plus/minus input is not supported in frequency input mode")
            }
        }

        if ((digitInputMode == DigitInputMode.FRACTION ||
                    digitInputMode == DigitInputMode.FRACTION_MIXED) &&
            display.displayMantissa.last() == ';') {
            return
        }

        onEditInputChanged(Unit)
    }

    /**
     * Removes the last entered digit from the mantissa, shifting the remaining digits to the right.
     */
    fun inputBack() {
        if (!isEditMode)
            return

        if (digitInputMode == DigitInputMode.FRACTION ||
            digitInputMode == DigitInputMode.FRACTION_MIXED) {
            inputBackFraction()
            return
        }

        if (digitInputMode != DigitInputMode.MANTISSA)
            return

        if (inputPositionMantissa >= NUM_MANTISSA_DIGITS - 1)
            return

        if (inputPositionMantissa == NUM_MANTISSA_DIGITS - 2) {
            // is last digit
            if (display.displayDecimalPointPos == NUM_MANTISSA_DIGITS - 1) {
                display.displayDecimalPointPos = -1
                return
            }

            // set last digit to 0
            display.displayMantissa[NUM_MANTISSA_DIGITS - 1] = '0'
            inputPositionMantissa = NUM_MANTISSA_DIGITS - 1
            onEditInputChanged(Unit)
            return
        }

        val hasMinus = display.displayMantissa[inputPositionMantissa] == '-'
        if (hasMinus)
            display.displayMantissa[inputPositionMantissa] = ' '

        for (idx in NUM_MANTISSA_DIGITS - 1 downTo inputPositionMantissa + 1) {
            display.displayMantissa[idx] = display.displayMantissa[idx - 1]
        }

        inputPositionMantissa++
        display.displayMantissa[inputPositionMantissa] = ' '

        if (display.displayDecimalPointPos != -1)
            display.displayDecimalPointPos++

        if (hasMinus)
            display.displayMantissa[inputPositionMantissa] = '-'

        onEditInputChanged(Unit)
    }

    /** Removes the last character from a simple or mixed fraction input. */
    private fun inputBackFraction() {
        if (inputPositionMantissa >= NUM_MANTISSA_DIGITS - 1)
            return

        val wasMixedInput = digitInputMode == DigitInputMode.FRACTION_MIXED
        val removesSeparator = display.displayMantissa.last() == ';'

        if (wasMixedInput && removesSeparator) {
            val mixedSeparatorIndex = display.displayMantissa.indexOf('_')
            check(mixedSeparatorIndex != -1)
            display.displayMantissa[mixedSeparatorIndex] = ';'
        }

        val hasMinus = display.displayMantissa[inputPositionMantissa] == '-'
        if (hasMinus)
            display.displayMantissa[inputPositionMantissa] = ' '

        for (idx in NUM_MANTISSA_DIGITS - 1 downTo inputPositionMantissa + 1)
            display.displayMantissa[idx] = display.displayMantissa[idx - 1]

        inputPositionMantissa++
        display.displayMantissa[inputPositionMantissa] = ' '

        if (hasMinus)
            display.displayMantissa[inputPositionMantissa] = '-'

        if (removesSeparator) {
            digitInputMode = if (wasMixedInput) {
                DigitInputMode.FRACTION
            } else {
                DigitInputMode.MANTISSA
            }
        }

        val isIncompleteFraction =
            (digitInputMode == DigitInputMode.FRACTION ||
                    digitInputMode == DigitInputMode.FRACTION_MIXED) &&
                    display.displayMantissa.last() == ';'

        if (!isIncompleteFraction)
            onEditInputChanged(Unit)
    }

    /**
     * Enters the edit mode for the exponent, allowing the user to input digits and a sign.
     * The mantissa is not editable in this mode.
     */
    fun enterExponentEditMode() {
        if (!isEditMode || (digitInputMode != DigitInputMode.MANTISSA))
            return

        digitInputMode = DigitInputMode.EXPONENT
        " 00".toCharArray().copyInto(display.displayExponent)

        onEditInputChanged(Unit)
    }

    /**
     * Enters the edit mode for the frequency, allowing the user to input digits.
     * The mantissa and exponent are not editable in this mode.
     */
    fun enterFrequencyEditMode() {
        if (digitInputMode == DigitInputMode.FREQUENCY)
            return

        digitInputMode = DigitInputMode.FREQUENCY

        display.displayMantissa.fill(' ')
        "Fr 00".toCharArray().copyInto(display.displayMantissa, NUM_MANTISSA_DIGITS - 4 - 1)
        display.displayExponent.fill(' ')
        display.displayDecimalPointPos = -1

        onEditInputChanged(Unit)
    }
}
