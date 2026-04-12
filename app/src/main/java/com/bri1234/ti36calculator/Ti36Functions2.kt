package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.contracts.CalculatorStateInterface
import com.bri1234.ti36calculator.utils.factorial
import com.bri1234.ti36calculator.utils.getIntFromDouble
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

/**
 * This class implements the two parameter functions of the TI-36 calculator.
 */
class Ti36Functions2(val labels: CalculatorStateInterface,
                     val computation: Ti36Computation) {

    fun swap() {
        val (first, second) = computation.getTwoValues()
        computation.setTwoValues(second, first)
    }

    fun nCr() {
        val (rd, nd) = computation.getTwoValues()
        val n = getIntFromDouble(nd)
        val r = getIntFromDouble(rd)

        check(n >= 0 && r >= 0 && n >= r) { "nCr is only defined for non-negative integers with n >= r" }

        val result = factorial(n) / (factorial(r) * factorial(n - r))
        computation.setValue(result)
    }

    fun nPr() {
        val (rd, nd) = computation.getTwoValues()
        val n = getIntFromDouble(nd)
        val r = getIntFromDouble(rd)

        check(n >= 0 && r >= 0 && n >= r) { "nPr is only defined for non-negative integers with n >= r" }

        val result = factorial(n) / factorial(n - r)
        computation.setValue(result)
    }

    fun rectangularToPolar() {
        val (y, x) = computation.getTwoValues()

        val r = hypot(x, y)
        val rad = atan2(y, x)

        val phi = when {
            labels.hasState(CalculatorState.RAD) -> rad
            labels.hasState(CalculatorState.DEG) -> Math.toDegrees(rad)
            labels.hasState(CalculatorState.GRAD) -> Math.toDegrees(rad) / 0.9
            else -> throw IllegalStateException("Unknown angle unit")
        }

        computation.setTwoValues(r, phi)
    }

    fun polarToRectangular() {
        val (phi, r) = computation.getTwoValues()

        val rad = when {
            labels.hasState(CalculatorState.RAD) -> phi
            labels.hasState(CalculatorState.DEG) -> Math.toRadians(phi)
            labels.hasState(CalculatorState.GRAD) -> Math.toRadians(phi * 0.9)
            else -> throw IllegalStateException("Unknown angle unit")
        }

        val x = r * cos(rad)
        val y = r * sin(rad)

        computation.setTwoValues(x, y)
    }
}

