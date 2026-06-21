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
import org.junit.Assert.assertNotSame
import org.junit.Assert.assertTrue
import org.junit.Test

class UnitTestCalculatorValue {

    @Test
    fun testDecimalValue() {
        val value = CalculatorValue(1.25)

        assertEquals(1.25, value.getDouble(), 0.0)

        value.setDouble(-3.5)
        assertEquals(-3.5, value.getDouble(), 0.0)
    }

    @Test
    fun testFractionValue() {
        val fraction = Fraction(3, 4)
        val value = CalculatorValue(fraction)

        assertEquals(0.75, value.getDouble(), 0.0)
    }

    @Test
    fun testIsInteger() {
        assertTrue(CalculatorValue(0.0).isInteger)
        assertTrue(CalculatorValue(42.0).isInteger)
        assertTrue(CalculatorValue(-3.0).isInteger)
        assertFalse(CalculatorValue(1.25).isInteger)
        assertFalse(CalculatorValue(Double.NaN).isInteger)
        assertFalse(CalculatorValue(Double.POSITIVE_INFINITY).isInteger)

        assertTrue(CalculatorValue(Fraction(4, 2)).isInteger)
        assertFalse(CalculatorValue(Fraction(3, 2)).isInteger)
    }

    @Test
    fun testClone() {
        val decimalOriginal = CalculatorValue(1.25)
        val decimalCopy = decimalOriginal.clone()

        assertNotSame(decimalOriginal, decimalCopy)
        assertEquals(1.25, decimalCopy.getDouble(), 0.0)

        decimalOriginal.setDouble(2.5)
        assertEquals(1.25, decimalCopy.getDouble(), 0.0)

        val fractionOriginal = CalculatorValue(Fraction(3, 4))
        val fractionCopy = fractionOriginal.clone()

        assertNotSame(fractionOriginal, fractionCopy)
        assertTrue(fractionCopy.isFraction)
        assertEquals(0.75, fractionCopy.getDouble(), 0.0)

        fractionOriginal.negate()
        assertEquals(0.75, fractionCopy.getDouble(), 0.0)
    }
}
