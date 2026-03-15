package com.bri1234.ti36calculator

/**
 * A simulator class for the TI-36 calculator.
 */
class Ti36Simulator {

    private val display = Ti36Display()
    private val input = Ti36Input(display)
    private val output = Ti36Output(display)
    private val computation = Ti36Computation()
    private val functions = Ti36Functions(display, computation)

    fun getDisplayState(): CalculatorDisplayState = display.getDisplayState()

    init {
        buttonPressedAcOn()
        // viewAll()

        input.onEditInputChanged += { onEditInputChanged() }
        output.onPrint += { onPrint() }
        computation.onResultChanged += { value -> onResultChanged(value) }
    }

    fun buttonPressed(button: CalculatorButton) {
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
    }

    private fun buttonFirstFunction(button : CalculatorButton) {
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
        display.removeLabel(DisplayLabels.SECOND)

        when (button) {
            CalculatorButton.HYP -> functions.cycleAngleUnit(false)
            else -> {
                // do nothing
            }
        }
    }

    private fun buttonThirdFunction(button : CalculatorButton) {
        display.removeLabel(DisplayLabels.THIRD)

        when (button) {
            CalculatorButton.HYP -> functions.cycleAngleUnit(true)
            else -> {
                // do nothing
            }
        }
    }

    private fun buttonPressedAcOn() {
        computation.reset()
        display.reset()
        input.reset()
        output.reset()

        output.printValue(computation.getValue())
    }

    private fun buttonPressedThird() {
        display.removeLabel(DisplayLabels.SECOND)

        if (display.hasLabel(DisplayLabels.THIRD)) {
            display.removeLabel(DisplayLabels.THIRD)
        } else {
            display.addLabel(DisplayLabels.THIRD)
        }
    }

    private fun buttonPressedSecond() {
        display.removeLabel(DisplayLabels.THIRD)

        if (display.hasLabel(DisplayLabels.SECOND)) {
            display.removeLabel(DisplayLabels.SECOND)
        } else {
            display.addLabel(DisplayLabels.SECOND)
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



