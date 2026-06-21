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

import com.bri1234.ti36calculator.enums.CalculatorAngleUnit
import com.bri1234.ti36calculator.enums.CalculatorHypMode
import com.bri1234.ti36calculator.enums.Operation
import com.bri1234.ti36calculator.utils.factorial as mathUtilsFactorial
import com.bri1234.ti36calculator.utils.getIntFromDouble

import kotlin.math.*

/**
 * This class implements the one parameter functions of the TI-36 calculator.
 *
 * @property state The calculator mode state used to select angle and hyperbolic behavior.
 * @property computation The computation stack whose current value is read and replaced.
 */
class CalculatorFunctions(val state: CalculatorState,
                          val computation: CalculatorComputation) {

    private val angleEpsilon = 1e-12

    /** Returns whether hyperbolic function mode is active. */
    private fun isFunctionHyp() = state.calculatorHypMode == CalculatorHypMode.HYP

    /** Returns whether angles are currently interpreted as degrees. */
    private fun isAngleDeg() = state.calculatorAngleUnit == CalculatorAngleUnit.DEG

    /** Returns whether angles are currently interpreted as radians. */
    private fun isAngleRad() = state.calculatorAngleUnit == CalculatorAngleUnit.RAD

    /** Returns whether angles are currently interpreted as gradians. */
    private fun isAngleGrad() = state.calculatorAngleUnit == CalculatorAngleUnit.GRAD

    /**
     * Reduces [value] to the symmetric interval around zero for the supplied [period].
     *
     * @param value The angle to normalize.
     * @param period The full period of the angle unit.
     * @return The IEEE remainder of [value] divided by [period].
     */
    private fun normalizeAngle(value: Double, period: Double) = Math.IEEEremainder(value, period)

    /** Returns whether [value] is equal to [expected] within the angle tolerance. */
    private fun isAngleEqual(value: Double, expected: Double) = abs(value - expected) < angleEpsilon

    /** Calculates sine for a radian angle, returning exact values at quadrant boundaries. */
    private fun sinRad(value: Double): Double {
        val angle = normalizeAngle(value, 2.0 * PI)
        return when {
            isAngleEqual(angle, 0.0) || isAngleEqual(abs(angle), PI) -> 0.0
            isAngleEqual(angle, PI / 2.0) -> 1.0
            isAngleEqual(angle, -PI / 2.0) -> -1.0
            else -> sin(angle)
        }
    }

    /** Calculates sine for a degree angle, returning exact values at quadrant boundaries. */
    private fun sinDeg(value: Double): Double {
        val angle = normalizeAngle(value, 360.0)
        return when {
            isAngleEqual(angle, 0.0) || isAngleEqual(abs(angle), 180.0) -> 0.0
            isAngleEqual(angle, 90.0) -> 1.0
            isAngleEqual(angle, -90.0) -> -1.0
            else -> sin(Math.toRadians(angle))
        }
    }

    /** Calculates sine for a gradian angle, returning exact values at quadrant boundaries. */
    private fun sinGrad(value: Double): Double {
        val angle = normalizeAngle(value, 400.0)
        return when {
            isAngleEqual(angle, 0.0) || isAngleEqual(abs(angle), 200.0) -> 0.0
            isAngleEqual(angle, 100.0) -> 1.0
            isAngleEqual(angle, -100.0) -> -1.0
            else -> sin(Math.toRadians(angle * 0.9))
        }
    }

    /** Calculates cosine for a radian angle, returning exact values at quadrant boundaries. */
    private fun cosRad(value: Double): Double {
        val angle = normalizeAngle(value, 2.0 * PI)
        return when {
            isAngleEqual(angle, 0.0) -> 1.0
            isAngleEqual(abs(angle), PI / 2.0) -> 0.0
            isAngleEqual(abs(angle), PI) -> -1.0
            else -> cos(angle)
        }
    }

    /** Calculates cosine for a degree angle, returning exact values at quadrant boundaries. */
    private fun cosDeg(value: Double): Double {
        val angle = normalizeAngle(value, 360.0)
        return when {
            isAngleEqual(angle, 0.0) -> 1.0
            isAngleEqual(abs(angle), 90.0) -> 0.0
            isAngleEqual(abs(angle), 180.0) -> -1.0
            else -> cos(Math.toRadians(angle))
        }
    }

    /** Calculates cosine for a gradian angle, returning exact values at quadrant boundaries. */
    private fun cosGrad(value: Double): Double {
        val angle = normalizeAngle(value, 400.0)
        return when {
            isAngleEqual(angle, 0.0) -> 1.0
            isAngleEqual(abs(angle), 100.0) -> 0.0
            isAngleEqual(abs(angle), 200.0) -> -1.0
            else -> cos(Math.toRadians(angle * 0.9))
        }
    }

    /**
     * Calculates tangent for a radian angle, returning exact zeroes at half-turn boundaries.
     *
     * @throws IllegalArgumentException If tangent is undefined for the normalized angle.
     */
    private fun tanRad(value: Double): Double {
        val angle = normalizeAngle(value, 2.0 * PI)
        return when {
            isAngleEqual(angle, 0.0) || isAngleEqual(abs(angle), PI) -> 0.0
            isAngleEqual(abs(angle), PI / 2.0) ->
                throw IllegalArgumentException("tan(): Undefined for this angle")
            else -> tan(angle)
        }
    }

    /**
     * Calculates tangent for a degree angle, returning exact zeroes at half-turn boundaries.
     *
     * @throws IllegalArgumentException If tangent is undefined for the normalized angle.
     */
    private fun tanDeg(value: Double): Double {
        val angle = normalizeAngle(value, 360.0)
        return when {
            isAngleEqual(angle, 0.0) || isAngleEqual(abs(angle), 180.0) -> 0.0
            isAngleEqual(abs(angle), 90.0) ->
                throw IllegalArgumentException("tan(): Undefined for this angle")
            else -> tan(Math.toRadians(angle))
        }
    }

    /**
     * Calculates tangent for a gradian angle, returning exact zeroes at half-turn boundaries.
     *
     * @throws IllegalArgumentException If tangent is undefined for the normalized angle.
     */
    private fun tanGrad(value: Double): Double {
        val angle = normalizeAngle(value, 400.0)
        return when {
            isAngleEqual(angle, 0.0) || isAngleEqual(abs(angle), 200.0) -> 0.0
            isAngleEqual(abs(angle), 100.0) ->
                throw IllegalArgumentException("tan(): Undefined for this angle")
            else -> tan(Math.toRadians(angle * 0.9))
        }
    }

    /**
     * Replaces the current value with its base-10 logarithm.
     *
     * @throws IllegalArgumentException If the current value is not greater than zero.
     */
    fun log() {
        val value = computation.getDoubleValue()
        if (value <= 0.0)
            throw IllegalArgumentException("log(): Input value must be greater than 0")

        val result = log10(value)
        computation.setDoubleValue(result)
    }

    /**
     * Replaces the current value with its natural logarithm.
     *
     * @throws IllegalArgumentException If the current value is not greater than zero.
     */
    fun ln() {
        val value = computation.getDoubleValue()
        if (value <= 0.0)
            throw IllegalArgumentException("ln(): Input value must be greater than 0")

        val result = ln(value)
        computation.setDoubleValue(result)
    }

    /**
     * Replaces the current value with its sine or hyperbolic sine.
     *
     * Normal sine uses the configured angle unit; hyperbolic mode ignores the angle unit.
     *
     * @throws IllegalStateException If no supported angle unit is active.
     */
    fun sin() {
        val value = computation.getDoubleValue()
        val result = when {
            isFunctionHyp() -> sinh(value)
            isAngleDeg() -> sinDeg(value)
            isAngleRad() -> sinRad(value)
            isAngleGrad() -> sinGrad(value)
            else -> throw IllegalStateException("sin(): No angle unit label found")
        }
        computation.setDoubleValue(result)
    }

    /**
     * Replaces the current value with its inverse sine or inverse hyperbolic sine.
     *
     * The inverse sine result is converted to the configured angle unit.
     *
     * @throws IllegalArgumentException If inverse sine is requested outside `[-1, 1]`.
     * @throws IllegalStateException If no supported angle unit is active.
     */
    fun asin() {
        val value = computation.getDoubleValue()

        if (isFunctionHyp()) {
            computation.setDoubleValue(asinh(value))
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
        computation.setDoubleValue(result)
    }

    /**
     * Replaces the current value with its cosine or hyperbolic cosine.
     *
     * Normal cosine uses the configured angle unit; hyperbolic mode ignores the angle unit.
     *
     * @throws IllegalStateException If no supported angle unit is active.
     */
    fun cos() {
        val value = computation.getDoubleValue()
        val result = when {
            isFunctionHyp() -> cosh(value)
            isAngleDeg() -> cosDeg(value)
            isAngleRad() -> cosRad(value)
            isAngleGrad() -> cosGrad(value)
            else -> throw IllegalStateException("cos(): No angle unit label found")
        }
        computation.setDoubleValue(result)
    }

    /**
     * Replaces the current value with its inverse cosine or inverse hyperbolic cosine.
     *
     * The inverse cosine result is converted to the configured angle unit.
     *
     * @throws IllegalArgumentException If the value is outside the selected function's domain.
     * @throws IllegalStateException If no supported angle unit is active.
     */
    fun acos() {
        val value = computation.getDoubleValue()

        if (isFunctionHyp()) {
            if (value < 1.0)
                throw IllegalArgumentException("acos(): Input value must be in the range [1, +inf)]")

            computation.setDoubleValue(acosh(value))
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
        computation.setDoubleValue(result)
    }

    /**
     * Replaces the current value with its tangent or hyperbolic tangent.
     *
     * Normal tangent uses the configured angle unit; hyperbolic mode ignores the angle unit.
     *
     * @throws IllegalArgumentException If tangent is undefined for the current angle.
     * @throws IllegalStateException If no supported angle unit is active.
     */
    fun tan() {
        val value = computation.getDoubleValue()
        val result = when {
            isFunctionHyp() -> tanh(value)
            isAngleDeg() -> tanDeg(value)
            isAngleRad() -> tanRad(value)
            isAngleGrad() -> tanGrad(value)
            else -> throw IllegalStateException("tan(): No angle unit label found")
        }
        computation.setDoubleValue(result)
    }

    /**
     * Replaces the current value with its inverse tangent or inverse hyperbolic tangent.
     *
     * The inverse tangent result is converted to the configured angle unit.
     *
     * @throws IllegalArgumentException If inverse hyperbolic tangent is requested outside
     * `(-1, 1)`.
     * @throws IllegalStateException If no supported angle unit is active.
     */
    fun atan() {
        val value = computation.getDoubleValue()

        if (isFunctionHyp()) {
            if ((value <= -1.0) || (value >= 1.0))
                throw IllegalArgumentException("atan(): Input value must be in the range (-1, 1)")

            computation.setDoubleValue(atanh(value))
            return
        }

        val r = atan(value)

        val result = when {
            isAngleDeg() -> Math.toDegrees(r)
            isAngleRad() -> r
            isAngleGrad() -> Math.toDegrees(r) / 0.9
            else -> throw IllegalStateException("atan(): No angle unit label found")
        }
        computation.setDoubleValue(result)
    }

    /**
     * Replaces the current value with its reciprocal.
     *
     * @throws IllegalArgumentException If the current value is zero.
     */
    fun oneDivX() {
        val value = computation.getValue().clone()
        value.reciprocal()
        computation.setValue(value)
    }

    /** Replaces the current value with its square. */
    fun xSquared() {
        val value = computation.getValue().clone()
        value.sqr()
        computation.setValue(value)
    }

    /**
     * Replaces the current value with its square root.
     *
     * @throws IllegalArgumentException If the current value is not greater than zero.
     */
    fun squareRootX() {
        val value = computation.getDoubleValue()
        if (value <= 0.0)
            throw IllegalArgumentException("sqrt(): Input value must be greater than 0")

        val result = sqrt(value)
        computation.setDoubleValue(result)
    }

    /** Replaces the current value with its arithmetic negation. */
    fun negate() {
        val value = computation.getValue().clone()
        value.negate()
        computation.setValue(value)
    }

    /** Replaces the current value with its real cube root. */
    fun thirdRootX() {
        val value = computation.getDoubleValue()
        val result = cbrt(value)
        computation.setDoubleValue(result)
    }

    /**
     * Replaces the current value with its factorial.
     *
     * @throws IllegalArgumentException If the value is not an integer or is outside `[0, 69]`.
     */
    fun factorial() {
        val intValue = getIntFromDouble(computation.getDoubleValue())

        if ((intValue < 0) || (intValue > 69))
            throw IllegalArgumentException("factorial(): Input value must be in the range [0, 69]")

        val result = mathUtilsFactorial(intValue)
        computation.setDoubleValue(result)
    }

    /** Replaces the current value `x` with `e^x`. */
    fun exp() {
        val value = computation.getDoubleValue()
        val result = exp(value)
        computation.setDoubleValue(result)
    }

    /** Replaces the current value `x` with `10^x`. */
    fun tenPowX() {
        val value = computation.getDoubleValue()
        val result = 10.0.pow(value)
        computation.setDoubleValue(result)
    }

    /**
     * Rounds the current value to a [Long], applies bitwise inversion, and stores the result.
     */
    fun bitwiseNot() {
        val value = computation.getDoubleValue()
        val result = round(value).toLong().inv()
        computation.setDoubleValue(result.toDouble())
    }

    /** Converts the current length from centimeters to inches. */
    fun convertCmToInch() {
        val value = computation.getDoubleValue()
        val result = value / 2.54
        computation.setDoubleValue(result)
    }

    /** Converts the current volume from liters to US gallons. */
    fun convertLiterToGallon() {
        val value = computation.getDoubleValue()
        val result = value / 3.785411784
        computation.setDoubleValue(result)
    }

    /** Converts the current mass from kilograms to pounds. */
    fun convertKgToPound() {
        val value = computation.getDoubleValue()
        val result = value / 0.45359237
        computation.setDoubleValue(result)
    }

    /** Converts the current mass from grams to ounces. */
    fun convertGramToOunce() {
        val value = computation.getDoubleValue()
        val result = value / 28.349523125
        computation.setDoubleValue(result)
    }

    /** Converts the current temperature from degrees Celsius to degrees Fahrenheit. */
    fun convertCelsiusToFahrenheit() {
        val value = computation.getDoubleValue()
        val result = value * 9.0 / 5.0 + 32.0
        computation.setDoubleValue(result)
    }

    /** Converts the current length from inches to centimeters. */
    fun convertInchToCm() {
        val value = computation.getDoubleValue()
        val result = value * 2.54
        computation.setDoubleValue(result)
    }

    /** Converts the current volume from US gallons to liters. */
    fun convertGallonToLiter() {
        val value = computation.getDoubleValue()
        val result = value * 3.785411784
        computation.setDoubleValue(result)
    }

    /** Converts the current mass from pounds to kilograms. */
    fun convertPoundToKg() {
        val value = computation.getDoubleValue()
        val result = value * 0.45359237
        computation.setDoubleValue(result)
    }

    /** Converts the current temperature from degrees Fahrenheit to degrees Celsius. */
    fun convertFahrenheitToCelsius() {
        val value = computation.getDoubleValue()
        val result = (value - 32.0) * 5.0 / 9.0
        computation.setDoubleValue(result)
    }

    /** Converts the current mass from ounces to grams. */
    fun convertOunceToGram() {
        val value = computation.getDoubleValue()
        val result = value * 28.349523125
        computation.setDoubleValue(result)
    }

    /**
     * Replaces the current value with its percentage interpretation.
     *
     * For pending addition or subtraction, the percentage is relative to the previous operand.
     * For all other operations, the current value is divided by 100.
     */
    fun percent() {
        val value = computation.getDoubleValue()
        val op = computation.getLastOperation()

        val result = when (op) {
            Operation.ADDITION,
            Operation.SUBTRACTION -> {
                val valueBase = computation.getPreviousValue()
                value / 100.0 * valueBase
            }
            else -> value / 100.0
        }

        computation.setDoubleValue(result)
    }

    /**
     * Converts a packed `degrees.minutesSeconds` value to decimal degrees.
     *
     * The first two digits after the decimal separator are interpreted as minutes and the next
     * two digits as seconds.
     */
    fun convertDegreesMinutesSecondsToDecimal() {
        var value = computation.getDoubleValue()

        val degrees = floor(value)
        value -= degrees
        value *= 100
        val minutes = floor(value)
        value -= minutes
        value *= 100
        val seconds = value

        val result = degrees + minutes / 60.0 + seconds / 3600.0
        computation.setDoubleValue(result)
    }
}
