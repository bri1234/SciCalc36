package com.bri1234.ti36calculator

import android.util.Log

/**
 * A simulator class for the TI-36 calculator.
 */
class Ti36Simulator {

    private val display = Ti36Display()
    private val input = Ti36Input(display)
    private val output = Ti36Output(display)
    private val computation = Ti36Computation()
    private val functions = Ti36Functions(display, computation)

    private val memory = Ti36Memory(computation)

    private var isErrorState: Boolean = false

    private var modeConst: Boolean = false
    private var modeConstWasSet: Boolean = false
    private var modeMemory: MemoryOperation = MemoryOperation.NONE
    private var modeMemoryWasSet: Boolean = false

    fun getDisplayState(): CalculatorDisplayData = display.getDisplayState()

    init {
        buttonPressedAcOn()
        // viewAll()

        input.onEditInputChanged += { onEditInputChanged() }
        input.onEditModeBegin += { onEditModeBegin() }
        output.onPrint += { onPrint() }
        computation.onResultChanged += { value -> onResultChanged(value) }
        memory.onContentChanged += { hasContent -> onMemoryContentChanged(hasContent) }
    }

    fun reset() {
        isErrorState = false
        modeConst = false
        modeConstWasSet = false
        modeMemory = MemoryOperation.NONE
        modeMemoryWasSet = false

        computation.reset()
        display.reset()
        input.reset()
        output.reset()
        memory.reset()
    }

    fun buttonPressed(button: CalculatorButton) {

        if (isErrorState) {
            if (button == CalculatorButton.AC_ON) {
                buttonPressedAcOn()
            }
            return
        }

        modeConstWasSet = false
        modeMemoryWasSet = false

        try {
            when (button) {
                CalculatorButton.AC_ON -> buttonPressedAcOn()
                CalculatorButton.SECOND -> buttonPressedSecond()
                CalculatorButton.THIRD -> buttonPressedThird()

                else -> {
                    when {
                        modeConst -> modeConstant(button)
                        modeMemory != MemoryOperation.NONE -> modeMemory(button)
                        display.isSecondFunctionActive() -> modeSecondFunction(button)
                        display.isThirdFunctionActive() -> modeThirdFunction(button)
                        else -> modeFirstFunction(button)
                    }
                }

            }

            if (!modeConstWasSet)
                modeConst = false

            if (!modeMemoryWasSet)
                modeMemory = MemoryOperation.NONE

        } catch (e: Exception) {
            Log.e("Ti36Simulator", "Error during button press", e)
            isErrorState = true
            output.printError()
        }

    }

    private fun modeFirstFunction(button : CalculatorButton) {
        when (button) {
            CalculatorButton.HYP -> functions.hyp()
            CalculatorButton.LOG -> functions.log()
            CalculatorButton.LN -> functions.ln()
            CalculatorButton.CE_C -> buttonPressedCeC()
            CalculatorButton.SIN -> functions.sin()
            CalculatorButton.COS -> functions.cos()
            CalculatorButton.TAN -> functions.tan()
            CalculatorButton.Y_POW_X -> computation.operation(Operation.Y_POW_X)
            CalculatorButton.X_SWAP_Y -> computation.swap()
            CalculatorButton.ONE_DIV_X -> functions.oneDivX()
            CalculatorButton.X_SQUARED -> functions.xSquared()
            CalculatorButton.SQRT_X -> functions.squareRootX()
            CalculatorButton.DIVIDE -> computation.operation(Operation.DIVISION)
            CalculatorButton.SUM_PLUS -> notImplemented()
            CalculatorButton.EE -> input.enterExponentEditMode()
            CalculatorButton.LEFT_PARENTHESES -> computation.operation(Operation.LEFT_PARENTHESES)
            CalculatorButton.RIGHT_PARENTHESES -> computation.operation(Operation.RIGHT_PARENTHESES)
            CalculatorButton.MULTIPLY -> computation.operation(Operation.MULTIPLICATION)
            CalculatorButton.STORE -> buttonPressedMemory(MemoryOperation.STORE)
            CalculatorButton.SEVEN -> input.inputDigit(7)
            CalculatorButton.EIGHT -> input.inputDigit(8)
            CalculatorButton.NINE -> input.inputDigit(9)
            CalculatorButton.MINUS -> computation.operation(Operation.SUBTRACTION)
            CalculatorButton.RECALL -> buttonPressedMemory(MemoryOperation.RECALL)
            CalculatorButton.FOUR -> input.inputDigit(4)
            CalculatorButton.FIVE -> input.inputDigit(5)
            CalculatorButton.SIX -> input.inputDigit(6)
            CalculatorButton.PLUS -> computation.operation(Operation.ADDITION)
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

    private fun modeSecondFunction(button : CalculatorButton) {
        display.removeState(CalculatorState.SECOND)

        when (button) {
            CalculatorButton.HYP -> functions.cycleAngleUnit(false)
            CalculatorButton.LOG -> functions.tenPowX()
            CalculatorButton.LN -> functions.exp()
            CalculatorButton.CE_C -> selectNumberFormat(DisplayNumberFormat.FIX)
            CalculatorButton.SIN -> functions.asin()
            CalculatorButton.COS -> functions.acos()
            CalculatorButton.TAN -> functions.atan()
            CalculatorButton.Y_POW_X -> computation.operation(Operation.Y_ROOT_X)
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
            CalculatorButton.EQUAL -> notImplemented()
            CalculatorButton.BACK -> notImplemented()
            CalculatorButton.ZERO -> functions.convertCelsiusToFahrenheit()
            CalculatorButton.DOT -> functions.convertGramToOunce()
            CalculatorButton.PLUS_MINUS -> notImplemented()
            else -> {
                // do nothing
            }
        }
    }

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
            CalculatorButton.Y_POW_X -> notImplemented()
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
            CalculatorButton.STORE -> computation.operation(Operation.BITWISE_AND)
            CalculatorButton.SEVEN -> computation.operation(Operation.BITWISE_OR)
            CalculatorButton.EIGHT -> computation.operation(Operation.BITWISE_XOR)
            CalculatorButton.NINE -> computation.operation(Operation.BITWISE_XNOR)
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
            CalculatorButton.EQUAL -> notImplemented()
            CalculatorButton.BACK -> notImplemented()
            CalculatorButton.ZERO -> functions.convertFahrenheitToCelsius()
            CalculatorButton.DOT -> functions.convertOunceToGram()
            CalculatorButton.PLUS_MINUS -> notImplemented()
            else -> {
                // do nothing
            }
        }
    }

    private fun buttonPressedAcOn() {
        reset()
        output.printValue(computation.getValue())
    }

    private fun buttonPressedCeC() {
        notImplemented()
    }

    private fun buttonPressedThird() {
        display.removeState(CalculatorState.SECOND)

        if (display.hasState(CalculatorState.THIRD)) {
            display.removeState(CalculatorState.THIRD)
        } else {
            display.addState(CalculatorState.THIRD)
        }
    }

    private fun buttonPressedSecond() {
        display.removeState(CalculatorState.THIRD)

        if (display.hasState(CalculatorState.SECOND)) {
            display.removeState(CalculatorState.SECOND)
        } else {
            display.addState(CalculatorState.SECOND)
        }
    }

    private fun buttonPressedConst() {
        modeConst = true
        modeConstWasSet = true
    }

    private fun buttonPressedMemory(memoryOperation: MemoryOperation) {
        modeMemory = memoryOperation
        modeMemoryWasSet = true
        output.printValue(computation.getValue())
    }

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
            computation.setResult(constant)
            return true

        } catch (_: Exception) {
            return false
        }
    }

    private fun modeMemory(button : CalculatorButton): Boolean {

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

            memory.performOperation(modeMemory, memoryCell)

            return true

        } catch (_: Exception) {
            return false
        }
    }

    private fun selectNumberFormat(numberFormat: DisplayNumberFormat) {
        output.setNumberFormat(numberFormat)
        output.printValue(computation.getValue())
    }

    private fun functionPlusMinus() {
        if (input.isEditMode) {
            input.inputPlusMinus()
        } else {
            functions.negate()
        }
    }

    private fun onEditInputChanged() {
        val inputValue = display.convertDisplayValueToNumeric()
        computation.setValue(inputValue)
    }

    private fun onEditModeBegin() {
        computation.enterNewNumber()
    }

    private fun onPrint() {
        input.endEditMode()
    }

    private fun onResultChanged(value : Double) {
        output.printValue(value)
    }

    private fun onMemoryContentChanged(hasContent : Boolean) {
        if (hasContent) {
            display.addState(CalculatorState.MEMORY)
        } else {
            display.removeState(CalculatorState.MEMORY)
        }
    }

    private fun notImplemented() {
        display.printNotImplemented()
    }

    private fun noOperation() {
        // do nothing
    }
}



