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
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class UnitTestCalculatorValue {

    @Test
    fun testDecimalValue() {
        val value = CalculatorValue.decimal(1.25)

        assertEquals(1.25, value.toDouble(), 0.0)
        assertNull(value.rationalOrNull())
        assertEquals(CalculatorValue.Presentation.DECIMAL, value.presentation)
        assertFalse(value.isFractionDisplayed())
    }

    @Test
    fun testFractionValue() {
        val rational = Rational(3, 4)
        val value = CalculatorValue.fraction(rational)

        assertEquals(0.75, value.toDouble(), 0.0)
        assertEquals(rational, value.rationalOrNull())
        assertEquals(CalculatorValue.Presentation.FRACTION_MIXED, value.presentation)
        assertTrue(value.isFractionDisplayed())
    }

    @Test
    fun testFractionRetainedInDecimalPresentation() {
        val rational = Rational(55, 24)
        val fraction = CalculatorValue.fraction(rational)
        val decimal = fraction.withPresentation(CalculatorValue.Presentation.DECIMAL)
        val restored = decimal.withPresentation(CalculatorValue.Presentation.FRACTION_IMPROPER)

        assertFalse(decimal.isFractionDisplayed())
        assertEquals(rational, decimal.rationalOrNull())
        assertEquals(rational, restored.rationalOrNull())
        assertTrue(restored.isFractionDisplayed())
    }

    @Test
    fun testDecimalCannotUseFractionPresentation() {
        assertThrows(IllegalArgumentException::class.java) {
            CalculatorValue(
                CalculatorValue.INumericValue.Decimal(0.5),
                CalculatorValue.Presentation.FRACTION_MIXED,
            )
        }
    }

    @Test
    fun testConstants() {
        assertEquals(0.0, CalculatorValue.ZERO.toDouble(), 0.0)
        assertEquals(1.0, CalculatorValue.ONE.toDouble(), 0.0)
    }
}
