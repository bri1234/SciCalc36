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

import com.bri1234.ti36calculator.views.DisplayLabels

/*
Next features to implement:

- P->R, R->P buttons (Polar/Rectangular conversion)
- A B/C button (Fractions)
- d/c button (Fractions)
- F<->D button (Fractions)

*/

/**
 * A simulator class for the TI-36 calculator.
 */
class CalculatorCore(
    private val logger: CalculatorLogger = NoOpCalculatorLogger,
) {

    private val state = CalculatorState()

    val display = CalculatorNumericDisplay(state)
    private val input = CalculatorInput(state, display)
    private val computation = CalculatorComputation(state)
    private val functions = CalculatorFunctions(state, computation)
    private val functions2 = CalculatorFunctions2(state, computation)
    private val memory = CalculatorMemory(computation)
    private val statistic = CalculatorStatistic(state, computation)

    private var isErrorState: Boolean = false

    private var currentInputState: CalculatorInputState = CalculatorInputState.NONE
    private var currentInputStateWasSet: Boolean = false
    private var currentMemoryOperation: MemoryOperation = MemoryOperation.NONE

    private var calculatorHypModeWasSet: Boolean = false

    // Sets up event listeners for input changes, edit mode, print events,
    // result changes, and memory content changes. Also resets the calculator to its initial state.
    init {
        buttonPressedAcOn()
        // viewAll()

        input.onEditInputChanged += { onEditInputChanged() }
        input.onEditModeBegin += { onEditModeBegin() }
        display.onPrint += { onPrint() }
        computation.onResultChanged += { value -> onResultChanged(value) }
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

        calculatorHypModeWasSet = false

        state.reset()
        computation.reset()
        display.reset()
        input.reset()
        memory.reset()
        statistic.reset()
    }

    /** Handles the CE/C button press. */
    private fun buttonPressedCeC() {
        // TODO: distinguish CE and C based on current input state (CE in edit mode, C otherwise)

        isErrorState = false

        currentInputState = CalculatorInputState.NONE
        currentInputStateWasSet = false

        currentMemoryOperation = MemoryOperation.NONE

        calculatorHypModeWasSet = false

        computation.setValue(0.0)
        input.reset()
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

        if (BuildConfig.DEBUG) computation.printInfo()

        if (isErrorState) {
            if (button == CalculatorButton.AC_ON) {
                buttonPressedAcOn()
            }
            return
        }

        currentInputStateWasSet = false
        calculatorHypModeWasSet = false

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
                        CalculatorInputState.NONE -> {

                            when (state.calculatorNumberMode) {
                                CalculatorNumberMode.HEXADECIMAL,
                                CalculatorNumberMode.OCTAL,
                                CalculatorNumberMode.BINARY -> modeHexOctBin(button)
                                else -> {
                                    modeFunctions(button)
                                }
                            }

                            state.calculatorFunction = CalculatorFunction.FIRST
                        }
                    }

                    if (!calculatorHypModeWasSet) {
                        state.calculatorHypMode = CalculatorHypMode.OFF
                    }
                }

            }

            if (!currentInputStateWasSet) {
                currentInputState = CalculatorInputState.NONE
                currentMemoryOperation = MemoryOperation.NONE
            }

        } catch (e: Exception) {
            logger.error("Error during button press", e)
            isErrorState = true
            display.printError()
        }

        if (BuildConfig.DEBUG) computation.printInfo()
    }

    /** Handles the button presses for the current function mode.
     *
     * @param button The [CalculatorButton] that was pressed.
     */
    private fun modeFunctions(button : CalculatorButton) {
        when (state.calculatorFunction) {
            CalculatorFunction.FIRST -> modeFirstFunction(button)
            CalculatorFunction.SECOND -> modeSecondFunction(button)
            CalculatorFunction.THIRD -> modeThirdFunction(button)
        }
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
            CalculatorButton.HYP -> buttonPressedHyp()
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
            CalculatorButton.SUM_PLUS -> statistic.addValue()
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
            CalculatorButton.A_B_C -> notImplemented("A_B_C")
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
        state.calculatorFunction = CalculatorFunction.FIRST

        when (button) {
            CalculatorButton.HYP -> cycleAngleUnit(false)
            CalculatorButton.LOG -> functions.tenPowX()
            CalculatorButton.LN -> functions.exp()
            CalculatorButton.CE_C -> buttonPressedFixed()
            CalculatorButton.SIN -> functions.asin()
            CalculatorButton.COS -> functions.acos()
            CalculatorButton.TAN -> functions.atan()
            CalculatorButton.Y_POW_X -> computation.yRootX()
            CalculatorButton.X_SWAP_Y -> statistic.clearStatistic()
            CalculatorButton.ONE_DIV_X -> enterFrequencyMode()
            CalculatorButton.X_SQUARED -> statistic.printMeanX()
            CalculatorButton.SQRT_X -> statistic.printSampleStandardDeviationX()
            CalculatorButton.DIVIDE -> statistic.printPopulationStandardDeviationX()
            CalculatorButton.SUM_PLUS -> statistic.subtractValue()
            CalculatorButton.EE -> statistic.printCount()
            CalculatorButton.LEFT_PARENTHESES -> statistic.printMeanY()
            CalculatorButton.RIGHT_PARENTHESES -> statistic.printSampleStandardDeviationY()
            CalculatorButton.MULTIPLY -> statistic.printPopulationStandardDeviationY()
            CalculatorButton.STORE -> statistic.printSumX()
            CalculatorButton.SEVEN -> statistic.printSumX2()
            CalculatorButton.EIGHT -> statistic.printSumY()
            CalculatorButton.NINE -> statistic.printSumY2()
            CalculatorButton.MINUS -> statistic.printSumXY()
            CalculatorButton.RECALL -> buttonPressedMemory(MemoryOperation.SUM)
            CalculatorButton.FOUR -> statistic.printIntercept()
            CalculatorButton.FIVE -> statistic.printSlope()
            CalculatorButton.SIX -> statistic.printPredictedX()
            CalculatorButton.PLUS -> statistic.printPredictedY()
            CalculatorButton.A_B_C -> notImplemented("D/C")
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
        state.calculatorFunction = CalculatorFunction.FIRST

        when (button) {
            CalculatorButton.HYP -> cycleAngleUnit(true)
            CalculatorButton.LOG -> functions.factorial()
            CalculatorButton.LN -> functions.thirdRootX()
            CalculatorButton.CE_C -> buttonPressedConst()
            CalculatorButton.SIN -> noOperation()
            CalculatorButton.COS -> noOperation()
            CalculatorButton.TAN -> noOperation()
            CalculatorButton.Y_POW_X -> functions.percent()
            CalculatorButton.X_SWAP_Y -> enableStatistic1()
            CalculatorButton.ONE_DIV_X -> noOperation()
            CalculatorButton.X_SQUARED -> noOperation()
            CalculatorButton.SQRT_X -> noOperation()
            CalculatorButton.DIVIDE -> pi()
            CalculatorButton.SUM_PLUS -> enableStatistic2()
            CalculatorButton.EE -> selectNumberMode(CalculatorNumberMode.DECIMAL)
            CalculatorButton.LEFT_PARENTHESES -> selectNumberMode(CalculatorNumberMode.HEXADECIMAL)
            CalculatorButton.RIGHT_PARENTHESES -> selectNumberMode(CalculatorNumberMode.OCTAL)
            CalculatorButton.MULTIPLY -> selectNumberMode(CalculatorNumberMode.BINARY)
            CalculatorButton.STORE -> computation.bitwiseAnd()
            CalculatorButton.SEVEN -> computation.bitwiseOr()
            CalculatorButton.EIGHT -> computation.bitwiseXor()
            CalculatorButton.NINE -> computation.bitwiseXnor()
            CalculatorButton.MINUS -> functions.bitwiseNot()
            CalculatorButton.RECALL -> buttonPressedMemory(MemoryOperation.EXCHANGE)
            CalculatorButton.FOUR -> statistic.printCorrelationCoefficient()
            CalculatorButton.FIVE -> selectNumberFormat(DisplayNumberFormat.FLOAT)
            CalculatorButton.SIX -> selectNumberFormat(DisplayNumberFormat.SCIENTIFIC)
            CalculatorButton.PLUS -> selectNumberFormat(DisplayNumberFormat.ENGINEERING)
            CalculatorButton.A_B_C -> notImplemented("F <-> D")
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
     * Handles the button presses for the HEX mode.
     *
     * @param button The [CalculatorButton] that was pressed.
     */
    private fun modeHexOctBin(button : CalculatorButton)  {
        val isFirstFunc = state.calculatorFunction == CalculatorFunction.FIRST
        val isSecondFunc = state.calculatorFunction == CalculatorFunction.SECOND
        val isThirdFunc = state.calculatorFunction == CalculatorFunction.THIRD
        val isHex = state.calculatorNumberMode == CalculatorNumberMode.HEXADECIMAL

        when (button) {
            CalculatorButton.HYP -> check(!isThirdFunc)

            CalculatorButton.LOG -> { check(!isFirstFunc); modeFunctions(button) }

            CalculatorButton.CE_C -> if (isFirstFunc) modeFunctions(button)

            CalculatorButton.ONE_DIV_X -> { check(isHex); input.inputDigit(10) }

            CalculatorButton.X_SQUARED -> {
                if (isHex) input.inputDigit(11)
                else {
                    check(isFirstFunc)
                    modeFunctions(button)
                }
            }

            CalculatorButton.SQRT_X -> { check(isHex); input.inputDigit(12) }
            CalculatorButton.SIN -> { check(isHex); input.inputDigit(13) }
            CalculatorButton.COS -> { check(isHex); input.inputDigit(14) }
            CalculatorButton.TAN -> { check(isHex); input.inputDigit(15) }

            CalculatorButton.SUM_PLUS -> { check(isThirdFunc); modeFunctions(button) }

            CalculatorButton.EE -> { check(!isSecondFunc); if (isThirdFunc) modeFunctions(button) }

            CalculatorButton.X_SWAP_Y,
            CalculatorButton.LEFT_PARENTHESES,
            CalculatorButton.RIGHT_PARENTHESES,
            CalculatorButton.MULTIPLY,
            CalculatorButton.STORE,
            CalculatorButton.SEVEN,
            CalculatorButton.EIGHT,
            CalculatorButton.NINE,
            CalculatorButton.MINUS -> { check(!isSecondFunc); modeFunctions(button) }

            CalculatorButton.RECALL -> modeFunctions(button)

            CalculatorButton.FIVE,
            CalculatorButton.SIX,
            CalculatorButton.PLUS -> { check(!isSecondFunc); if (isFirstFunc) modeFunctions(button) }

            CalculatorButton.A_B_C -> noOperation()

            CalculatorButton.DIVIDE,
            CalculatorButton.Y_POW_X,
            CalculatorButton.ONE,
            CalculatorButton.TWO,
            CalculatorButton.THREE,
            CalculatorButton.FOUR,
            CalculatorButton.EQUAL,
            CalculatorButton.BACK,
            CalculatorButton.ZERO,
            CalculatorButton.PLUS_MINUS -> { check(isFirstFunc); modeFunctions(button) }

            CalculatorButton.DOT -> check(isFirstFunc)

            else -> {
                throw IllegalArgumentException("Invalid button in hexadecimal mode")
            }
        }
    }

    /**
     * Handles the button press for the AC/ON button. Resets the calculator to its initial state
     * and prints the current value to the display.
     */
    private fun buttonPressedAcOn() {
        reset()
        display.printValue(computation.getValue())
    }

    /** Toggles the THIRD function mode; deactivates SECOND mode if active. */
    private fun buttonPressedThird() {
        state.calculatorFunction = when (state.calculatorFunction) {
            CalculatorFunction.THIRD -> CalculatorFunction.FIRST
            else -> CalculatorFunction.THIRD
        }
    }

    /** Toggles the SECOND function mode; deactivates THIRD mode if active. */
    private fun buttonPressedSecond() {
        state.calculatorFunction = when (state.calculatorFunction) {
            CalculatorFunction.SECOND -> CalculatorFunction.FIRST
            else -> CalculatorFunction.SECOND
        }
    }

    /**
     * Toggles the HYP mode. If HYP mode is currently active, it will be deactivated.
     * If it is currently inactive, it will be activated unless the current number format is hexadecimal, octal, or binary.
     */
    private fun buttonPressedHyp() {
        if (state.calculatorHypMode == CalculatorHypMode.HYP) {
            state.calculatorHypMode = CalculatorHypMode.OFF
        }
        else
        {
            when (state.calculatorNumberMode) {
                CalculatorNumberMode.HEXADECIMAL,
                CalculatorNumberMode.OCTAL,
                CalculatorNumberMode.BINARY -> {}
                else -> {
                    state.calculatorHypMode = CalculatorHypMode.HYP
                    calculatorHypModeWasSet = true
                }
            }
        }
    }

    /** Activates constant input mode; the next button press selects a physical constant. */
    private fun buttonPressedConst() {
        currentInputState = CalculatorInputState.CONSTANT
        currentInputStateWasSet = true

        display.printValue(computation.getValue())
    }

    /**
     * Activates memory mode for the given operation; the next button press selects the memory cell.
     *
     * @param memoryOperation The [MemoryOperation] to perform.
     */
    private fun buttonPressedMemory(memoryOperation: MemoryOperation) {
        currentInputState = CalculatorInputState.MEMORY
        currentInputStateWasSet = true
        currentMemoryOperation = memoryOperation

        display.printValue(computation.getValue())
    }

    /** Activates fixed number format mode, unless a non-decimal base (hex, octal, binary) is currently active. */
    private fun buttonPressedFixed() {
        when (state.calculatorNumberMode) {
            CalculatorNumberMode.HEXADECIMAL,
            CalculatorNumberMode.OCTAL,
            CalculatorNumberMode.BINARY -> {}
            else -> {
                currentInputState = CalculatorInputState.FIXED_NUMBER_FORMAT
                currentInputStateWasSet = true
            }
        }
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

    fun pi() {
        computation.getValue()
        computation.setValue(Math.PI)
    }

    /**
     * Maps the given button (0–9) to a memory cell and executes the pending memory operation.
     *
     * @param button The [CalculatorButton] representing the memory cell index (0–9).
     * @return `true` on success, `false` if the button is not a digit.
     */
    private fun modeMemory(button : CalculatorButton): Boolean {
        require(currentMemoryOperation != MemoryOperation.NONE)

        if (button == CalculatorButton.STORE) {
            currentMemoryOperation = MemoryOperation.STORE
            currentInputStateWasSet = true
            return false
        }

        if (button == CalculatorButton.RECALL) {
            currentMemoryOperation = MemoryOperation.RECALL
            currentInputStateWasSet = true
            return false
        }

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
                CalculatorButton.NINE -> 9
                CalculatorButton.DOT -> -1 // clears fixed number format, returns to automatic mode
                else -> throw IllegalArgumentException("Invalid button for fixed number format mode")
            }

            display.numberOfDigitsAfterDecimalPoint = numberOfDigits
            display.printValue(computation.getValue())

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
        display.setNumberFormat(numberFormat)
        display.printValue(computation.getValue())
    }

    /**
     * Sets the given number mode and reprints the current value.
     *
     * @param numberMode The [CalculatorNumberMode] to apply.
     */
    private fun selectNumberMode(numberMode: CalculatorNumberMode) {
        state.calculatorNumberMode = numberMode
        state.calculatorStatisticMode = CalculatorStatisticMode.OFF

        when (numberMode) {
            CalculatorNumberMode.HEXADECIMAL,
            CalculatorNumberMode.OCTAL,
            CalculatorNumberMode.BINARY -> {
                val intValue = computation.getValue().toLong()
                computation.setValue(intValue.toDouble())
            }
            else -> { }
        }

        display.printValue(computation.getValue())
    }

    /** Returns `true` if the current number mode is hexadecimal, `false` otherwise. */
    fun isNumberModeHex() : Boolean{
        return state.calculatorNumberMode == CalculatorNumberMode.HEXADECIMAL
    }

    /** Toggles the sign: negates via input in edit mode, otherwise applies the negate function. */
    private fun functionPlusMinus() {
        if (!input.isEditMode
            || (state.calculatorNumberMode == CalculatorNumberMode.HEXADECIMAL)
            || (state.calculatorNumberMode == CalculatorNumberMode.OCTAL)
            || (state.calculatorNumberMode == CalculatorNumberMode.BINARY)) {

            functions.negate()
        } else {
            input.inputPlusMinus()
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
        display.printValue(value)
    }

    /**
     * Displays the current value on the display in degrees, minutes, and seconds format.
     */
    private fun viewCurrentValueAsDegreesMinutesSeconds() {
        val currentValue = computation.getValue()
        display.viewValueAsDegreesMinutesSeconds(currentValue)
    }

    /**
     * Cycles through the angle units (degrees, radians, gradians) and converts the current value if needed.
     * @param convert `true` to convert the current value to the new angle unit, `false` to only change the unit without conversion.
     */
    private fun cycleAngleUnit(convert: Boolean) {

        state.calculatorAngleUnit = when (state.calculatorAngleUnit) {

            CalculatorAngleUnit.DEG -> {
                if (convert) {
                    val value = computation.getValue()
                    computation.setValue(value * Math.PI / 180)
                }
                CalculatorAngleUnit.RAD
            }

            CalculatorAngleUnit.RAD -> {
                if (convert) {
                    val value = computation.getValue()
                    computation.setValue(value * 200 / Math.PI)
                }
                CalculatorAngleUnit.GRAD
            }

            CalculatorAngleUnit.GRAD -> {
                if (convert) {
                    val value = computation.getValue()
                    computation.setValue(value * 180 / 200)
                }
                CalculatorAngleUnit.DEG
            }
        }
    }

    /**
     * Returns the current display state of the calculator.
     *
     * @return The current [CalculatorDisplayData].
     */
    fun getDisplayData(): CalculatorDisplayData {

        val labels = mutableSetOf<DisplayLabels>()

        if (memory.hasNonZeroMemory())
            labels.add(DisplayLabels.MEMORY)

        if (computation.hasParentheses())
            labels.add(DisplayLabels.PARENTHESES)

        if (state.calculatorNumberMode == CalculatorNumberMode.DECIMAL) {
            if (state.calculatorAngleUnit == CalculatorAngleUnit.DEG)
                labels.add(DisplayLabels.DEG)

            if (state.calculatorAngleUnit == CalculatorAngleUnit.RAD)
                labels.add(DisplayLabels.RAD)

            if (state.calculatorAngleUnit == CalculatorAngleUnit.GRAD)
                labels.add(DisplayLabels.GRAD)
        }

        if (state.calculatorHypMode == CalculatorHypMode.HYP)
            labels.add(DisplayLabels.HYP)

        if (state.calculatorStatisticMode == CalculatorStatisticMode.STAT1)
            labels.add(DisplayLabels.STAT)

        if (state.calculatorStatisticMode == CalculatorStatisticMode.STAT2)
            labels.add(DisplayLabels.STAT)

        if (state.calculatorNumberMode == CalculatorNumberMode.HEXADECIMAL)
            labels.add(DisplayLabels.HEX)

        if (state.calculatorNumberMode == CalculatorNumberMode.OCTAL)
            labels.add(DisplayLabels.OCT)

        if (state.calculatorNumberMode == CalculatorNumberMode.BINARY)
            labels.add(DisplayLabels.BIN)

        if (state.calculatorFunction == CalculatorFunction.SECOND)
            labels.add(DisplayLabels.SECOND)

        if (state.calculatorFunction == CalculatorFunction.THIRD)
            labels.add(DisplayLabels.THIRD)

        return CalculatorDisplayData(
            digitsLarge = display.displayMantissa.copyOf(),
            decimalPointIndex = display.displayDecimalPointPos,
            digitsSmall = display.displayExponent.copyOf(),
            displayLabels = labels.toSet(),
        )
    }

    private fun noOperation() {
        // do nothing
    }

    /**
     * Displays a "not implemented" message on the display.
     */
    private fun notImplemented(functionName: String) {
        logger.info("function $functionName is not implemented")
    }

    /**
     * Enters the frequency edit mode for the statistic input.
     * Requires that a statistic mode is active.
     */
    private fun enterFrequencyMode() {
        require(state.calculatorStatisticMode != CalculatorStatisticMode.OFF)
        input.enterFrequencyEditMode()
    }

    /**
     * Enables the first statistic mode and updates the display with the current value.
     */
    private fun enableStatistic1() {
        statistic.enableStatistic1()
        display.printValue(computation.getValue())
    }

    /**
     * Enables the second statistic mode and updates the display with the current value.
     */
    private fun enableStatistic2() {
        statistic.enableStatistic2()
        display.printValue(computation.getValue())
    }
}
