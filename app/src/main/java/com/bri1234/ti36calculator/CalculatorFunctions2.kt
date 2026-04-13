/*
 * Ti36Calculator - A TI-36 calculator simulator for Android.
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
 * along with this program.  If not, see <http://gnu.org>.
 */

package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.utils.factorial
import com.bri1234.ti36calculator.utils.getIntFromDouble
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

/**
 * This class implements the two parameter functions of the TI-36 calculator.
 */
class CalculatorFunctions2(val state: CalculatorState,
                           val computation: CalculatorComputation) {

    private fun isAngleDeg() = state.calculatorAngleUnit == CalculatorAngleUnit.DEG
    private fun isAngleRad() = state.calculatorAngleUnit == CalculatorAngleUnit.RAD
    private fun isAngleGrad() = state.calculatorAngleUnit == CalculatorAngleUnit.GRAD

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
            isAngleRad() -> rad
            isAngleDeg() -> Math.toDegrees(rad)
            isAngleGrad() -> Math.toDegrees(rad) / 0.9
            else -> throw IllegalStateException("Unknown angle unit")
        }

        computation.setTwoValues(r, phi)
    }

    fun polarToRectangular() {
        val (phi, r) = computation.getTwoValues()

        val rad = when {
            isAngleRad() -> phi
            isAngleDeg() -> Math.toRadians(phi)
            isAngleGrad() -> Math.toRadians(phi * 0.9)
            else -> throw IllegalStateException("Unknown angle unit")
        }

        val x = r * cos(rad)
        val y = r * sin(rad)

        computation.setTwoValues(x, y)
    }
}

