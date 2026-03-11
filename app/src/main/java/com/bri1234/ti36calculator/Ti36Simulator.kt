package com.bri1234.ti36calculator

import java.util.Locale
import kotlin.CharArray
import kotlin.charArrayOf

private const val REGISTER_COUNT = 32

private const val MAX_MANTISSA_DIGITS = 11
private const val MAX_EXPONENT_DIGITS = 3

private enum class Operation {
    NONE,
    ADDITION,
    SUBTRACTION,
    MULTIPLICATION,
    DIVISION,
    X_POW_Y,
    LEFT_PARENTHESES,
    RIGHT_PARENTHESES,
}

/**
 * A simulator class for the TI-36 calculator.
 */
class Ti36Simulator {

    fun getDisplayState(): CalculatorDisplayState {
        // Return a dummy display state for now
        return CalculatorDisplayState(
            digitsLarge = displayMantissa.copyOf(),
            decimalPointIndex = displayDecimalPointIndex,
            digitsSmall = displayExponent.copyOf(),
            displayLabels = displayLabels.toSet(),
        )
    }

    private val displayLabels: MutableSet<DisplayLabels> = mutableSetOf()

    private val displayMantissa  = CharArray(MAX_MANTISSA_DIGITS) { ' ' }
    private var displayDecimalPointIndex: Int = -1
    private val displayExponent =  CharArray(MAX_EXPONENT_DIGITS) { ' ' }
    private var isEditMode: Boolean = false

    private val registerArray: Array<Double> = Array(REGISTER_COUNT) { 0.0 }
    private var registerIndex: Int = 0
    private val operationArray: Array<Operation> = Array(REGISTER_COUNT) { Operation.NONE }
    private var operationIndex: Int = 0

    init {
        buttonPressedAcOn()
        // viewAll()
    }

    fun viewAll() {
        charArrayOf('-', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8').copyInto(displayMantissa)
        displayDecimalPointIndex = 1

        charArrayOf('-', '8', '8').copyInto(displayExponent)
    }

    fun buttonPressed(button: CalculatorButton) {
        when (button) {
            CalculatorButton.AC_ON -> buttonPressedAcOn()
            CalculatorButton.SECOND -> buttonPressedSecond()
            CalculatorButton.THIRD -> buttonPressedThird()

            else -> {
                when {
                    DisplayLabels.SECOND in displayLabels -> buttonSecondFunction(button)
                    DisplayLabels.THIRD in displayLabels -> buttonThirdFunction(button)
                    else -> buttonFirstFunction(button)
                }
            }

        }
    }

    private fun buttonFirstFunction(button : CalculatorButton) {
        when (button) {
            CalculatorButton.HYP -> functionHyp()
            CalculatorButton.LOG -> functionLog()
            CalculatorButton.LN -> functionLn()
            CalculatorButton.CE_C -> functionCeC()
            CalculatorButton.SIN -> functionSin()
            CalculatorButton.COS -> functionCos()
            CalculatorButton.TAN -> functionTan()
            CalculatorButton.Y_POW_X -> functionYPowX()
            CalculatorButton.X_SWAP_Y -> functionXSwapY()
            CalculatorButton.ONE_DIV_X -> functionOneDivX()
            CalculatorButton.X_SQUARED -> functionXSquared()
            CalculatorButton.SQRT_X -> functionSqrtX()
            CalculatorButton.DIVIDE -> functionDivide()
            CalculatorButton.SUM_PLUS -> functionSumPlus()
            CalculatorButton.EE -> functionEE()
            CalculatorButton.LEFT_PARENTHESES -> functionLeftParentheses()
            CalculatorButton.RIGHT_PARENTHESES -> functionRightParentheses()
            CalculatorButton.MULTIPLY -> functionMultiply()
            CalculatorButton.STORE -> functionStore()
            CalculatorButton.SEVEN -> functionSeven()
            CalculatorButton.EIGHT -> functionEight()
            CalculatorButton.NINE -> functionNine()
            CalculatorButton.MINUS -> functionMinus()
            CalculatorButton.RECALL -> functionRecall()
            CalculatorButton.FOUR -> functionFour()
            CalculatorButton.FIVE -> functionFive()
            CalculatorButton.SIX -> functionSix()
            CalculatorButton.PLUS -> functionPlus()
            CalculatorButton.A_B_C -> functionABC()
            CalculatorButton.ONE -> functionOne()
            CalculatorButton.TWO -> functionTwo()
            CalculatorButton.THREE -> functionThree()
            CalculatorButton.EQUAL -> functionEqual()
            CalculatorButton.BACK -> functionBack()
            CalculatorButton.ZERO -> functionZero()
            CalculatorButton.DOT -> functionDot()
            CalculatorButton.PLUS_MINUS -> functionPlusMinus()
            else -> {
                // do nothing
            }
        }
    }

    private fun buttonSecondFunction(button : CalculatorButton) {
        displayLabels.remove(DisplayLabels.SECOND)

        when (button) {
            CalculatorButton.HYP -> functionCycleAngleUnit(false)
            else -> {
                // do nothing
            }
        }
    }

    private fun buttonThirdFunction(button : CalculatorButton) {
        displayLabels.remove(DisplayLabels.THIRD)

        when (button) {
            CalculatorButton.HYP -> functionCycleAngleUnit(true)
            else -> {
                // do nothing
            }
        }
    }

    fun buttonPressedAcOn() {
        registerArray.fill(0.0)
        registerIndex = 0
        operationArray.fill(Operation.NONE)
        operationIndex = 0

        displayLabels.clear()
        displayLabels.add(DisplayLabels.DEG)

        printCurrentValue()
    }

    fun buttonPressedThird() {
        displayLabels.remove(DisplayLabels.SECOND)

        if (DisplayLabels.THIRD in displayLabels) {
            displayLabels.remove(DisplayLabels.THIRD)
        } else {
            displayLabels.add(DisplayLabels.THIRD)
        }
    }

    fun buttonPressedSecond() {
        displayLabels.remove(DisplayLabels.THIRD)

        if (DisplayLabels.SECOND in displayLabels) {
            displayLabels.remove(DisplayLabels.SECOND)
        } else {
            displayLabels.add(DisplayLabels.SECOND)
        }
    }

    private fun getValue() : Double {
        return registerArray[registerIndex]
    }

    private fun setValue(newValue: Double) {
        registerArray[registerIndex] = newValue
        printCurrentValue()
    }

    private fun printCurrentValue() {
        val value = getValue()

        if ((value > -10E10) && (value < 10E10)) {
            val valueStr = String.format(Locale.US, "%f", value)

            return
        }

        charArrayOf(' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '0').copyInto(displayMantissa)
        displayDecimalPointIndex = 10

        charArrayOf(' ', ' ', ' ').copyInto(displayExponent)
    }

    private fun functionHyp() {
        if (DisplayLabels.HYP in displayLabels)
            displayLabels.remove(DisplayLabels.HYP)
        else if (
            DisplayLabels.BIN !in displayLabels &&
            DisplayLabels.OCT !in displayLabels &&
            DisplayLabels.HEX !in displayLabels
        ) {
            displayLabels.add(DisplayLabels.HYP)
        }
    }

    private fun functionCycleAngleUnit(convert: Boolean) {
        when {
            DisplayLabels.DEG in displayLabels -> {
                displayLabels.remove(DisplayLabels.DEG)
                displayLabels.add(DisplayLabels.RAD)

                if (convert) {
//                    val value = stack.removeLast()
//                    stack.add(value * Math.PI / 180)
                }
            }
            DisplayLabels.RAD in displayLabels -> {
                displayLabels.remove(DisplayLabels.RAD)
                displayLabels.add(DisplayLabels.GRAD)

                if (convert) {
//                    val value = stack.removeLast()
//                    stack.add(value * 200 / Math.PI)
                }
            }
            DisplayLabels.GRAD in displayLabels -> {
                displayLabels.remove(DisplayLabels.GRAD)
                displayLabels.add(DisplayLabels.DEG)

                if (convert) {
//                    val value = stack.removeLast()
//                    stack.add(value * 180 / 200)
                }
            }
        }
    }

    private fun functionLog() {}
    private fun functionLn() {}
    private fun functionCeC() {}

    private fun functionSin() {}
    private fun functionCos() {}
    private fun functionTan() {}
    private fun functionYPowX() {}
    private fun functionXSwapY() {}
    private fun functionOneDivX() {}
    private fun functionXSquared() {}
    private fun functionSqrtX() {}
    private fun functionDivide() {}
    private fun functionSumPlus() {}
    private fun functionEE() {}
    private fun functionLeftParentheses() {}
    private fun functionRightParentheses() {}
    private fun functionMultiply() {}
    private fun functionStore() {}
    private fun functionSeven() {}
    private fun functionEight() {}
    private fun functionNine() {}
    private fun functionMinus() {}
    private fun functionRecall() {}
    private fun functionFour() {}
    private fun functionFive() {}
    private fun functionSix() {}
    private fun functionPlus() {}
    private fun functionABC() {}
    private fun functionOne() {}
    private fun functionTwo() {}
    private fun functionThree() {}
    private fun functionEqual() {}
    private fun functionBack() {}
    private fun functionZero() {}
    private fun functionDot() {}
    private fun functionPlusMinus() {}

}



