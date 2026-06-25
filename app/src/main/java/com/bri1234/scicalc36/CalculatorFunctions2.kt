/*
 * SciCalc 36 - A classic-style scientific calculator inspired by traditional handheld calculator workflows.
 * Copyright (C) 2026 Torsten Brischalle <torsten@brischalle.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */

package com.bri1234.scicalc36

import com.bri1234.scicalc36.enums.CalculatorAngleUnit
import com.bri1234.scicalc36.enums.RectangularPolarView
import com.bri1234.scicalc36.utils.factorial
import com.bri1234.scicalc36.utils.getIntFromDouble
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

/**
 * This class implements the two parameter functions of the SciCalc 36 calculator.
 *
 * @property state The calculator mode state used for angle conversions and coordinate views.
 * @property computation The computation stack that supplies operands and receives results.
 */
class CalculatorFunctions2(val state: CalculatorState,
                           val computation: CalculatorComputation) {

    /** Returns whether angles are currently interpreted as degrees. */
    private fun isAngleDeg() = state.calculatorAngleUnit == CalculatorAngleUnit.DEG

    /** Returns whether angles are currently interpreted as radians. */
    private fun isAngleRad() = state.calculatorAngleUnit == CalculatorAngleUnit.RAD

    /** Returns whether angles are currently interpreted as gradians. */
    private fun isAngleGrad() = state.calculatorAngleUnit == CalculatorAngleUnit.GRAD

    /** Exchanges the current register value with the preceding register value. */
    fun swap() {
        val (first, second) = computation.getTwoValues()
        computation.setTwoValues(second, first)
    }

    /**
     * Calculates the number of combinations `n choose r` from the top two register values.
     *
     * The previous value is interpreted as `n`, the current value as `r`, and the result replaces
     * the current value.
     *
     * @throws IllegalArgumentException If an operand is not an integer.
     * @throws IllegalStateException If either operand is negative or `n < r`.
     */
    fun nCr() {
        val (rd, nd) = computation.getTwoDoubleValues()
        val n = getIntFromDouble(nd)
        val r = getIntFromDouble(rd)

        check(n >= 0 && r >= 0 && n >= r) { "nCr is only defined for non-negative integers with n >= r" }

        val result = factorial(n) / (factorial(r) * factorial(n - r))
        computation.setDoubleValue(result)
    }

    /**
     * Calculates the number of permutations of `r` elements selected from `n` elements.
     *
     * The previous value is interpreted as `n`, the current value as `r`, and the result replaces
     * the current value.
     *
     * @throws IllegalArgumentException If an operand is not an integer.
     * @throws IllegalStateException If either operand is negative or `n < r`.
     */
    fun nPr() {
        val (rd, nd) = computation.getTwoDoubleValues()
        val n = getIntFromDouble(nd)
        val r = getIntFromDouble(rd)

        check(n >= 0 && r >= 0 && n >= r) { "nPr is only defined for non-negative integers with n >= r" }

        val result = factorial(n) / factorial(n - r)
        computation.setDoubleValue(result)
    }

    /**
     * Converts rectangular coordinates `(x, y)` from the top two registers to `(r, phi)`.
     *
     * The previous value is interpreted as `x` and the current value as `y`. The radius becomes
     * the current displayed value, the angle uses the configured angle unit, and the calculator
     * enters the polar-coordinate view.
     *
     * @throws IllegalStateException If no supported angle unit is active.
     */
    fun rectangularToPolar() {
        val (y, x) = computation.getTwoDoubleValues()

        val r = hypot(x, y)
        val rad = atan2(y, x)

        val phi = when {
            isAngleRad() -> rad
            isAngleDeg() -> Math.toDegrees(rad)
            isAngleGrad() -> Math.toDegrees(rad) / 0.9
            else -> throw IllegalStateException("Unknown angle unit")
        }

        computation.setTwoValues(r, phi)
        state.rectangularPolarView = RectangularPolarView.POLAR_R
    }

    /**
     * Converts polar coordinates `(r, phi)` from the top two registers to `(x, y)`.
     *
     * The previous value is interpreted as `r` and the current value as `phi`. The angle uses the
     * configured angle unit, `x` becomes the current displayed value, and the calculator enters
     * the rectangular-coordinate view.
     *
     * @throws IllegalStateException If no supported angle unit is active.
     */
    fun polarToRectangular() {
        val (phi, r) = computation.getTwoDoubleValues()

        val rad = when {
            isAngleRad() -> phi
            isAngleDeg() -> Math.toRadians(phi)
            isAngleGrad() -> Math.toRadians(phi * 0.9)
            else -> throw IllegalStateException("Unknown angle unit")
        }

        val x = r * cos(rad)
        val y = r * sin(rad)

        computation.setTwoValues(x, y)
        state.rectangularPolarView = RectangularPolarView.RECTANGULAR_X
    }
}
