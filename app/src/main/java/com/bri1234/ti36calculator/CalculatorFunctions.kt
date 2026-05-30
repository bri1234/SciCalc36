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

import com.bri1234.ti36calculator.utils.factorial as mathUtilsFactorial
import com.bri1234.ti36calculator.utils.getIntFromDouble

import kotlin.math.*

/**
 * This class implements the one parameter functions of the TI-36 calculator.
 */
class CalculatorFunctions(val state: CalculatorState,
                          val computation: CalculatorComputation) {

    private val angleEpsilon = 1e-12

    private fun isFunctionHyp() = state.calculatorHypMode == CalculatorHypMode.HYP
    private fun isAngleDeg() = state.calculatorAngleUnit == CalculatorAngleUnit.DEG
    private fun isAngleRad() = state.calculatorAngleUnit == CalculatorAngleUnit.RAD
    private fun isAngleGrad() = state.calculatorAngleUnit == CalculatorAngleUnit.GRAD

    private fun normalizeAngle(value: Double, period: Double) = Math.IEEEremainder(value, period)

    private fun isAngleEqual(value: Double, expected: Double) = abs(value - expected) < angleEpsilon

    private fun sinRad(value: Double): Double {
        val angle = normalizeAngle(value, 2.0 * PI)
        return when {
            isAngleEqual(angle, 0.0) || isAngleEqual(abs(angle), PI) -> 0.0
            isAngleEqual(angle, PI / 2.0) -> 1.0
            isAngleEqual(angle, -PI / 2.0) -> -1.0
            else -> sin(angle)
        }
    }

    private fun sinDeg(value: Double): Double {
        val angle = normalizeAngle(value, 360.0)
        return when {
            isAngleEqual(angle, 0.0) || isAngleEqual(abs(angle), 180.0) -> 0.0
            isAngleEqual(angle, 90.0) -> 1.0
            isAngleEqual(angle, -90.0) -> -1.0
            else -> sin(Math.toRadians(angle))
        }
    }

    private fun sinGrad(value: Double): Double {
        val angle = normalizeAngle(value, 400.0)
        return when {
            isAngleEqual(angle, 0.0) || isAngleEqual(abs(angle), 200.0) -> 0.0
            isAngleEqual(angle, 100.0) -> 1.0
            isAngleEqual(angle, -100.0) -> -1.0
            else -> sin(Math.toRadians(angle * 0.9))
        }
    }

    private fun cosRad(value: Double): Double {
        val angle = normalizeAngle(value, 2.0 * PI)
        return when {
            isAngleEqual(angle, 0.0) -> 1.0
            isAngleEqual(abs(angle), PI / 2.0) -> 0.0
            isAngleEqual(abs(angle), PI) -> -1.0
            else -> cos(angle)
        }
    }

    private fun cosDeg(value: Double): Double {
        val angle = normalizeAngle(value, 360.0)
        return when {
            isAngleEqual(angle, 0.0) -> 1.0
            isAngleEqual(abs(angle), 90.0) -> 0.0
            isAngleEqual(abs(angle), 180.0) -> -1.0
            else -> cos(Math.toRadians(angle))
        }
    }

    private fun cosGrad(value: Double): Double {
        val angle = normalizeAngle(value, 400.0)
        return when {
            isAngleEqual(angle, 0.0) -> 1.0
            isAngleEqual(abs(angle), 100.0) -> 0.0
            isAngleEqual(abs(angle), 200.0) -> -1.0
            else -> cos(Math.toRadians(angle * 0.9))
        }
    }

    private fun tanRad(value: Double): Double {
        val angle = normalizeAngle(value, 2.0 * PI)
        return when {
            isAngleEqual(angle, 0.0) || isAngleEqual(abs(angle), PI) -> 0.0
            isAngleEqual(abs(angle), PI / 2.0) ->
                throw IllegalArgumentException("tan(): Undefined for this angle")
            else -> tan(angle)
        }
    }

    private fun tanDeg(value: Double): Double {
        val angle = normalizeAngle(value, 360.0)
        return when {
            isAngleEqual(angle, 0.0) || isAngleEqual(abs(angle), 180.0) -> 0.0
            isAngleEqual(abs(angle), 90.0) ->
                throw IllegalArgumentException("tan(): Undefined for this angle")
            else -> tan(Math.toRadians(angle))
        }
    }

    private fun tanGrad(value: Double): Double {
        val angle = normalizeAngle(value, 400.0)
        return when {
            isAngleEqual(angle, 0.0) || isAngleEqual(abs(angle), 200.0) -> 0.0
            isAngleEqual(abs(angle), 100.0) ->
                throw IllegalArgumentException("tan(): Undefined for this angle")
            else -> tan(Math.toRadians(angle * 0.9))
        }
    }

    fun log() {
        val value = computation.getValue()
        if (value <= 0.0)
            throw IllegalArgumentException("log(): Input value must be greater than 0")

        val result = log10(value)
        computation.setValue(result)
    }

    fun ln() {
        val value = computation.getValue()
        if (value <= 0.0)
            throw IllegalArgumentException("ln(): Input value must be greater than 0")

        val result = ln(value)
        computation.setValue(result)
    }

    fun sin() {
        val value = computation.getValue()
        val result = when {
            isFunctionHyp() -> sinh(value)
            isAngleDeg() -> sinDeg(value)
            isAngleRad() -> sinRad(value)
            isAngleGrad() -> sinGrad(value)
            else -> throw IllegalStateException("sin(): No angle unit label found")
        }
        computation.setValue(result)
    }

    fun asin() {
        val value = computation.getValue()

        if (isFunctionHyp()) {
            computation.setValue(asinh(value))
            return
        }

        if ((value < -1.0) || (value > 1.0))
            throw IllegalArgumentException("asin(): Input value must be in the range [-1, 1]")

        val r = asin(value)

        val result = when {
            isAngleDeg() -> Math.toDegrees(r)
            isAngleRad() -> r
            isAngleGrad() -> Math.toDegrees(r) / 0.9
            else -> throw IllegalStateException("asin(): No angle unit label found")
        }
        computation.setValue(result)
    }

    fun cos() {
        val value = computation.getValue()
        val result = when {
            isFunctionHyp() -> cosh(value)
            isAngleDeg() -> cosDeg(value)
            isAngleRad() -> cosRad(value)
            isAngleGrad() -> cosGrad(value)
            else -> throw IllegalStateException("cos(): No angle unit label found")
        }
        computation.setValue(result)
    }

    fun acos() {
        val value = computation.getValue()

        if (isFunctionHyp()) {
            if (value < 1.0)
                throw IllegalArgumentException("acos(): Input value must be in the range [1, +inf)]")

            computation.setValue(acosh(value))
            return
        }

        if ((value < -1.0) || (value > 1.0))
            throw IllegalArgumentException("acos(): Input value must be in the range [-1, 1]")

        val r = acos(value)

        val result = when {
            isAngleDeg() -> Math.toDegrees(r)
            isAngleRad() -> r
            isAngleGrad() -> Math.toDegrees(r) / 0.9
            else -> throw IllegalStateException("acos(): No angle unit label found")
        }
        computation.setValue(result)
    }

    fun tan() {
        val value = computation.getValue()
        val result = when {
            isFunctionHyp() -> tanh(value)
            isAngleDeg() -> tanDeg(value)
            isAngleRad() -> tanRad(value)
            isAngleGrad() -> tanGrad(value)
            else -> throw IllegalStateException("tan(): No angle unit label found")
        }
        computation.setValue(result)
    }

    fun atan() {
        val value = computation.getValue()

        if (isFunctionHyp()) {
            if ((value <= -1.0) || (value >= 1.0))
                throw IllegalArgumentException("atan(): Input value must be in the range (-1, 1)")

            computation.setValue(atanh(value))
            return
        }

        val r = atan(value)

        val result = when {
            isAngleDeg() -> Math.toDegrees(r)
            isAngleRad() -> r
            isAngleGrad() -> Math.toDegrees(r) / 0.9
            else -> throw IllegalStateException("atan(): No angle unit label found")
        }
        computation.setValue(result)
    }

    fun oneDivX() {
        val value = computation.getValue()
        if (value == 0.0)
            throw IllegalArgumentException("1 / x: Input value must be not equal 0")

        val result = 1.0 / value
        computation.setValue(result)
    }

    fun xSquared() {
        val value = computation.getValue()
        val result = value * value
        computation.setValue(result)
    }

    fun squareRootX() {
        val value = computation.getValue()
        if (value <= 0.0)
            throw IllegalArgumentException("sqrt(): Input value must be greater than 0")

        val result = sqrt(value)
        computation.setValue(result)
    }

    fun negate() {
        val value = computation.getValue()
        val result = -value
        computation.setValue(result)
    }

    fun thirdRootX() {
        val value = computation.getValue()
        val result = cbrt(value)
        computation.setValue(result)
    }

    fun factorial() {
        val intValue = getIntFromDouble(computation.getValue())

        if ((intValue < 0) || (intValue > 69))
            throw IllegalArgumentException("factorial(): Input value must be in the range [0, 69]")

        val result = mathUtilsFactorial(intValue)
        computation.setValue(result)
    }

    fun exp() {
        val value = computation.getValue()
        val result = exp(value)
        computation.setValue(result)
    }

    fun tenPowX() {
        val value = computation.getValue()
        val result = 10.0.pow(value)
        computation.setValue(result)
    }

    fun bitwiseNot() {
        val value = computation.getValue()
        val result = round(value).toLong().inv()
        computation.setValue(result.toDouble())
    }

    fun convertCmToInch() {
        val value = computation.getValue()
        val result = value / 2.54
        computation.setValue(result)
    }

    fun convertLiterToGallon() {
        val value = computation.getValue()
        val result = value / 3.785411784
        computation.setValue(result)
    }

    fun convertKgToPound() {
        val value = computation.getValue()
        val result = value / 0.45359237
        computation.setValue(result)
    }

    fun convertGramToOunce() {
        val value = computation.getValue()
        val result = value / 28.349523125
        computation.setValue(result)
    }

    fun convertCelsiusToFahrenheit() {
        val value = computation.getValue()
        val result = value * 9.0 / 5.0 + 32.0
        computation.setValue(result)
    }

    fun convertInchToCm() {
        val value = computation.getValue()
        val result = value * 2.54
        computation.setValue(result)
    }

    fun convertGallonToLiter() {
        val value = computation.getValue()
        val result = value * 3.785411784
        computation.setValue(result)
    }

    fun convertPoundToKg() {
        val value = computation.getValue()
        val result = value * 0.45359237
        computation.setValue(result)
    }

    fun convertFahrenheitToCelsius() {
        val value = computation.getValue()
        val result = (value - 32.0) * 5.0 / 9.0
        computation.setValue(result)
    }

    fun convertOunceToGram() {
        val value = computation.getValue()
        val result = value * 28.349523125
        computation.setValue(result)
    }

    fun percent() {
        val value = computation.getValue()
        val op = computation.getLastOperation()

        val result = when (op) {
            Operation.ADDITION,
            Operation.SUBTRACTION -> {
                val valueBase = computation.getPreviousValue()
                value / 100.0 * valueBase
            }
            else -> value / 100.0
        }

        computation.setValue(result)
    }

    fun convertDegreesMinutesSecondsToDecimal() {
        var value = computation.getValue()

        val degrees = floor(value)
        value -= degrees
        value *= 100
        val minutes = floor(value)
        value -= minutes
        value *= 100
        val seconds = value

        val result = degrees + minutes / 60.0 + seconds / 3600.0
        computation.setValue(result)
    }
}
