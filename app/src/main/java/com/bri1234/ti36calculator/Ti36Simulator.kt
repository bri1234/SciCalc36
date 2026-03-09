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
            digitsLarge = digitsLarge.copyOf(),
            decimalPointIndex = decimalPointIndex,
            digitsSmall = digitsSmall.copyOf(),
            displayLabels = displayLabels.toSet(),
        )
    }

    private val digitsLarge = CharArray(11) { ' ' }
    private var decimalPointIndex: Int = -1
    private val digitsSmall =  CharArray(3) { ' ' }

    private val displayLabels: MutableSet<DisplayLabels> = mutableSetOf()

    init {
        buttonPressedAcOn()
    }

    fun buttonPressedAcOn() {
        charArrayOf(' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '0').copyInto(digitsLarge)
        decimalPointIndex = 10

        charArrayOf(' ', ' ', ' ').copyInto(digitsSmall)

        displayLabels.clear()
        displayLabels.add(DisplayLabels.DEG)
    }

    fun buttonPressedThird() {
        displayLabels.remove(DisplayLabels.SECOND)

        if (DisplayLabels.THIRD in displayLabels) {
            displayLabels.remove(DisplayLabels.THIRD)
        } else {
            displayLabels.add(DisplayLabels.THIRD)
        }
    }

    fun buttonPressedHyp() {}
    fun buttonPressedLog() {}
    fun buttonPressedLn() {}
    fun buttonPressedCeC() {}

    fun buttonPressedSecond() {
        displayLabels.remove(DisplayLabels.THIRD)

        if (DisplayLabels.SECOND in displayLabels) {
            displayLabels.remove(DisplayLabels.SECOND)
        } else {
            displayLabels.add(DisplayLabels.SECOND)
        }
    }

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
