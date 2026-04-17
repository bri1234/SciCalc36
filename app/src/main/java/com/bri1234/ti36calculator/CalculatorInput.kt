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

import com.bri1234.ti36calculator.utils.ObserverSubject

class CalculatorInput(val state: CalculatorState,
                      val display: CalculatorNumericDisplay) {

    val onEditInputChanged: ObserverSubject<Unit> = ObserverSubject()
    val onEditModeBegin: ObserverSubject<Unit> = ObserverSubject()
    val onEditModeEnd: ObserverSubject<Unit> = ObserverSubject()

    var isEditMode: Boolean = false
        private set

    private var isEditExponent: Boolean = false
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
        isEditExponent = false

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
        isEditExponent = false
        inputPositionMantissa = -1

        onEditModeEnd(Unit)
    }

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

        if (!isEditMode)
            beginEditMode()

        if (isEditExponent) {
            display.displayExponent[1] = display.displayExponent[2]
            display.displayExponent[2] = "$digit"[0]
        } else {
            if (inputPositionMantissa < 1)
                return

            val hasMinus = display.displayMantissa[inputPositionMantissa] == '-'

            for (idx in inputPositionMantissa ..< NUM_MANTISSA_DIGITS - 1) {
                display.displayMantissa[idx] = display.displayMantissa[idx + 1]
            }
            display.displayMantissa[NUM_MANTISSA_DIGITS - 1] = "$digit"[0]

            inputPositionMantissa--

            if (hasMinus)
                display.displayMantissa[inputPositionMantissa] = '-'

            if (display.displayDecimalPointPos != -1)
                display.displayDecimalPointPos--
        }

        onEditInputChanged(Unit)
    }

    /**
     * Inputs a decimal point into the mantissa, if not already present.
     */
    fun inputDecimalPoint() {
        if (state.calculatorNumberMode != CalculatorNumberMode.DECIMAL)
            return

        if (!isEditMode)
            beginEditMode()

        if (isEditExponent || (display.displayDecimalPointPos != -1))
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

        if (isEditExponent) {
            when (display.displayExponent[0]) {
                ' ' -> display.displayExponent[0] = '-'
                '-' -> display.displayExponent[0] = ' '
                else -> error("Invalid state: plus/minus can only be toggled on a space or a minus sign")
            }
        } else {
            when (display.displayMantissa[inputPositionMantissa]) {
                ' ' -> display.displayMantissa[inputPositionMantissa] = '-'
                '-' -> display.displayMantissa[inputPositionMantissa] = ' '
                else -> error("Invalid state: plus/minus can only be toggled on a space or a minus sign")
            }
        }

        onEditInputChanged(Unit)
    }

    /**
     * Removes the last entered digit from the mantissa, shifting the remaining digits to the right.
     */
    fun inputBack() {
        if (!isEditMode || isEditExponent)
            return

        if (inputPositionMantissa >= NUM_MANTISSA_DIGITS - 1)
            return

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

    /**
     * Enters the edit mode for the exponent, allowing the user to input digits and a sign.
     * The mantissa is not editable in this mode.
     */
    fun enterExponentEditMode() {
        if (!isEditMode || isEditExponent)
            return

        isEditExponent = true
        " 00".toCharArray().copyInto(display.displayExponent)

        onEditInputChanged(Unit)
    }

}
