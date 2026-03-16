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

    private var isErrorState: Boolean = false

    private var constModeWasSet: Boolean = false

    fun getDisplayState(): CalculatorDisplayData = display.getDisplayState()

    init {
        buttonPressedAcOn()
        // viewAll()

        input.onEditInputChanged += { onEditInputChanged() }
        output.onPrint += { onPrint() }
        computation.onResultChanged += { value -> onResultChanged(value) }
    }

    fun buttonPressed(button: CalculatorButton) {

        if (isErrorState) {
            if (button == CalculatorButton.AC_ON) {
                buttonPressedAcOn()
            }
            return
        }

        constModeWasSet = false

        try {
            when (button) {
                CalculatorButton.AC_ON -> buttonPressedAcOn()
                CalculatorButton.SECOND -> buttonPressedSecond()
                CalculatorButton.THIRD -> buttonPressedThird()

                else -> {
                    when {
                        display.isSecondFunctionActive() -> buttonSecondFunction(button)
                        display.isThirdFunctionActive() -> buttonThirdFunction(button)
                        else -> buttonFirstFunction(button)
                    }
                }

            }

            if (!constModeWasSet) {
                display.removeState(CalculatorState.CONST)
            }

        } catch (e: Exception) {
            Log.e("Ti36Simulator", "Error during button press", e)
            isErrorState = true
            output.printError()
        }

    }

    private fun buttonFirstFunction(button : CalculatorButton) {

        if (display.hasState(CalculatorState.CONST)) {
            if (constantSet(button))
                return
        }

        when (button) {
            CalculatorButton.HYP -> functions.hyp()
            CalculatorButton.LOG -> functions.log()
            CalculatorButton.LN -> functions.ln()
            CalculatorButton.CE_C -> functions.ceC()
            CalculatorButton.SIN -> functions.sin()
            CalculatorButton.COS -> functions.cos()
            CalculatorButton.TAN -> functions.tan()
            CalculatorButton.Y_POW_X -> functions.yPowX()
            CalculatorButton.X_SWAP_Y -> functions.xSwapY()
            CalculatorButton.ONE_DIV_X -> functions.oneDivX()
            CalculatorButton.X_SQUARED -> functions.xSquared()
            CalculatorButton.SQRT_X -> functions.sqrtX()
            CalculatorButton.DIVIDE -> functions.divide()
            CalculatorButton.SUM_PLUS -> functions.sumPlus()
            CalculatorButton.EE -> input.enterExponentEditMode()
            CalculatorButton.LEFT_PARENTHESES -> functions.leftParentheses()
            CalculatorButton.RIGHT_PARENTHESES -> functions.rightParentheses()
            CalculatorButton.MULTIPLY -> functions.multiply()
            CalculatorButton.STORE -> functions.store()
            CalculatorButton.SEVEN -> input.inputDigit(7)
            CalculatorButton.EIGHT -> input.inputDigit(8)
            CalculatorButton.NINE -> input.inputDigit(9)
            CalculatorButton.MINUS -> functions.minus()
            CalculatorButton.RECALL -> functions.recall()
            CalculatorButton.FOUR -> input.inputDigit(4)
            CalculatorButton.FIVE -> input.inputDigit(5)
            CalculatorButton.SIX -> input.inputDigit(6)
            CalculatorButton.PLUS -> functions.plus()
            CalculatorButton.A_B_C -> functions.aBC()
            CalculatorButton.ONE -> input.inputDigit(1)
            CalculatorButton.TWO -> input.inputDigit(2)
            CalculatorButton.THREE -> input.inputDigit(3)
            CalculatorButton.EQUAL -> functions.equal()
            CalculatorButton.BACK -> input.inputBack()
            CalculatorButton.ZERO -> input.inputDigit(0)
            CalculatorButton.DOT -> input.inputDecimalPoint()
            CalculatorButton.PLUS_MINUS -> input.inputPlusMinus()
            else -> {
                // do nothing
            }
        }
    }

    private fun buttonSecondFunction(button : CalculatorButton) {
        display.removeState(CalculatorState.SECOND)

        when (button) {
            CalculatorButton.HYP -> functions.cycleAngleUnit(false)
            CalculatorButton.LOG -> functions.tenPowX()
            CalculatorButton.LN -> functions.exp()
            CalculatorButton.CE_C -> functions.notImplemented()
            CalculatorButton.SIN -> functions.notImplemented()
            CalculatorButton.COS -> functions.notImplemented()
            CalculatorButton.TAN -> functions.notImplemented()
            CalculatorButton.Y_POW_X -> functions.notImplemented()
            CalculatorButton.X_SWAP_Y -> functions.notImplemented()
            CalculatorButton.ONE_DIV_X -> functions.notImplemented()
            CalculatorButton.X_SQUARED -> functions.notImplemented()
            CalculatorButton.SQRT_X -> functions.notImplemented()
            CalculatorButton.DIVIDE -> functions.notImplemented()
            CalculatorButton.SUM_PLUS -> functions.notImplemented()
            CalculatorButton.EE -> functions.notImplemented()
            CalculatorButton.LEFT_PARENTHESES -> functions.notImplemented()
            CalculatorButton.RIGHT_PARENTHESES -> functions.notImplemented()
            CalculatorButton.MULTIPLY -> functions.notImplemented()
            CalculatorButton.STORE -> functions.notImplemented()
            CalculatorButton.SEVEN -> functions.notImplemented()
            CalculatorButton.EIGHT -> functions.notImplemented()
            CalculatorButton.NINE -> functions.notImplemented()
            CalculatorButton.MINUS -> functions.notImplemented()
            CalculatorButton.RECALL -> functions.notImplemented()
            CalculatorButton.FOUR -> functions.notImplemented()
            CalculatorButton.FIVE -> functions.notImplemented()
            CalculatorButton.SIX -> functions.notImplemented()
            CalculatorButton.PLUS -> functions.notImplemented()
            CalculatorButton.A_B_C -> functions.notImplemented()
            CalculatorButton.ONE -> functions.notImplemented()
            CalculatorButton.TWO -> functions.notImplemented()
            CalculatorButton.THREE -> functions.notImplemented()
            CalculatorButton.EQUAL -> functions.notImplemented()
            CalculatorButton.BACK -> functions.notImplemented()
            CalculatorButton.ZERO -> functions.notImplemented()
            CalculatorButton.DOT -> functions.notImplemented()
            CalculatorButton.PLUS_MINUS -> functions.notImplemented()
            else -> {
                // do nothing
            }
        }
    }

    private fun buttonThirdFunction(button : CalculatorButton)  {
        display.removeState(CalculatorState.THIRD)

        when (button) {
            CalculatorButton.HYP -> functions.notImplemented()
            CalculatorButton.LOG -> functions.notImplemented()
            CalculatorButton.LN -> functions.notImplemented()
            CalculatorButton.CE_C -> buttonPressedConst()
            CalculatorButton.SIN -> functions.notImplemented()
            CalculatorButton.COS -> functions.notImplemented()
            CalculatorButton.TAN -> functions.notImplemented()
            CalculatorButton.Y_POW_X -> functions.notImplemented()
            CalculatorButton.X_SWAP_Y -> functions.notImplemented()
            CalculatorButton.ONE_DIV_X -> functions.notImplemented()
            CalculatorButton.X_SQUARED -> functions.notImplemented()
            CalculatorButton.SQRT_X -> functions.notImplemented()
            CalculatorButton.DIVIDE -> functions.pi()
            CalculatorButton.SUM_PLUS -> functions.notImplemented()
            CalculatorButton.EE -> functions.notImplemented()
            CalculatorButton.LEFT_PARENTHESES -> functions.notImplemented()
            CalculatorButton.RIGHT_PARENTHESES -> functions.notImplemented()
            CalculatorButton.MULTIPLY -> functions.notImplemented()
            CalculatorButton.STORE -> functions.notImplemented()
            CalculatorButton.SEVEN -> functions.notImplemented()
            CalculatorButton.EIGHT -> functions.notImplemented()
            CalculatorButton.NINE -> functions.notImplemented()
            CalculatorButton.MINUS -> functions.notImplemented()
            CalculatorButton.RECALL -> functions.notImplemented()
            CalculatorButton.FOUR -> functions.notImplemented()
            CalculatorButton.FIVE -> functions.notImplemented()
            CalculatorButton.SIX -> functions.notImplemented()
            CalculatorButton.PLUS -> functions.notImplemented()
            CalculatorButton.A_B_C -> functions.notImplemented()
            CalculatorButton.ONE -> functions.notImplemented()
            CalculatorButton.TWO -> functions.notImplemented()
            CalculatorButton.THREE -> functions.notImplemented()
            CalculatorButton.EQUAL -> functions.notImplemented()
            CalculatorButton.BACK -> functions.notImplemented()
            CalculatorButton.ZERO -> functions.notImplemented()
            CalculatorButton.DOT -> functions.notImplemented()
            CalculatorButton.PLUS_MINUS -> functions.notImplemented()
            else -> {
                // do nothing
            }
        }
    }

    private fun buttonPressedAcOn() {
        isErrorState = false
        constModeWasSet = false

        computation.reset()
        display.reset()
        input.reset()
        output.reset()

        output.printValue(computation.getValue())
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
        display.addState(CalculatorState.CONST)
        constModeWasSet = true
    }

    private fun constantSet(button : CalculatorButton): Boolean {

        try {

            val constant = when (button) {
                CalculatorButton.SIN -> 299792458.0 // speed of light in m/s
                CalculatorButton.COS -> 9.80665 // standard gravity in m/s^2
                CalculatorButton.TAN -> 9.1093837139E-31 // electron mass in kg
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

    private fun onEditInputChanged() {
        val inputValue = display.convertDisplayValueToNumeric()
        computation.setValue(inputValue)
    }

    private fun onPrint() {
        input.endEditMode()
    }

    private fun onResultChanged(value : Double) {
        output.printValue(value)
    }

}



