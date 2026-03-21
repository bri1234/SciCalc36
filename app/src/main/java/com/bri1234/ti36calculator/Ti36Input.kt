package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.utils.ObserverSubject

class Ti36Input(val display: Ti36Display) {

    val onEditInputChanged: ObserverSubject<Unit> = ObserverSubject()

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

        "          0".toCharArray().copyInto(display.mantissa)
        display.decimalPointPos = -1
        display.exponent.fill(' ')
    }

    /**
     * Exits the edit mode, resetting all related flags and positions.
     */
    fun endEditMode() {
        isEditMode = false
        isEditExponent = false
        inputPositionMantissa = -1
    }

    /**
     * Inputs a digit into the mantissa or exponent, depending on the current edit mode.
     * @param digit The digit to input (0-9).
     */
    fun inputDigit(digit : Int) {
        require(digit in 0..9)

        if (!isEditMode)
            beginEditMode()

        if (isEditExponent) {
            display.exponent[1] = display.exponent[2]
            display.exponent[2] = "$digit"[0]
        } else {
            if (inputPositionMantissa < 1)
                return

            val hasMinus = display.mantissa[inputPositionMantissa] == '-'

            for (idx in inputPositionMantissa ..< NUM_MANTISSA_DIGITS - 1) {
                display.mantissa[idx] = display.mantissa[idx + 1]
            }
            display.mantissa[NUM_MANTISSA_DIGITS - 1] = "$digit"[0]

            inputPositionMantissa--

            if (hasMinus)
                display.mantissa[inputPositionMantissa] = '-'

            if (display.decimalPointPos != -1)
                display.decimalPointPos--
        }

        onEditInputChanged(Unit)
    }

    /**
     * Inputs a decimal point into the mantissa, if not already present.
     */
    fun inputDecimalPoint() {
        if (!isEditMode)
            beginEditMode()

        if (isEditExponent || (display.decimalPointPos != -1))
            return

        if (inputPositionMantissa == NUM_MANTISSA_DIGITS - 1)
            inputDigit(0)

        display.decimalPointPos = NUM_MANTISSA_DIGITS - 1

        onEditInputChanged(Unit)
    }

    /**
     * Toggles the sign of the mantissa or exponent, depending on the current edit mode.
     */
    fun inputPlusMinus() {
        if (!isEditMode)
            return

        if (isEditExponent) {
            when (display.exponent[0]) {
                ' ' -> display.exponent[0] = '-'
                '-' -> display.exponent[0] = ' '
                else -> error("Invalid state: plus/minus can only be toggled on a space or a minus sign")
            }
        } else {
            when (display.mantissa[inputPositionMantissa]) {
                ' ' -> display.mantissa[inputPositionMantissa] = '-'
                '-' -> display.mantissa[inputPositionMantissa] = ' '
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

        val hasMinus = display.mantissa[inputPositionMantissa] == '-'
        if (hasMinus)
            display.mantissa[inputPositionMantissa] = ' '

        for (idx in NUM_MANTISSA_DIGITS - 1 downTo inputPositionMantissa + 1) {
            display.mantissa[idx] = display.mantissa[idx - 1]
        }

        inputPositionMantissa++
        display.mantissa[inputPositionMantissa] = ' '

        if (display.decimalPointPos != -1)
            display.decimalPointPos++

        if (hasMinus)
            display.mantissa[inputPositionMantissa] = '-'

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
        " 00".toCharArray().copyInto(display.exponent)

        onEditInputChanged(Unit)
    }

}
