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

import android.util.Log

private enum class CalculatorInputState {
    NONE,
    CONSTANT,
    MEMORY,
    FIXED_NUMBER_FORMAT,
}

/*
Next features to implement:
- refactoring: separate display state from internal state, display state created from internal state (e.g. STAT, BIN, OCT, HEX ...)

- CE/C button (clear entry / clear)
- A/B/C button (Fractions)
- HEX, OCT, BIN number input
- STAT 1 & 2 mode

*/

/**
 * A simulator class for the TI-36 calculator.
 */
class Ti36Simulator {

    private val display = Ti36Display()
    private val input = Ti36Input(display)
    private val output = Ti36Output(display)
    private val computation = Ti36Computation()

    private val functions = Ti36Functions(display, computation)
    private val functions2 = Ti36Functions2(display, computation)

    private val memory = Ti36Memory(computation)

    private var isErrorState: Boolean = false

    private var currentInputState: CalculatorInputState = CalculatorInputState.NONE
    private var currentInputStateWasSet: Boolean = false
    private var currentMemoryOperation: MemoryOperation = MemoryOperation.NONE

    /**
     * Returns the current display state of the calculator.
     *
     * @return The current [CalculatorDisplayData].
     */
    fun getDisplayState(): CalculatorDisplayData {
        val state = display.getDisplayState()

        if (computation.hasParentheses())
            state.displayLabels.add(CalculatorState.PARENTHESES)
        else
            state.displayLabels.remove(CalculatorState.PARENTHESES)

        return state
    }

    // Sets up event listeners for input changes, edit mode, print events,
    // result changes, and memory content changes. Also resets the calculator to its initial state.
    init {
        buttonPressedAcOn()
        // viewAll()

        input.onEditInputChanged += { onEditInputChanged() }
        input.onEditModeBegin += { onEditModeBegin() }
        output.onPrint += { onPrint() }
        computation.onResultChanged += { value -> onResultChanged(value) }
        memory.onContentChanged += { hasContent -> onMemoryContentChanged(hasContent) }
    }

    /**
     * Resets the calculator to its initial state, clearing any error state, resetting input states,
     * and clearing all computations, display, input, output, and memory.
     */
    fun reset() {
        isErrorState = false

        currentInputState = CalculatorInputState.NONE
        currentInputStateWasSet = false
        currentMemoryOperation = MemoryOperation.NONE

        computation.reset()
        display.reset()
        input.reset()
        output.reset()
        memory.reset()
    }

    /**
     * Simulates the pressing of a calculator button. Depending on the current state of the calculator
     * and the button pressed, it will perform different actions such as entering constants,
     * performing memory operations, changing number formats or executing functions.
     * If an error occurs during the processing of the button press, it will set the error state
     * and display an error message.
     *
     * @param button The [CalculatorButton] that was pressed.
     */
    fun buttonPressed(button: CalculatorButton) {

        computation.printInfo()

        if (isErrorState) {
            if (button == CalculatorButton.AC_ON) {
                buttonPressedAcOn()
            }
            return
        }

        currentInputStateWasSet = false

        try {
            when (button) {
                CalculatorButton.AC_ON -> buttonPressedAcOn()
                CalculatorButton.SECOND -> buttonPressedSecond()
                CalculatorButton.THIRD -> buttonPressedThird()

                else -> {
                    when (currentInputState) {
                        CalculatorInputState.CONSTANT -> modeConstant(button)
                        CalculatorInputState.MEMORY -> modeMemory(button)
                        CalculatorInputState.FIXED_NUMBER_FORMAT -> modeFixedNumberFormat(button)

                        else -> {
                            when {
                                display.isSecondFunctionActive() -> modeSecondFunction(button)
                                display.isThirdFunctionActive() -> modeThirdFunction(button)
                                else -> modeFirstFunction(button)
                            }
                        }
                    }
                }

            }

            if (!currentInputStateWasSet) {
                currentInputState = CalculatorInputState.NONE
                currentMemoryOperation = MemoryOperation.NONE
            }

        } catch (e: Exception) {
            Log.e("Ti36Simulator", "Error during button press", e)
            isErrorState = true
            output.printError()
        }

        computation.printInfo()
    }

    /**
     * Handles the button presses for the first function of each button. Depending on the button pressed,
     * it will execute the corresponding function or operation. If a button does not have a defined action
     * in this mode, it will do nothing.
     *
     * @param button The [CalculatorButton] that was pressed.
     */
    private fun modeFirstFunction(button : CalculatorButton) {
        when (button) {
            CalculatorButton.HYP -> functions.hyp()
            CalculatorButton.LOG -> functions.log()
            CalculatorButton.LN -> functions.ln()
            CalculatorButton.CE_C -> buttonPressedCeC()
            CalculatorButton.SIN -> functions.sin()
            CalculatorButton.COS -> functions.cos()
            CalculatorButton.TAN -> functions.tan()
            CalculatorButton.Y_POW_X -> computation.yPowerX()
            CalculatorButton.X_SWAP_Y -> functions2.swap()
            CalculatorButton.ONE_DIV_X -> functions.oneDivX()
            CalculatorButton.X_SQUARED -> functions.xSquared()
            CalculatorButton.SQRT_X -> functions.squareRootX()
            CalculatorButton.DIVIDE -> computation.division()
            CalculatorButton.SUM_PLUS -> notImplemented()
            CalculatorButton.EE -> input.enterExponentEditMode()
            CalculatorButton.LEFT_PARENTHESES -> computation.leftParentheses()
            CalculatorButton.RIGHT_PARENTHESES -> computation.rightParentheses()
            CalculatorButton.MULTIPLY -> computation.multiplication()
            CalculatorButton.STORE -> buttonPressedMemory(MemoryOperation.STORE)
            CalculatorButton.SEVEN -> input.inputDigit(7)
            CalculatorButton.EIGHT -> input.inputDigit(8)
            CalculatorButton.NINE -> input.inputDigit(9)
            CalculatorButton.MINUS -> computation.subtraction()
            CalculatorButton.RECALL -> buttonPressedMemory(MemoryOperation.RECALL)
            CalculatorButton.FOUR -> input.inputDigit(4)
            CalculatorButton.FIVE -> input.inputDigit(5)
            CalculatorButton.SIX -> input.inputDigit(6)
            CalculatorButton.PLUS -> computation.addition()
            CalculatorButton.A_B_C -> notImplemented()
            CalculatorButton.ONE -> input.inputDigit(1)
            CalculatorButton.TWO -> input.inputDigit(2)
            CalculatorButton.THREE -> input.inputDigit(3)
            CalculatorButton.EQUAL -> computation.evaluateAll()
            CalculatorButton.BACK -> input.inputBack()
            CalculatorButton.ZERO -> input.inputDigit(0)
            CalculatorButton.DOT -> input.inputDecimalPoint()
            CalculatorButton.PLUS_MINUS -> functionPlusMinus()
            else -> {
                // do nothing
            }
        }
    }

    /**
     * Handles the button presses for the second function of each button. When the second function mode
     * is active, it will execute the corresponding function or operation for each button. If a button
     * does not have a defined action in this mode, it will do nothing. After processing the button press,
     * it will exit the second function mode.
     *
     * @param button The [CalculatorButton] that was pressed.
     */
    private fun modeSecondFunction(button : CalculatorButton) {
        display.removeState(CalculatorState.SECOND)

        when (button) {
            CalculatorButton.HYP -> functions.cycleAngleUnit(false)
            CalculatorButton.LOG -> functions.tenPowX()
            CalculatorButton.LN -> functions.exp()
            CalculatorButton.CE_C -> buttonPressedFixed()
            CalculatorButton.SIN -> functions.asin()
            CalculatorButton.COS -> functions.acos()
            CalculatorButton.TAN -> functions.atan()
            CalculatorButton.Y_POW_X -> computation.yRootX()
            CalculatorButton.X_SWAP_Y -> notImplemented()
            CalculatorButton.ONE_DIV_X -> notImplemented()
            CalculatorButton.X_SQUARED -> notImplemented()
            CalculatorButton.SQRT_X -> notImplemented()
            CalculatorButton.DIVIDE -> notImplemented()
            CalculatorButton.SUM_PLUS -> notImplemented()
            CalculatorButton.EE -> notImplemented()
            CalculatorButton.LEFT_PARENTHESES -> notImplemented()
            CalculatorButton.RIGHT_PARENTHESES -> notImplemented()
            CalculatorButton.MULTIPLY -> notImplemented()
            CalculatorButton.STORE -> notImplemented()
            CalculatorButton.SEVEN -> notImplemented()
            CalculatorButton.EIGHT -> notImplemented()
            CalculatorButton.NINE -> notImplemented()
            CalculatorButton.MINUS -> notImplemented()
            CalculatorButton.RECALL -> buttonPressedMemory(MemoryOperation.SUM)
            CalculatorButton.FOUR -> notImplemented()
            CalculatorButton.FIVE -> notImplemented()
            CalculatorButton.SIX -> notImplemented()
            CalculatorButton.PLUS -> notImplemented()
            CalculatorButton.A_B_C -> notImplemented()
            CalculatorButton.ONE -> functions.convertCmToInch()
            CalculatorButton.TWO -> functions.convertLiterToGallon()
            CalculatorButton.THREE -> functions.convertKgToPound()
            CalculatorButton.EQUAL -> functions.convertDegreesMinutesSecondsToDecimal()
            CalculatorButton.BACK -> functions2.nPr()
            CalculatorButton.ZERO -> functions.convertCelsiusToFahrenheit()
            CalculatorButton.DOT -> functions.convertGramToOunce()
            CalculatorButton.PLUS_MINUS -> functions2.polarToRectangular()
            else -> {
                // do nothing
            }
        }
    }

    /**
     * Handles the button presses for the third function of each button. When the third function mode
     * is active, it will execute the corresponding function or operation for each button. If a button
     * does not have a defined action in this mode, it will do nothing. After processing the button press,
     * it will exit the third function mode.
     *
     * @param button The [CalculatorButton] that was pressed.
     */
    private fun modeThirdFunction(button : CalculatorButton)  {
        display.removeState(CalculatorState.THIRD)

        when (button) {
            CalculatorButton.HYP -> functions.cycleAngleUnit(true)
            CalculatorButton.LOG -> functions.factorial()
            CalculatorButton.LN -> functions.thirdRootX()
            CalculatorButton.CE_C -> buttonPressedConst()
            CalculatorButton.SIN -> noOperation()
            CalculatorButton.COS -> noOperation()
            CalculatorButton.TAN -> noOperation()
            CalculatorButton.Y_POW_X -> functions.percent()
            CalculatorButton.X_SWAP_Y -> notImplemented()
            CalculatorButton.ONE_DIV_X -> noOperation()
            CalculatorButton.X_SQUARED -> noOperation()
            CalculatorButton.SQRT_X -> noOperation()
            CalculatorButton.DIVIDE -> functions.pi()
            CalculatorButton.SUM_PLUS -> notImplemented()
            CalculatorButton.EE -> selectNumberFormat(DisplayNumberFormat.DECIMAL)
            CalculatorButton.LEFT_PARENTHESES -> selectNumberFormat(DisplayNumberFormat.HEXADECIMAL)
            CalculatorButton.RIGHT_PARENTHESES -> selectNumberFormat(DisplayNumberFormat.OCTAL)
            CalculatorButton.MULTIPLY -> selectNumberFormat(DisplayNumberFormat.BINARY)
            CalculatorButton.STORE -> computation.bitwiseAnd()
            CalculatorButton.SEVEN -> computation.bitwiseOr()
            CalculatorButton.EIGHT -> computation.bitwiseXor()
            CalculatorButton.NINE -> computation.bitwiseXnor()
            CalculatorButton.MINUS -> functions.bitwiseNot()
            CalculatorButton.RECALL -> buttonPressedMemory(MemoryOperation.EXCHANGE)
            CalculatorButton.FOUR -> notImplemented()
            CalculatorButton.FIVE -> selectNumberFormat(DisplayNumberFormat.FLOAT)
            CalculatorButton.SIX -> selectNumberFormat(DisplayNumberFormat.SCIENTIFIC)
            CalculatorButton.PLUS -> selectNumberFormat(DisplayNumberFormat.ENGINEERING)
            CalculatorButton.A_B_C -> notImplemented()
            CalculatorButton.ONE -> functions.convertInchToCm()
            CalculatorButton.TWO -> functions.convertGallonToLiter()
            CalculatorButton.THREE -> functions.convertPoundToKg()
            CalculatorButton.EQUAL -> viewCurrentValueAsDegreesMinutesSeconds()
            CalculatorButton.BACK -> functions2.nCr()
            CalculatorButton.ZERO -> functions.convertFahrenheitToCelsius()
            CalculatorButton.DOT -> functions.convertOunceToGram()
            CalculatorButton.PLUS_MINUS -> functions2.rectangularToPolar()
            else -> {
                // do nothing
            }
        }
    }

    /**
     * Handles the button press for the AC/ON button. Resets the calculator to its initial state
     * and prints the current value to the display.
     */
    private fun buttonPressedAcOn() {
        reset()
        output.printValue(computation.getValue())
    }

    /** Handles the CE/C button press (currently not implemented). */
    private fun buttonPressedCeC() {
        notImplemented()
    }

    /** Toggles the THIRD function mode; deactivates SECOND mode if active. */
    private fun buttonPressedThird() {
        display.removeState(CalculatorState.SECOND)

        if (display.hasState(CalculatorState.THIRD)) {
            display.removeState(CalculatorState.THIRD)
        } else {
            display.addState(CalculatorState.THIRD)
        }
    }

    /** Toggles the SECOND function mode; deactivates THIRD mode if active. */
    private fun buttonPressedSecond() {
        display.removeState(CalculatorState.THIRD)

        if (display.hasState(CalculatorState.SECOND)) {
            display.removeState(CalculatorState.SECOND)
        } else {
            display.addState(CalculatorState.SECOND)
        }
    }

    /** Activates constant input mode; the next button press selects a physical constant. */
    private fun buttonPressedConst() {
        currentInputState = CalculatorInputState.CONSTANT
        currentInputStateWasSet = true

        output.printValue(computation.getValue())
    }

    /**
     * Activates memory mode for the given operation; the next button press selects the memory cell.
     *
     * @param memoryOperation The [MemoryOperation] to perform.
     */
    private fun buttonPressedMemory(memoryOperation: MemoryOperation) {
        currentInputState = CalculatorInputState.CONSTANT
        currentInputStateWasSet = true
        currentMemoryOperation = memoryOperation

        output.printValue(computation.getValue())
    }

    /** Activates fixed number format mode, unless a non-decimal base (hex, octal, binary) is currently active. */
    private fun buttonPressedFixed() {
        when (output.getNumberFormat()) {
            DisplayNumberFormat.HEXADECIMAL,
            DisplayNumberFormat.OCTAL,
            DisplayNumberFormat.BINARY -> return
            else -> {}
        }

        currentInputState = CalculatorInputState.FIXED_NUMBER_FORMAT
        currentInputStateWasSet = true
    }

    /**
     * Maps the given button to a physical constant and stores it as the current result.
     *
     * @param button The [CalculatorButton] that selects the constant.
     * @return `true` on success, `false` if the button is not valid in constant mode.
     */
    private fun modeConstant(button : CalculatorButton): Boolean {

        try {

            val constant = when (button) {
                CalculatorButton.SIN -> 299792458.0 // speed of light in m/s
                CalculatorButton.COS -> 9.80665 // standard gravity in m/s^2
                CalculatorButton.TAN -> 9.109383713928E-31 // electron mass in kg
                CalculatorButton.Y_POW_X -> 1.602176634E-19 // electron charge in coulombs
                CalculatorButton.ONE_DIV_X -> 6.62607015E-34 // Planck constant in J * s
                CalculatorButton.X_SQUARED -> 6.02214076E23 // Avogadro's number in mol^-1
                CalculatorButton.SQRT_X -> 8.31446261815324 // gas constant in J/(mol*K)
                CalculatorButton.DIVIDE -> 6.6743015E-11 // gravitational constant in m^3/(kg*s^2)
                else -> throw IllegalArgumentException("Invalid button for constant mode")
            }
            computation.setValue(constant)
            return true

        } catch (_: Exception) {
            return false
        }
    }

    /**
     * Maps the given button (0–9) to a memory cell and executes the pending memory operation.
     *
     * @param button The [CalculatorButton] representing the memory cell index (0–9).
     * @return `true` on success, `false` if the button is not a digit.
     */
    private fun modeMemory(button : CalculatorButton): Boolean {
        require(currentMemoryOperation != MemoryOperation.NONE)

        try {

            val memoryCell = when (button) {
                CalculatorButton.ZERO -> 0
                CalculatorButton.ONE -> 1
                CalculatorButton.TWO -> 2
                CalculatorButton.THREE -> 3
                CalculatorButton.FOUR -> 4
                CalculatorButton.FIVE -> 5
                CalculatorButton.SIX -> 6
                CalculatorButton.SEVEN -> 7
                CalculatorButton.EIGHT -> 8
                CalculatorButton.NINE -> 9
                else -> throw IllegalArgumentException("Invalid button for memory mode")
            }

            memory.performOperation(currentMemoryOperation, memoryCell)

            return true

        } catch (_: Exception) {
            return false
        }
    }

    /**
     * Maps the given button (0–9) to a number of decimal digits and applies the fixed number format.
     *
     * @param button The [CalculatorButton] representing the number of digits (0–8; 9 = automatic).
     * @return `true` on success, `false` if the button is not a digit.
     */
    private fun modeFixedNumberFormat(button : CalculatorButton): Boolean {

        try {

            val numberOfDigits = when (button) {
                CalculatorButton.ZERO -> 0
                CalculatorButton.ONE -> 1
                CalculatorButton.TWO -> 2
                CalculatorButton.THREE -> 3
                CalculatorButton.FOUR -> 4
                CalculatorButton.FIVE -> 5
                CalculatorButton.SIX -> 6
                CalculatorButton.SEVEN -> 7
                CalculatorButton.EIGHT -> 8
                CalculatorButton.NINE -> -1 // 9 means "use as many digits as needed"
                else -> throw IllegalArgumentException("Invalid button for fixed number format mode")
            }

            output.numberOfDigitsAfterDecimalPoint = numberOfDigits
            output.printValue(computation.getValue())

            return true

        } catch (_: Exception) {
            return false
        }
    }

    /**
     * Sets the given number format and reprints the current value.
     *
     * @param numberFormat The [DisplayNumberFormat] to apply.
     */
    private fun selectNumberFormat(numberFormat: DisplayNumberFormat) {
        output.setNumberFormat(numberFormat)
        output.printValue(computation.getValue())
    }

    /** Toggles the sign: negates via input in edit mode, otherwise applies the negate function. */
    private fun functionPlusMinus() {
        if (input.isEditMode) {
            input.inputPlusMinus()
        } else {
            functions.negate()
        }
    }

    /** Called when the displayed input changes; converts the display value to a number and updates the computation. */
    private fun onEditInputChanged() {
        val inputValue = display.convertDisplayValueToNumeric()
        computation.setValue(inputValue, false)
    }

    /** Called when edit mode begins; signals the computation to accept a new number. */
    private fun onEditModeBegin() {
        computation.enterNewNumber()
    }

    /** Called when a value is printed to the display; ends the input edit mode. */
    private fun onPrint() {
        input.endEditMode()
    }

    /**
     * Called when the computation result changes; prints the new value to the display.
     *
     * @param value The new result value to display.
     */
    private fun onResultChanged(value : Double) {
        output.printValue(value)
    }

    /**
     * Called when the memory content changes; shows or hides the MEMORY indicator on the display.
     *
     * @param hasContent `true` if memory contains a value, `false` if it is empty.
     */
    private fun onMemoryContentChanged(hasContent : Boolean) {
        if (hasContent) {
            display.addState(CalculatorState.MEMORY)
        } else {
            display.removeState(CalculatorState.MEMORY)
        }
    }

    /**
     * Displays the current value on the display in degrees, minutes, and seconds format.
     */
    private fun viewCurrentValueAsDegreesMinutesSeconds() {
        val currentValue = computation.getValue()
        display.viewValueAsDegreesMinutesSeconds(currentValue)
    }

    /** Displays a "not implemented" message on the display. */
    private fun notImplemented() {
        display.printNotImplemented()
    }

    /** Explicit no-op placeholder for buttons without a function in the current mode. */
    private fun noOperation() {
        // do nothing
    }
}
