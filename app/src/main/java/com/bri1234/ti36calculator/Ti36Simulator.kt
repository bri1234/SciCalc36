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

    private val stack: ArrayDeque<Double> = ArrayDeque()

    init {
        // viewAll()
        buttonPressedAcOn()
    }

    fun viewAll() {
        stackClear()
        stackPush(0.0)

        charArrayOf('-', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8').copyInto(digitsLarge)
        decimalPointIndex = 1

        charArrayOf('-', '8', '8').copyInto(digitsSmall)

        displayLabels.clear()
        displayLabels.addAll(DisplayLabels.entries)
    }

    fun buttonPressedAcOn() {
        stackClear()
        stackPush(0.0)

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

    fun buttonPressedHyp() {
        when {
            DisplayLabels.SECOND in displayLabels -> {
                // DRG
                displayLabels.remove(DisplayLabels.SECOND)
                cycleAngleUnit(false)
            }
            DisplayLabels.THIRD in displayLabels -> {
                // DRG▶
                displayLabels.remove(DisplayLabels.THIRD)
                cycleAngleUnit(true)
            }
            else -> {
                // HYP
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
        }
    }

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

    private fun stackClear() {
        stack.clear()
        stack.add(0.0)
    }

    private fun stackPush(value: Double) {
        stack.addFirst(value)
    }

    private fun stackPop(): Double {
        return stack.removeFirst()
    }

    private fun cycleAngleUnit(convert: Boolean) {
        when {
            DisplayLabels.DEG in displayLabels -> {
                displayLabels.remove(DisplayLabels.DEG)
                displayLabels.add(DisplayLabels.RAD)

                if (convert) {
                    val value = stack.removeLast()
                    stack.add(value * Math.PI / 180)
                }
            }
            DisplayLabels.RAD in displayLabels -> {
                displayLabels.remove(DisplayLabels.RAD)
                displayLabels.add(DisplayLabels.GRAD)

                if (convert) {
                    val value = stack.removeLast()
                    stack.add(value * 200 / Math.PI)
                }
            }
            DisplayLabels.GRAD in displayLabels -> {
                displayLabels.remove(DisplayLabels.GRAD)
                displayLabels.add(DisplayLabels.DEG)

                if (convert) {
                    val value = stack.removeLast()
                    stack.add(value * 180 / 200)
                }
            }
        }
    }
}
