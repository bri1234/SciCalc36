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

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class UnitTestFraction {

    @Test
    fun testNormalization() {
        assertEquals(Fraction(2, 3), Fraction(6, 9))
        assertEquals(Fraction(-2, 3), Fraction(6, -9))
        assertEquals(Fraction(-2, 3), Fraction(-6, 9))
        assertEquals(Fraction(2, 3), Fraction(-6, -9))
        assertEquals(Fraction(0), Fraction(0, -25))
        assertEquals(1, Fraction(0).denominator)
    }

    @Test
    fun testInvalidDenominator() {
        assertThrows(IllegalArgumentException::class.java) { Fraction(1, 0) }
    }

    @Test
    fun testMixedFractionConstructor() {
        assertEquals(Fraction(20, 3), Fraction(6, 4, 6))
        assertEquals(Fraction(-20, 3), Fraction(-6, 4, 6))
        assertEquals(Fraction(3, 2), Fraction(0, 3, 2))
        assertEquals(Fraction(5), Fraction(4, 2, 2))

        assertThrows(IllegalArgumentException::class.java) { Fraction(1, -1, 2) }
        assertThrows(IllegalArgumentException::class.java) { Fraction(1, 1, 0) }
        assertThrows(IllegalArgumentException::class.java) { Fraction(1, 1, -2) }
    }

    @Test
    fun testMixedFractionParts() {
        assertEquals(
            MixedFractionParts(6, 2, 3),
            Fraction(20, 3).toMixedFractionParts(),
        )
        assertEquals(
            MixedFractionParts(-6, 2, 3),
            Fraction(-20, 3).toMixedFractionParts(),
        )
        assertEquals(
            MixedFractionParts(0, 1, 2),
            Fraction(1, 2).toMixedFractionParts(),
        )
        assertEquals(
            MixedFractionParts(0, -1, 2),
            Fraction(-1, 2).toMixedFractionParts(),
        )
        assertEquals(
            MixedFractionParts(2, 0, 1),
            Fraction(2).toMixedFractionParts(),
        )
    }

    @Test
    fun testMixedFractionPartsConstructor() {
        val fractions = listOf(
            Fraction(20, 3),
            Fraction(-20, 3),
            Fraction(1, 2),
            Fraction(-1, 2),
            Fraction(2),
            Fraction(0),
        )

        for (fraction in fractions) {
            assertEquals(fraction, Fraction(fraction.toMixedFractionParts()))
        }

        assertThrows(IllegalArgumentException::class.java) {
            Fraction(MixedFractionParts(1, -1, 2))
        }
        assertThrows(IllegalArgumentException::class.java) {
            Fraction(MixedFractionParts(1, 1, 0))
        }
    }

    @Test
    fun testArithmetic() {
        assertEquals(Fraction(5, 6), Fraction(1, 2).add(Fraction(1, 3)))
        assertEquals(Fraction(1, 6), Fraction(1, 2).subtract(Fraction(1, 3)))
        assertEquals(Fraction(-1, 2), Fraction(1, 2).negate())
        assertEquals(Fraction(3, 8), Fraction(1, 2).multiply(Fraction(3, 4)))
        assertEquals(Fraction(2, 3), Fraction(1, 2).divide(Fraction(3, 4)))
        assertEquals(Fraction(5, 2), Fraction(2, 5).reciprocal())
    }

    @Test
    fun testDivisionByZero() {
        assertThrows(ArithmeticException::class.java) { Fraction(1).divide(Fraction(0)) }
        assertThrows(ArithmeticException::class.java) { Fraction(0).reciprocal() }
    }

    @Test
    fun testOverflowIsRejected() {
        assertThrows(ArithmeticException::class.java) {
            Fraction(Int.MAX_VALUE).add(Fraction(1))
        }
        assertThrows(ArithmeticException::class.java) {
            Fraction(Int.MAX_VALUE, 2, 3)
        }
    }

    @Test
    fun testComparisonAndConversion() {
        assertTrue(Fraction(1, 3) < Fraction(1, 2))
        assertTrue(Fraction(4, 2).isInteger)
        assertFalse(Fraction(1, 2).isInteger)
        assertTrue(Fraction(0).isZero)
        assertEquals(0.125, Fraction(1, 8).toDouble(), 0.0)
        assertEquals("-2/3", Fraction(-2, 3).toString())
        assertEquals("2", Fraction(4, 2).toString())

        assertTrue(Fraction(Int.MAX_VALUE, Int.MAX_VALUE - 1) >
                Fraction(Int.MAX_VALUE - 1, Int.MAX_VALUE))
    }

    @Test
    fun testToInt() {
        assertEquals(0, Fraction(0).toInt())
        assertEquals(2, Fraction(6, 3).toInt())
        assertEquals(-2, Fraction(-6, 3).toInt())
        assertEquals(Int.MAX_VALUE, Fraction(Int.MAX_VALUE).toInt())

        assertThrows(ArithmeticException::class.java) { Fraction(3, 2).toInt() }
        assertThrows(ArithmeticException::class.java) { Fraction(-1, 2).toInt() }
    }
}
