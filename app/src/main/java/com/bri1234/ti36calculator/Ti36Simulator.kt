package com.bri1234.ti36calculator

import kotlin.CharArray
import kotlin.charArrayOf

/**
 * A simulator class for the TI-36 calculator.
 */
class Ti36Simulator {

    fun getDisplayState(): CalculatorDisplayState {
        // Return a dummy display state for now
        return CalculatorDisplayState(
            digitsLarge = charArrayOf(' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '0'),
            decimalPointIndex = 10,
            digitsSmall = charArrayOf(' ', ' ', ' '),
            displayLabels = mutableSetOf(DisplayLabels.DEG),
        )
    }

    fun buttonPressedAcOn() {}
    fun buttonPressedThird() {}
    fun buttonPressedHyp() {}
    fun buttonPressedLog() {}
    fun buttonPressedLn() {}
    fun buttonPressedCeC() {}
    fun buttonPressedSecond() {}
    fun buttonPressedSin() {}
    fun buttonPressedCos() {}
    fun buttonPressedTan() {}
    fun buttonPressedYPow() {}
    fun buttonPressedXSwapY() {}
    fun buttonPressedOneDivX() {}
    fun buttonPressedXSquared() {}
    fun buttonPressedSqrtX() {}
    fun buttonPressedDivide() {}
    fun buttonPressedSumPlus() {}
    fun buttonPressedEE() {}
    fun buttonPressedLeftParentheses() {}
    fun buttonPressedRightParentheses() {}
    fun buttonPressedMultiply() {}
    fun buttonPressedStore() {}
    fun buttonPressedSeven() {}
    fun buttonPressedEight() {}
    fun buttonPressedNine() {}
    fun buttonPressedMinus() {}
    fun buttonPressedRecall() {}
    fun buttonPressedFour() {}
    fun buttonPressedFive() {}
    fun buttonPressedSix() {}
    fun buttonPressedPlus() {}
    fun buttonPressedABC() {}
    fun buttonPressedOne() {}
    fun buttonPressedTwo() {}
    fun buttonPressedThree() {}
    fun buttonPressedEqual() {}
    fun buttonPressedBack() {}
    fun buttonPressedZero() {}
    fun buttonPressedDot() {}
    fun buttonPressedPlusMinus() {}
}
