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

import com.bri1234.ti36calculator.contracts.CalculatorStateInterface
import com.bri1234.ti36calculator.utils.factorial as mathUtilsFactorial
import com.bri1234.ti36calculator.utils.getIntFromDouble

import kotlin.math.*

/**
 * This class implements the one parameter functions of the TI-36 calculator.
 */
class Ti36Functions(val labels: CalculatorStateInterface,
                    val computation: Ti36Computation) {

    fun hyp() {
        if (labels.hasState(CalculatorState.HYP))
            labels.removeState(CalculatorState.HYP)
        else if (
            !labels.hasState(CalculatorState.BIN) &&
            !labels.hasState(CalculatorState.OCT) &&
            !labels.hasState(CalculatorState.HEX)
        ) {
            labels.addState(CalculatorState.HYP)
        }
    }

    fun cycleAngleUnit(convert: Boolean) {
        when {
            labels.hasState(CalculatorState.DEG) -> {
                labels.removeState(CalculatorState.DEG)
                labels.addState(CalculatorState.RAD)

                if (convert) {
                    val value = computation.getValue()
                    computation.setValue(value * Math.PI / 180)
                }
            }
            labels.hasState(CalculatorState.RAD) -> {
                labels.removeState(CalculatorState.RAD)
                labels.addState(CalculatorState.GRAD)

                if (convert) {
                    val value = computation.getValue()
                    computation.setValue(value * 200 / Math.PI)
                }
            }
            labels.hasState(CalculatorState.GRAD) -> {
                labels.removeState(CalculatorState.GRAD)
                labels.addState(CalculatorState.DEG)

                if (convert) {
                    val value = computation.getValue()
                    computation.setValue(value * 180 / 200)
                }
            }
        }
    }

    fun pi() {
        computation.setValue(Math.PI)
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
            labels.hasState(CalculatorState.HYP) -> sinh(value)
            labels.hasState(CalculatorState.DEG) -> sin(Math.toRadians(value))
            labels.hasState(CalculatorState.RAD) -> sin(value)
            labels.hasState(CalculatorState.GRAD) -> sin(Math.toRadians(value * 0.9))
            else -> throw IllegalStateException("sin(): No angle unit label found")
        }
        computation.setValue(result)
    }

    fun asin() {
        val value = computation.getValue()

        if (labels.hasState(CalculatorState.HYP)) {
            computation.setValue(asinh(value))
            return
        }

        if ((value < -1.0) || (value > 1.0))
            throw IllegalArgumentException("asin(): Input value must be in the range [-1, 1]")

        val r = asin(value)

        val result = when {
            labels.hasState(CalculatorState.DEG) -> Math.toDegrees(r)
            labels.hasState(CalculatorState.RAD) -> r
            labels.hasState(CalculatorState.GRAD) -> Math.toDegrees(r) / 0.9
            else -> throw IllegalStateException("asin(): No angle unit label found")
        }
        computation.setValue(result)
    }

    fun cos() {
        val value = computation.getValue()
        val result = when {
            labels.hasState(CalculatorState.HYP) -> cosh(value)
            labels.hasState(CalculatorState.DEG) -> cos(Math.toRadians(value))
            labels.hasState(CalculatorState.RAD) -> cos(value)
            labels.hasState(CalculatorState.GRAD) -> cos(Math.toRadians(value * 0.9))
            else -> throw IllegalStateException("cos(): No angle unit label found")
        }
        computation.setValue(result)
    }

    fun acos() {
        val value = computation.getValue()

        if (labels.hasState(CalculatorState.HYP)) {
            if (value < 1.0)
                throw IllegalArgumentException("acos(): Input value must be in the range [1, +inf)]")

            computation.setValue(acosh(value))
            return
        }

        if ((value < -1.0) || (value > 1.0))
            throw IllegalArgumentException("acos(): Input value must be in the range [-1, 1]")

        val r = acos(value)

        val result = when {
            labels.hasState(CalculatorState.DEG) -> Math.toDegrees(r)
            labels.hasState(CalculatorState.RAD) -> r
            labels.hasState(CalculatorState.GRAD) -> Math.toDegrees(r) / 0.9
            else -> throw IllegalStateException("acos(): No angle unit label found")
        }
        computation.setValue(result)
    }

    fun tan() {
        val value = computation.getValue()
        val result = when {
            labels.hasState(CalculatorState.HYP) -> tanh(value)
            labels.hasState(CalculatorState.DEG) -> tan(Math.toRadians(value))
            labels.hasState(CalculatorState.RAD) -> tan(value)
            labels.hasState(CalculatorState.GRAD) -> tan(Math.toRadians(value * 0.9))
            else -> throw IllegalStateException("tan(): No angle unit label found")
        }
        computation.setValue(result)
    }

    fun atan() {
        val value = computation.getValue()

        if (labels.hasState(CalculatorState.HYP)) {
            if ((value <= 1.0) || (value >= 1.0))
                throw IllegalArgumentException("atan(): Input value must be in the range (-1, 1)")

            computation.setValue(atanh(value))
            return
        }

        val r = atan(value)

        val result = when {
            labels.hasState(CalculatorState.DEG) -> Math.toDegrees(r)
            labels.hasState(CalculatorState.RAD) -> r
            labels.hasState(CalculatorState.GRAD) -> Math.toDegrees(r) / 0.9
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
}