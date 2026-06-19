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

import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext

/**
 * An immutable, exact rational number.
 *
 * Values are always normalized: numerator and denominator are coprime, the denominator is
 * positive, and zero is represented as `0 / 1`.
 *
 * @param numerator The signed numerator of the rational number.
 * @param denominator The non-zero denominator of the rational number.
 * @throws IllegalArgumentException If [denominator] is zero.
 */
class Rational(numerator: BigInteger, denominator: BigInteger) : Comparable<Rational> {

    /** The normalized signed numerator. */
    val numerator: BigInteger

    /** The normalized positive denominator. */
    val denominator: BigInteger

    init {
        require(denominator != BigInteger.ZERO) { "Denominator must not be zero" }

        if (numerator == BigInteger.ZERO) {
            this.numerator = BigInteger.ZERO
            this.denominator = BigInteger.ONE
        } else {
            val sign = if (denominator.signum() < 0) BigInteger.valueOf(-1L) else BigInteger.ONE
            val greatestCommonDivisor = numerator.gcd(denominator)

            this.numerator = numerator.divide(greatestCommonDivisor).multiply(sign)
            this.denominator = denominator.divide(greatestCommonDivisor).multiply(sign)
        }
    }

    /**
     * Creates a rational number from [Long] values.
     *
     * @param numerator The signed numerator.
     * @param denominator The non-zero denominator, or `1` when omitted.
     * @throws IllegalArgumentException If [denominator] is zero.
     */
    constructor(numerator: Long, denominator: Long = 1L) :
            this(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator))

    /**
     * Creates an integer-valued rational number with denominator `1`.
     *
     * @param numerator The signed integer value.
     */
    constructor(numerator: BigInteger) : this(numerator, BigInteger.ONE)

    /**
     * Creates a rational number from [Int] values.
     *
     * @param numerator The signed numerator.
     * @param denominator The non-zero denominator, or `1` when omitted.
     * @throws IllegalArgumentException If [denominator] is zero.
     */
    constructor(numerator: Int, denominator: Int = 1) :
            this(numerator.toLong(), denominator.toLong())

    /** Returns this rational number with its sign reversed. */
    operator fun unaryMinus(): Rational = Rational(numerator.negate(), denominator)

    /**
     * Adds [other] to this value and returns the exact, normalized result.
     *
     * @param other The value to add.
     * @return The sum of this value and [other].
     */
    operator fun plus(other: Rational): Rational {
        val denominatorGcd = denominator.gcd(other.denominator)
        val leftMultiplier = other.denominator.divide(denominatorGcd)
        val rightMultiplier = denominator.divide(denominatorGcd)

        return Rational(
            numerator.multiply(leftMultiplier).add(other.numerator.multiply(rightMultiplier)),
            denominator.multiply(leftMultiplier),
        )
    }

    /**
     * Subtracts [other] from this value and returns the exact, normalized result.
     *
     * @param other The value to subtract.
     * @return The difference between this value and [other].
     */
    operator fun minus(other: Rational): Rational = this + -other

    /**
     * Multiplies this value by [other] and returns the exact, normalized result.
     *
     * Common factors are cancelled before multiplication to limit intermediate value growth.
     *
     * @param other The value to multiply by.
     * @return The product of this value and [other].
     */
    operator fun times(other: Rational): Rational {
        val leftGcd = numerator.gcd(other.denominator)
        val rightGcd = other.numerator.gcd(denominator)

        return Rational(
            numerator.divide(leftGcd).multiply(other.numerator.divide(rightGcd)),
            denominator.divide(rightGcd).multiply(other.denominator.divide(leftGcd)),
        )
    }

    /**
     * Divides this value by [other] and returns the exact, normalized result.
     *
     * @param other The divisor.
     * @return The quotient of this value and [other].
     * @throws ArithmeticException If [other] is zero.
     */
    operator fun div(other: Rational): Rational {
        if (other.numerator == BigInteger.ZERO)
            throw ArithmeticException("Division by zero")

        return this * Rational(other.denominator, other.numerator)
    }

    /**
     * Returns the reciprocal of this value.
     *
     * @return A rational number with numerator and denominator exchanged.
     * @throws ArithmeticException If this value is zero.
     */
    fun reciprocal(): Rational {
        if (numerator == BigInteger.ZERO)
            throw ArithmeticException("Division by zero")

        return Rational(denominator, numerator)
    }

    /** Returns `true` when this rational number is zero. */
    fun isZero(): Boolean = numerator == BigInteger.ZERO

    /** Returns `true` when this rational number has no fractional part. */
    fun isInteger(): Boolean = denominator == BigInteger.ONE

    /**
     * Converts this exact value to the nearest representable [Double].
     *
     * The division uses [MathContext.DECIMAL128] before conversion to reduce avoidable precision
     * loss. Values outside the [Double] range may become positive or negative infinity.
     *
     * @return The approximate floating-point representation of this value.
     */
    fun toDouble(): Double = BigDecimal(numerator)
        .divide(BigDecimal(denominator), MathContext.DECIMAL128)
        .toDouble()

    /**
     * Compares this value with [other] using exact cross multiplication.
     *
     * @param other The rational number to compare with.
     * @return A negative value, zero, or a positive value when this value is respectively less
     * than, equal to, or greater than [other].
     */
    override fun compareTo(other: Rational): Int =
        numerator.multiply(other.denominator).compareTo(other.numerator.multiply(denominator))

    /** Returns whether [other] represents the same normalized rational number. */
    override fun equals(other: Any?): Boolean =
        this === other ||
                (other is Rational && numerator == other.numerator && denominator == other.denominator)

    /** Returns a hash code derived from the normalized numerator and denominator. */
    override fun hashCode(): Int = 31 * numerator.hashCode() + denominator.hashCode()

    /**
     * Returns the normalized value as `numerator/denominator`, or only the numerator for integers.
     */
    override fun toString(): String = if (isInteger()) {
        numerator.toString()
    } else {
        "$numerator/$denominator"
    }

    companion object {
        /** The rational number zero, normalized as `0 / 1`. */
        val ZERO: Rational = Rational(0)

        /** The rational number one, normalized as `1 / 1`. */
        val ONE: Rational = Rational(1)
    }
}
