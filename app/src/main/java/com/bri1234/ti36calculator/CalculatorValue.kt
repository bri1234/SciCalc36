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
 * along with this program.  If not, see <http://gnu.org/licenses/>.
 */

package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.enums.Presentation

/** Default display format assigned when an operation produces a fraction. */
private val DEFAULT_FRACTION_PRESENTATION = Presentation.FRACTION_MIXED

/**
 * Mutable numeric value used by calculator registers and operations.
 *
 * A value is stored either as a [Double] or as an exact [Fraction]. Arithmetic stays fractional
 * when both operands are fractions, or when a fraction is combined with a decimal value that can
 * be represented as an [Int]. All other mixed operations produce a decimal value.
 */
class CalculatorValue() {

    private var _valueDouble : Double = 0.0
    private var _valueFraction : Fraction = Fraction(0)
    private var _presentation : Presentation = Presentation.DECIMAL

    /**
     * Creates a calculator value represented as a decimal number.
     *
     * @param value The decimal value to store.
     */
    constructor(value: Double) : this() {
        _valueDouble = value
        _presentation = Presentation.DECIMAL
    }

    /**
     * Creates a calculator value represented as an exact mixed fraction.
     *
     * @param value The fraction to store.
     */
    constructor(value: Fraction) : this() {
        _valueFraction = value
        _presentation = DEFAULT_FRACTION_PRESENTATION
    }

    /**
     * Creates an independent copy of this calculator value.
     *
     * The decimal value, exact fraction, and presentation mode are copied. [Fraction] is immutable,
     * so its instance can be shared safely between the two [CalculatorValue] objects. Subsequent
     * mutations of either calculator value do not affect the other.
     *
     * @return A new [CalculatorValue] with the same value and presentation.
     */
    fun copy(): CalculatorValue {
        val result = CalculatorValue()
        result._valueDouble = _valueDouble
        result._valueFraction = _valueFraction
        result._presentation = _presentation
        return result
    }

    fun copy(other: CalculatorValue) {
        _valueDouble = other._valueDouble
        _valueFraction = other._valueFraction
        _presentation = other._presentation
    }

    /**
     * Returns whether the current calculator value represents an integer.
     *
     * Decimal values must be finite and have no fractional part. Fraction values are checked using
     * [Fraction.isInteger].
     */
    val isInteger: Boolean get() = if (_presentation == Presentation.DECIMAL) {
            _valueDouble.isFinite() && _valueDouble % 1.0 == 0.0
                    && _valueDouble <= Int.MAX_VALUE && _valueDouble >= Int.MIN_VALUE
        } else {
            _valueFraction.isInteger
        }

    /** Returns whether the current value is stored and presented as a fraction. */
    val isFraction: Boolean get() = _presentation != Presentation.DECIMAL

    /** Resets the value to zero and the presentation to [Presentation.DECIMAL]. */
    fun clear() {
        _valueDouble = 0.0
        _valueFraction = Fraction(0)
        _presentation = Presentation.DECIMAL
    }

    /**
     * Returns the current value as a [Double].
     *
     * Fraction values are converted using [Fraction.toDouble].
     */
    fun getDouble() : Double {
        return if (isFraction) {
            _valueFraction.toDouble()
        } else {
            _valueDouble
        }
    }

    /**
     * Replaces the current value with the decimal [value].
     *
     * Calling this function also switches the presentation to [Presentation.DECIMAL].
     */
    fun setDouble(value: Double) {
        _valueDouble = value
        _presentation = Presentation.DECIMAL
    }

    /**
     * Returns the current value as an [Int].
     *
     * @throws IllegalArgumentException If the value is not an integer or lies outside the [Int]
     * range.
     */
    private fun getInt() : Int {
        require(isInteger) { "Value is not an integer" }
        return if (isFraction) {
            _valueFraction.toInt()
        } else {
            _valueDouble.toInt()
        }
    }

    /** Replaces the current value with its arithmetic negation while retaining its representation. */
    fun negate() {

        if (isFraction) {
            _valueFraction = _valueFraction.negate()
        } else {
            _valueDouble = -_valueDouble
        }
    }

    /**
     * Replaces the current value with its reciprocal while retaining its representation.
     *
     * @throws ArithmeticException If the current value is zero.
     */
    fun reciprocal() {
        if (isFraction) {
            _valueFraction = _valueFraction.reciprocal()
        } else {
            if (_valueDouble == 0.0) {
                throw ArithmeticException("Division by zero")
            }
            _valueDouble = 1.0 / _valueDouble
        }
    }

    /** Replaces the current value with its square while retaining its representation. */
    fun sqr() {
        if (isFraction) {
            _valueFraction = _valueFraction.multiply(_valueFraction)
        } else {
            _valueDouble *= _valueDouble
        }
    }

    /**
     * Adds [other] to this value and stores the result in this object.
     *
     * Fraction representation is retained when both operands are fractions or when the decimal
     * operand is an integer. Otherwise, the result uses decimal representation.
     *
     * @param other The value to add.
     * @throws ArithmeticException If exact fraction arithmetic exceeds the supported range.
     */
    fun add(other: CalculatorValue) {

        when {
            isFraction && other.isFraction -> {
                _valueFraction = _valueFraction.add(other._valueFraction)
            }
            isFraction && other.isInteger -> {
                _valueFraction = _valueFraction.add(Fraction(other.getInt()))
            }
            isInteger && other.isFraction -> {
                _valueFraction = Fraction(getInt()).add(other._valueFraction)
                _presentation = DEFAULT_FRACTION_PRESENTATION
            }
            else -> {
                _valueDouble = getDouble() + other.getDouble()
                _presentation = Presentation.DECIMAL
            }
        }
    }

    /**
     * Subtracts [other] from this value and stores the result in this object.
     *
     * Fraction representation is retained when both operands are fractions or when the decimal
     * operand is an integer. Otherwise, the result uses decimal representation.
     *
     * @param other The value to subtract.
     * @throws ArithmeticException If exact fraction arithmetic exceeds the supported range.
     */
    fun subtract(other: CalculatorValue) {

        when {
            isFraction && other.isFraction -> {
                _valueFraction = _valueFraction.subtract(other._valueFraction)
            }
            isFraction && other.isInteger -> {
                _valueFraction = _valueFraction.subtract(Fraction(other.getInt()))
            }
            isInteger && other.isFraction -> {
                _valueFraction = Fraction(getInt()).subtract(other._valueFraction)
                _presentation = DEFAULT_FRACTION_PRESENTATION
            }
            else -> {
                _valueDouble = getDouble() - other.getDouble()
                _presentation = Presentation.DECIMAL
            }
        }
    }

    /**
     * Multiplies this value by [other] and stores the result in this object.
     *
     * Fraction representation is retained when both operands are fractions or when the decimal
     * operand is an integer. Otherwise, the result uses decimal representation.
     *
     * @param other The multiplication operand.
     * @throws ArithmeticException If exact fraction arithmetic exceeds the supported range.
     */
    fun multiply(other: CalculatorValue) {

        when {
            isFraction && other.isFraction -> {
                _valueFraction = _valueFraction.multiply(other._valueFraction)
            }
            isFraction && other.isInteger -> {
                _valueFraction = _valueFraction.multiply(Fraction(other.getInt()))
            }
            isInteger && other.isFraction -> {
                _valueFraction = Fraction(getInt()).multiply(other._valueFraction)
                _presentation = DEFAULT_FRACTION_PRESENTATION
            }
            else -> {
                _valueDouble = getDouble() * other.getDouble()
                _presentation = Presentation.DECIMAL
            }
        }
    }

    /**
     * Divides this value by [other] and stores the result in this object.
     *
     * Fraction representation is retained when both operands are fractions or when the decimal
     * operand is an integer. Otherwise, IEEE 754 decimal division is used and the result uses
     * decimal representation.
     *
     * @param other The divisor.
     * @throws ArithmeticException If exact fraction division uses a zero divisor or exceeds the
     * supported range.
     */
    fun divide(other: CalculatorValue) {

        when {
            isFraction && other.isFraction -> {
                _valueFraction = _valueFraction.divide(other._valueFraction)
            }
            isFraction && other.isInteger -> {
                _valueFraction = _valueFraction.divide(Fraction(other.getInt()))
            }
            isInteger && other.isFraction -> {
                _valueFraction = Fraction(getInt()).divide(other._valueFraction)
                _presentation = DEFAULT_FRACTION_PRESENTATION
            }
            else -> {
                val d = other.getDouble()
                if (d == 0.0) {
                    throw ArithmeticException("Division by zero")
                }

                _valueDouble = getDouble() / d
                _presentation = Presentation.DECIMAL
            }
        }
    }
}
