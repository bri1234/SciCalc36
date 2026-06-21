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

import kotlin.math.abs

/**
 * Components of a fraction represented as a mixed number.
 *
 * @property wholePart The signed whole-number part.
 * @property numerator The numerator of the remaining fractional part.
 * @property denominator The positive denominator of the fractional part.
 */
data class MixedFractionParts(
    val wholePart: Int,
    val numerator: Int,
    val denominator: Int,
)

/**
 * An immutable, exact fraction backed by [Int] values.
 *
 * Values are always normalized: numerator and denominator are coprime, the denominator is
 * positive, and zero is represented as `0 / 1`. Arithmetic operations throw an
 * [ArithmeticException] when an intermediate value or result cannot be represented as [Int].
 *
 * @param numerator The signed numerator of the fraction.
 * @param denominator The non-zero denominator of the fraction, or `1` when omitted.
 * @throws IllegalArgumentException If [denominator] is zero.
 * @throws ArithmeticException If normalization requires a value outside the [Int] range.
 */
class Fraction(numerator: Int, denominator: Int = 1) : Comparable<Fraction> {

    /** The normalized signed numerator. */
    val numerator: Int

    /** The normalized positive denominator. */
    val denominator: Int

    init {
        require(denominator != 0) { "Denominator must not be zero" }

        if (numerator == 0) {
            this.numerator = 0
            this.denominator = 1
        } else {
            val greatestCommonDivisor = greatestCommonDivisor(numerator, denominator)
            var normalizedNumerator = numerator / greatestCommonDivisor
            var normalizedDenominator = denominator / greatestCommonDivisor

            if (normalizedDenominator < 0) {
                normalizedNumerator = Math.negateExact(normalizedNumerator)
                normalizedDenominator = Math.negateExact(normalizedDenominator)
            }

            this.numerator = normalizedNumerator
            this.denominator = normalizedDenominator
        }
    }

    /**
     * Creates a fraction from a whole-number part, numerator, and denominator.
     *
     * The sign of a non-zero [wholePart] applies to the complete mixed number. For example,
     * `Fraction(-6, 4, 6)` represents `-(6 + 4/6)` and is normalized to `-20/3`.
     * The fractional part may be improper and is normalized by the primary constructor.
     *
     * @param wholePart The signed whole-number part.
     * @param numerator The non-negative numerator of the fractional part.
     * @param denominator The positive denominator of the fractional part.
     * @throws IllegalArgumentException If [numerator] is negative or [denominator] is not positive.
     * @throws ArithmeticException If the resulting numerator exceeds the [Int] range.
     */
    constructor(wholePart: Int, numerator: Int, denominator: Int) : this(
        when {
            numerator < 0 ->
                throw IllegalArgumentException("Numerator of a mixed fraction must not be negative")
            denominator <= 0 ->
                throw IllegalArgumentException("Denominator of a mixed fraction must be positive")
            wholePart < 0 ->
                Math.subtractExact(Math.multiplyExact(wholePart, denominator), numerator)
            else ->
                Math.addExact(Math.multiplyExact(wholePart, denominator), numerator)
        },
        denominator,
    )

    /**
     * Creates a fraction from mixed-number [parts].
     *
     * This constructor accepts the exact output of [toMixedFractionParts]. When the whole-number
     * part is zero, the numerator may therefore be negative to preserve the sign of a negative
     * proper fraction.
     *
     * @param parts The whole-number part, numerator, and denominator to convert.
     * @throws IllegalArgumentException If the denominator is not positive, or if a non-zero
     * whole-number part is combined with a negative numerator.
     * @throws ArithmeticException If the resulting numerator exceeds the [Int] range.
     */
    constructor(parts: MixedFractionParts) : this(
        when {
            parts.denominator <= 0 ->
                throw IllegalArgumentException("Denominator of a mixed fraction must be positive")
            parts.wholePart != 0 && parts.numerator < 0 ->
                throw IllegalArgumentException(
                    "Numerator of a mixed fraction must not be negative when the whole part is non-zero",
                )
            parts.wholePart < 0 ->
                Math.subtractExact(
                    Math.multiplyExact(parts.wholePart, parts.denominator),
                    parts.numerator,
                )
            else ->
                Math.addExact(
                    Math.multiplyExact(parts.wholePart, parts.denominator),
                    parts.numerator,
                )
        },
        parts.denominator,
    )

    /**
     * Returns this fraction with its sign reversed.
     *
     * @throws ArithmeticException If the negated numerator exceeds the [Int] range.
     */
    fun negate(): Fraction = Fraction(Math.negateExact(numerator), denominator)

    /**
     * Adds [other] to this value and returns the exact, normalized result.
     *
     * @throws ArithmeticException If the calculation exceeds the [Int] range.
     */
    fun add(other: Fraction): Fraction {
        val denominatorGcd = greatestCommonDivisor(denominator, other.denominator)
        val leftMultiplier = other.denominator / denominatorGcd
        val rightMultiplier = denominator / denominatorGcd
        val leftNumerator = Math.multiplyExact(numerator, leftMultiplier)
        val rightNumerator = Math.multiplyExact(other.numerator, rightMultiplier)

        return Fraction(
            Math.addExact(leftNumerator, rightNumerator),
            Math.multiplyExact(denominator, leftMultiplier),
        )
    }

    /**
     * Subtracts [other] from this value and returns the exact, normalized result.
     *
     * @throws ArithmeticException If the calculation exceeds the [Int] range.
     */
    fun subtract(other: Fraction): Fraction = this.add(other.negate())

    /**
     * Multiplies this value by [other] and returns the exact, normalized result.
     *
     * Common factors are cancelled before multiplication to reduce intermediate value growth.
     *
     * @throws ArithmeticException If the result exceeds the [Int] range.
     */
    fun multiply(other: Fraction): Fraction {
        val leftGcd = greatestCommonDivisor(numerator, other.denominator)
        val rightGcd = greatestCommonDivisor(other.numerator, denominator)

        return Fraction(
            Math.multiplyExact(numerator / leftGcd, other.numerator / rightGcd),
            Math.multiplyExact(denominator / rightGcd, other.denominator / leftGcd),
        )
    }

    /**
     * Divides this value by [other] and returns the exact, normalized result.
     *
     * @throws ArithmeticException If [other] is zero or the result exceeds the [Int] range.
     */
    fun divide(other: Fraction): Fraction {
        if (other.numerator == 0)
            throw ArithmeticException("Division by zero")

        return this.multiply(other.reciprocal())
    }

    /**
     * Returns the reciprocal of this value.
     *
     * @throws ArithmeticException If this value is zero or the result exceeds the [Int] range.
     */
    fun reciprocal(): Fraction {
        if (numerator == 0)
            throw ArithmeticException("Division by zero")

        return Fraction(denominator, numerator)
    }

    /** Returns `true` when this fraction is zero. */
    val isZero: Boolean get() = numerator == 0

    /** Returns `true` when this fraction has no fractional part. */
    val isInteger: Boolean get() = denominator == 1

    /**
     * Returns this fraction as an [Int] when it has no fractional part.
     *
     * @return The integer represented by this fraction.
     * @throws ArithmeticException If this fraction is not an integer.
     */
    fun toInt(): Int {
        if (!isInteger)
            throw ArithmeticException("Fraction is not an integer")

        return numerator
    }

    /**
     * Returns this fraction as a whole-number part, numerator, and denominator.
     *
     * The numerator of the fractional part is non-negative when [MixedFractionParts.wholePart] is
     * non-zero. For a negative proper fraction, whose whole-number part is zero, the numerator
     * retains the negative sign so the result does not lose it.
     *
     * @return The normalized mixed-number components of this fraction.
     */
    fun toMixedFractionParts(): MixedFractionParts {
        val wholePart = numerator / denominator
        val remainder = numerator % denominator
        val fractionalNumerator = if (wholePart == 0) remainder else abs(remainder)

        return MixedFractionParts(wholePart, fractionalNumerator, denominator)
    }

    /** Returns the nearest representable [Double] value. */
    fun toDouble(): Double = numerator.toDouble() / denominator.toDouble()

    /**
     * Compares this fraction with [other] without potentially overflowing cross multiplication.
     */
    override fun compareTo(other: Fraction): Int = compareFractions(
        numerator,
        denominator,
        other.numerator,
        other.denominator,
    )

    /** Returns whether [other] represents the same normalized fraction. */
    override fun equals(other: Any?): Boolean =
        this === other ||
                (other is Fraction && numerator == other.numerator && denominator == other.denominator)

    /** Returns a hash code derived from the normalized numerator and denominator. */
    override fun hashCode(): Int = 31 * numerator + denominator

    /** Returns `numerator/denominator`, or only the numerator for integer values. */
    override fun toString(): String = if (isInteger) {
        numerator.toString()
    } else {
        "$numerator/$denominator"
    }

}

/** Returns the greatest common divisor without converting the operands to wider types. */
private fun greatestCommonDivisor(first: Int, second: Int): Int {
    var a = first
    var b = second

    while (b != 0) {
        val remainder = a % b
        a = b
        b = remainder
    }

    return if (a < 0 && a != Int.MIN_VALUE) -a else a
}

/**
 * Compares two normalized fractions using their continued-fraction representation.
 */
private fun compareFractions(
    firstNumerator: Int,
    firstDenominator: Int,
    secondNumerator: Int,
    secondDenominator: Int,
): Int {
    var n1 = firstNumerator
    var d1 = firstDenominator
    var n2 = secondNumerator
    var d2 = secondDenominator
    var reverse = false

    while (true) {
        val quotient1 = Math.floorDiv(n1, d1)
        val quotient2 = Math.floorDiv(n2, d2)

        if (quotient1 != quotient2) {
            return if (reverse) {
                quotient2.compareTo(quotient1)
            } else {
                quotient1.compareTo(quotient2)
            }
        }

        val remainder1 = Math.floorMod(n1, d1)
        val remainder2 = Math.floorMod(n2, d2)

        if (remainder1 == 0 || remainder2 == 0) {
            val comparison = when {
                remainder1 == remainder2 -> 0
                remainder1 == 0 -> -1
                else -> 1
            }
            return if (reverse) -comparison else comparison
        }

        n1 = d1
        d1 = remainder1
        n2 = d2
        d2 = remainder2
        reverse = !reverse
    }
}

/**
 * Converts a finite [Double] to an [Int]-backed fraction using continued fractions.
 *
 * The closest convergent within the supported tolerance is returned. If no such fraction can be
 * represented by [Int] numerator and denominator values, `null` is returned.
 */
fun convertDoubleToFraction(value: Double): Fraction? {
    if (!value.isFinite())
        return null

    val isNegative = value < 0.0
    val absoluteValue = abs(value)
    val numeratorLimit = if (isNegative) {
        -(Int.MIN_VALUE.toLong())
    } else {
        Int.MAX_VALUE.toLong()
    }
    val denominatorLimit = Int.MAX_VALUE.toLong()
    val tolerance = maxOf(
        Math.ulp(absoluteValue) * 4.0,
        minOf(1E-9, absoluteValue * 2E-9),
    )

    var previousNumerator = 0L
    var currentNumerator = 1L
    var previousDenominator = 1L
    var currentDenominator = 0L
    var continuedFractionValue = absoluteValue

    repeat(64) {
        val wholePart = kotlin.math.floor(continuedFractionValue).toLong()

        if (wholePart < 0L ||
            (currentNumerator != 0L &&
                    wholePart > (numeratorLimit - previousNumerator) / currentNumerator) ||
            (currentDenominator != 0L &&
                    wholePart > (denominatorLimit - previousDenominator) / currentDenominator)) {
            return null
        }

        val nextNumerator = wholePart * currentNumerator + previousNumerator
        val nextDenominator = wholePart * currentDenominator + previousDenominator
        if (nextNumerator > numeratorLimit ||
            nextDenominator <= 0L ||
            nextDenominator > denominatorLimit) {
            return null
        }

        val approximation = nextNumerator.toDouble() / nextDenominator.toDouble()
        if ((nextNumerator != 0L || absoluteValue == 0.0) &&
            abs(approximation - absoluteValue) <= tolerance) {
            val signedNumerator = if (isNegative) -nextNumerator else nextNumerator
            return Fraction(signedNumerator.toInt(), nextDenominator.toInt())
        }

        val remainder = continuedFractionValue - wholePart
        if (remainder == 0.0)
            return null

        previousNumerator = currentNumerator
        currentNumerator = nextNumerator
        previousDenominator = currentDenominator
        currentDenominator = nextDenominator
        continuedFractionValue = 1.0 / remainder
    }

    return null
}
