/*
 * SciCalc 36 - A classic-style scientific calculator inspired by traditional handheld calculator workflows.
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

package com.bri1234.scicalc36

import com.bri1234.scicalc36.enums.Presentation
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotSame
import org.junit.Assert.assertThrows
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

    @Test
    fun testFractionPresentationChanges() {
        val value = CalculatorValue(Fraction(3, 2))

        assertEquals(Presentation.FRACTION_MIXED, value.presentation)

        value.changePresentationFractionFromMixedToImproper()
        assertEquals(Presentation.FRACTION_IMPROPER, value.presentation)

        value.changePresentationFractionFromImproperToMixed()
        assertEquals(Presentation.FRACTION_MIXED, value.presentation)

        value.changePresentationToDecimal()
        assertEquals(Presentation.DECIMAL, value.presentation)
        assertEquals(1.5, value.getDouble(), 0.0)

        value.changePresentationDecimalToFraction()
        assertEquals(Presentation.FRACTION_MIXED, value.presentation)
        assertEquals(Fraction(3, 2), value.getFraction())
    }

    @Test
    fun testDecimalToFractionPresentation() {
        val testValues = mapOf(
            0.75 to Fraction(3, 4),
            -1.25 to Fraction(-5, 4),
            2.291666667 to Fraction(55, 24),
        )

        for ((decimal, fraction) in testValues) {
            val value = CalculatorValue(decimal)
            value.changePresentationDecimalToFraction()

            assertEquals(Presentation.FRACTION_MIXED, value.presentation)
            assertEquals(fraction, value.getFraction())
        }
    }

    @Test
    fun testInvalidDecimalRemainsDecimal() {
        val values = listOf(
            CalculatorValue(Double.NaN),
            CalculatorValue(Double.POSITIVE_INFINITY),
            CalculatorValue(Double.NEGATIVE_INFINITY),
            CalculatorValue(Double.MAX_VALUE),
        )

        for (value in values) {
            value.changePresentationDecimalToFraction()
            assertEquals(Presentation.DECIMAL, value.presentation)
            assertFalse(value.isFraction)
        }
    }

    @Test
    fun testArithmeticWithFractionsAndIntegers() {
        val add = CalculatorValue(3.0)
        add.add(CalculatorValue(Fraction(1, 2)))
        assertEquals(Fraction(7, 2), add.getFraction())

        val subtract = CalculatorValue(3.0)
        subtract.subtract(CalculatorValue(Fraction(1, 2)))
        assertEquals(Fraction(5, 2), subtract.getFraction())

        val multiply = CalculatorValue(3.0)
        multiply.multiply(CalculatorValue(Fraction(1, 2)))
        assertEquals(Fraction(3, 2), multiply.getFraction())

        val divide = CalculatorValue(3.0)
        divide.divide(CalculatorValue(Fraction(1, 2)))
        assertEquals(Fraction(6), divide.getFraction())

        val decimal = CalculatorValue(0.5)
        decimal.add(CalculatorValue(Fraction(1, 3)))
        assertFalse(decimal.isFraction)
        assertEquals(5.0 / 6.0, decimal.getDouble(), 1e-15)
    }

    @Test
    fun testFractionArithmeticOverflow() {
        assertThrows(ArithmeticException::class.java) {
            CalculatorValue(Fraction(Int.MAX_VALUE)).add(CalculatorValue(Fraction(1)))
        }
        assertThrows(ArithmeticException::class.java) {
            CalculatorValue(Fraction(Int.MIN_VALUE)).subtract(CalculatorValue(Fraction(1)))
        }
        assertThrows(ArithmeticException::class.java) {
            CalculatorValue(Fraction(Int.MAX_VALUE)).multiply(CalculatorValue(Fraction(2)))
        }
        assertThrows(ArithmeticException::class.java) {
            CalculatorValue(Fraction(Int.MIN_VALUE)).divide(CalculatorValue(Fraction(-1)))
        }
    }
}
