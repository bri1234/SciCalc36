package com.bri1234.ti36calculator.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.bri1234.ti36calculator.CalculatorButton
import com.bri1234.ti36calculator.CalculatorDisplayState
import com.bri1234.ti36calculator.Ti36Simulator

/**
 * ViewModel for the TI-36 calculator app.
 * Manages the state of the calculator display and handles button presses.
 */
class CalculatorViewModel(
    val simulator: Ti36Simulator = Ti36Simulator()
) : ViewModel() {
    private val _displayState = mutableStateOf(CalculatorDisplayState())
    val displayState: State<CalculatorDisplayState> = _displayState

    fun onButtonPressed(button: CalculatorButton) {
        when (button) {
            CalculatorButton.AC_ON -> simulator.buttonPressedAcOn()
            CalculatorButton.THIRD -> simulator.buttonPressedThird()
            CalculatorButton.HYP -> simulator.buttonPressedHyp()
            CalculatorButton.LOG -> simulator.buttonPressedLog()
            CalculatorButton.LN -> simulator.buttonPressedLn()
            CalculatorButton.CE_C -> simulator.buttonPressedCeC()
            CalculatorButton.SECOND -> simulator.buttonPressedSecond()
            CalculatorButton.SIN -> simulator.buttonPressedSin()
            CalculatorButton.COS -> simulator.buttonPressedCos()
            CalculatorButton.TAN -> simulator.buttonPressedTan()
            CalculatorButton.Y_POW -> simulator.buttonPressedYPow()
            CalculatorButton.X_SWAP_Y -> simulator.buttonPressedXSwapY()
            CalculatorButton.ONE_DIV_X -> simulator.buttonPressedOneDivX()
            CalculatorButton.X_SQUARED -> simulator.buttonPressedXSquared()
            CalculatorButton.SQRT_X -> simulator.buttonPressedSqrtX()
            CalculatorButton.DIVIDE -> simulator.buttonPressedDivide()
            CalculatorButton.SUM_PLUS -> simulator.buttonPressedSumPlus()
            CalculatorButton.EE -> simulator.buttonPressedEE()
            CalculatorButton.LEFT_PARENTHESES -> simulator.buttonPressedLeftParentheses()
            CalculatorButton.RIGHT_PARENTHESES -> simulator.buttonPressedRightParentheses()
            CalculatorButton.MULTIPLY -> simulator.buttonPressedMultiply()
            CalculatorButton.STORE -> simulator.buttonPressedStore()
            CalculatorButton.SEVEN -> simulator.buttonPressedSeven()
            CalculatorButton.EIGHT -> simulator.buttonPressedEight()
            CalculatorButton.NINE -> simulator.buttonPressedNine()
            CalculatorButton.MINUS -> simulator.buttonPressedMinus()
            CalculatorButton.RECALL -> simulator.buttonPressedRecall()
            CalculatorButton.FOUR -> simulator.buttonPressedFour()
            CalculatorButton.FIVE -> simulator.buttonPressedFive()
            CalculatorButton.SIX -> simulator.buttonPressedSix()
            CalculatorButton.PLUS -> simulator.buttonPressedPlus()
            CalculatorButton.A_B_C -> simulator.buttonPressedABC()
            CalculatorButton.ONE -> simulator.buttonPressedOne()
            CalculatorButton.TWO -> simulator.buttonPressedTwo()
            CalculatorButton.THREE -> simulator.buttonPressedThree()
            CalculatorButton.EQUAL -> simulator.buttonPressedEqual()
            CalculatorButton.BACK -> simulator.buttonPressedBack()
            CalculatorButton.ZERO -> simulator.buttonPressedZero()
            CalculatorButton.DOT -> simulator.buttonPressedDot()
            CalculatorButton.PLUS_MINUS -> simulator.buttonPressedPlusMinus()
        }

        updateDisplayState()
    }

    private fun updateDisplayState() {
        _displayState.value = simulator.getDisplayState()
    }
}