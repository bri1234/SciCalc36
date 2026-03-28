package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.contracts.CalculatorStateInterface

const val NUM_MANTISSA_DIGITS = 11
const val NUM_EXPONENT_DIGITS = 3

class Ti36Display : CalculatorStateInterface {

    val mantissa = CharArray(NUM_MANTISSA_DIGITS) { ' ' }
    var decimalPointPos: Int = -1
    val exponent = CharArray(NUM_EXPONENT_DIGITS) { ' ' }

    private val labels: MutableSet<CalculatorState> = mutableSetOf()

    fun getDisplayState(): CalculatorDisplayData {
        // Return a dummy display state for now
        return CalculatorDisplayData(
            digitsLarge = mantissa.copyOf(),
            decimalPointIndex = decimalPointPos,
            digitsSmall = exponent.copyOf(),
            displayLabels = labels.toSet(),
        )
    }

    /**
     * Resets the display to its default state, clearing all labels and setting the numeric value to 0.
     * The default label is DEG for angle unit.
     */
    fun reset() {
        labels.clear()
        labels.add(CalculatorState.DEG)

        mantissa.fill(' ')
        decimalPointPos = -1
        exponent.fill(' ')
    }

    fun displayViewAllSegments() {
        "-8888888888".toCharArray().copyInto(mantissa)
        decimalPointPos = 1
        "-88".toCharArray().copyInto(exponent)
    }

    /**
     * Converts the current display value (mantissa and exponent) into a numeric double value.
     * @return The numeric value represented by the current display state.
     */
    fun convertDisplayValueToNumeric(): Double {
        var mantissaStr = mantissa.concatToString()
        val exponentStr = exponent.concatToString()

        if (decimalPointPos != -1) {
            val pos = decimalPointPos + 1
            mantissaStr = mantissaStr.replaceRange(pos, pos, ".")
        }

        val mantissa = mantissaStr.trim().toDoubleOrNull() ?: 0.0
        val exponent = exponentStr.trim().toIntOrNull() ?: 0

        return mantissa * Math.pow(10.0, exponent.toDouble())
    }

    /**
     * Checks if the second function is active.
     * @return true if the second function is active, false otherwise.
     */
    override fun isSecondFunctionActive() = CalculatorState.SECOND in labels

    /**
     * Checks if the third function is active.
     * @return true if the third function is active, false otherwise.
     */
    override fun isThirdFunctionActive() = CalculatorState.THIRD in labels

    /**
     * Checks if a specific label is active on the display.
     * @param state The label to check for.
     * @return true if the label is active, false otherwise.
     */
    override fun hasState(state: CalculatorState) = state in labels

    /**
     * Adds a label to the display.
     * @param state The label to add.
     */
    override fun addState(state: CalculatorState) {
        labels.add(state)
    }

    /**
     * Removes a label from the display.
     * @param state The label to remove.
     */
    override fun removeState(state: CalculatorState) {
        labels.remove(state)
    }

    override fun printNotImplemented() {
        "  not IPL  ".toCharArray().copyInto(mantissa)
        decimalPointPos = -1
        exponent.fill(' ')
    }
}

