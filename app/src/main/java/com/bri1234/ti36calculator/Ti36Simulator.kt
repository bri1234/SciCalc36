package com.bri1234.ti36calculator

/**
 * A simulator class for the TI-36 calculator.
 */
class Ti36Simulator {

    private val inputOutput = Ti36InputOutput()
    private val functions = Ti36Functions(inputOutput)
    private val computation = Ti36Computation()

    fun getDisplayState(): CalculatorDisplayState = inputOutput.getDisplayState()

    init {
        buttonPressedAcOn()
        // viewAll()
    }

    fun buttonPressed(button: CalculatorButton) {
        when (button) {
            CalculatorButton.AC_ON -> buttonPressedAcOn()
            CalculatorButton.SECOND -> buttonPressedSecond()
            CalculatorButton.THIRD -> buttonPressedThird()

            else -> {
                when {
                    inputOutput.isSecondFunctionActive() -> buttonSecondFunction(button)
                    inputOutput.isThirdFunctionActive() -> buttonThirdFunction(button)
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
            CalculatorButton.EE -> functions.eE()
            CalculatorButton.LEFT_PARENTHESES -> functions.leftParentheses()
            CalculatorButton.RIGHT_PARENTHESES -> functions.rightParentheses()
            CalculatorButton.MULTIPLY -> functions.multiply()
            CalculatorButton.STORE -> functions.store()
            CalculatorButton.SEVEN -> functions.seven()
            CalculatorButton.EIGHT -> functions.eight()
            CalculatorButton.NINE -> functions.nine()
            CalculatorButton.MINUS -> functions.minus()
            CalculatorButton.RECALL -> functions.recall()
            CalculatorButton.FOUR -> functions.four()
            CalculatorButton.FIVE -> functions.five()
            CalculatorButton.SIX -> functions.six()
            CalculatorButton.PLUS -> functions.plus()
            CalculatorButton.A_B_C -> functions.aBC()
            CalculatorButton.ONE -> functions.one()
            CalculatorButton.TWO -> functions.two()
            CalculatorButton.THREE -> functions.three()
            CalculatorButton.EQUAL -> functions.equal()
            CalculatorButton.BACK -> functions.back()
            CalculatorButton.ZERO -> functions.zero()
            CalculatorButton.DOT -> functions.dot()
            CalculatorButton.PLUS_MINUS -> functions.plusMinus()
            else -> {
                // do nothing
            }
        }
    }

    private fun buttonSecondFunction(button : CalculatorButton) {
        inputOutput.removeLabel(DisplayLabels.SECOND)

        when (button) {
            CalculatorButton.HYP -> functions.cycleAngleUnit(false)
            else -> {
                // do nothing
            }
        }
    }

    private fun buttonThirdFunction(button : CalculatorButton) {
        inputOutput.removeLabel(DisplayLabels.THIRD)

        when (button) {
            CalculatorButton.HYP -> functions.cycleAngleUnit(true)
            else -> {
                // do nothing
            }
        }
    }

    fun buttonPressedAcOn() {
        computation.reset()
        inputOutput.reset()
        inputOutput.printValue(computation.getValue())
    }

    fun buttonPressedThird() {
        inputOutput.removeLabel(DisplayLabels.SECOND)

        if (inputOutput.hasLabel(DisplayLabels.THIRD)) {
            inputOutput.removeLabel(DisplayLabels.THIRD)
        } else {
            inputOutput.addLabel(DisplayLabels.THIRD)
        }
    }

    fun buttonPressedSecond() {
        inputOutput.removeLabel(DisplayLabels.THIRD)

        if (inputOutput.hasLabel(DisplayLabels.SECOND)) {
            inputOutput.removeLabel(DisplayLabels.SECOND)
        } else {
            inputOutput.addLabel(DisplayLabels.SECOND)
        }
    }


}



