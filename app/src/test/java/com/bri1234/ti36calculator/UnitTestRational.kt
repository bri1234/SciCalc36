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

import java.math.BigInteger
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class UnitTestRational {

    @Test
    fun testNormalization() {
        assertEquals(Rational(2, 3), Rational(6, 9))
        assertEquals(Rational(-2, 3), Rational(6, -9))
        assertEquals(Rational(-2, 3), Rational(-6, 9))
        assertEquals(Rational(2, 3), Rational(-6, -9))
        assertEquals(Rational.ZERO, Rational(0, -25))
        assertEquals(BigInteger.ONE, Rational.ZERO.denominator)
    }

    @Test
    fun testInvalidDenominator() {
        assertThrows(IllegalArgumentException::class.java) { Rational(1, 0) }
    }

    @Test
    fun testArithmetic() {
        assertEquals(Rational(5, 6), Rational(1, 2) + Rational(1, 3))
        assertEquals(Rational(1, 6), Rational(1, 2) - Rational(1, 3))
        assertEquals(Rational(-1, 2), -Rational(1, 2))
        assertEquals(Rational(3, 8), Rational(1, 2) * Rational(3, 4))
        assertEquals(Rational(2, 3), Rational(1, 2) / Rational(3, 4))
        assertEquals(Rational(5, 2), Rational(2, 5).reciprocal())
    }

    @Test
    fun testDivisionByZero() {
        assertThrows(ArithmeticException::class.java) { Rational.ONE / Rational.ZERO }
        assertThrows(ArithmeticException::class.java) { Rational.ZERO.reciprocal() }
    }

    @Test
    fun testLargeValuesDoNotOverflow() {
        val large = BigInteger.TEN.pow(100)

        assertEquals(
            Rational(BigInteger.ONE, BigInteger.valueOf(3L)),
            Rational(large, large.multiply(BigInteger.valueOf(3L))),
        )
        assertEquals(
            Rational(BigInteger.valueOf(2L)),
            Rational(large, BigInteger.valueOf(3L)) *
                    Rational(BigInteger.valueOf(6L), large),
        )
    }

    @Test
    fun testComparisonAndConversion() {
        assertTrue(Rational(1, 3) < Rational(1, 2))
        assertTrue(Rational(4, 2).isInteger())
        assertFalse(Rational(1, 2).isInteger())
        assertTrue(Rational.ZERO.isZero())
        assertEquals(0.125, Rational(1, 8).toDouble(), 0.0)
        assertEquals("-2/3", Rational(-2, 3).toString())
        assertEquals("2", Rational(4, 2).toString())
    }
}
