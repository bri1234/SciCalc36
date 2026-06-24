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
 * along with this program.  If not, see <http://gnu.org>.
 */

package com.bri1234.scicalc36

import org.junit.Assert.assertEquals
import org.junit.Test

class UnitTestCalculatorDisplayData {

    @Test
    fun testToClipboardTextWithoutExponent() {
        val displayData = CalculatorDisplayData(
            digitsLarge = "      12345".toCharArray(),
            decimalPointIndex = 7,
            digitsSmall = "   ".toCharArray(),
        )

        assertEquals("12.345", displayData.toClipboardText())
    }

    @Test
    fun testToClipboardTextWithoutDecimalPoint() {
        val displayData = CalculatorDisplayData(
            digitsLarge = "    Error  ".toCharArray(),
            decimalPointIndex = -1,
            digitsSmall = "   ".toCharArray(),
        )

        assertEquals("Error", displayData.toClipboardText())
    }

    @Test
    fun testToClipboardTextWithPositiveExponent() {
        val displayData = CalculatorDisplayData(
            digitsLarge = "          1".toCharArray(),
            decimalPointIndex = 10,
            digitsSmall = " 04".toCharArray(),
        )

        assertEquals("1E04", displayData.toClipboardText())
    }

    @Test
    fun testToClipboardTextWithNegativeExponent() {
        val displayData = CalculatorDisplayData(
            digitsLarge = "       1234".toCharArray(),
            decimalPointIndex = 7,
            digitsSmall = "-03".toCharArray(),
        )

        assertEquals("1.234E-03", displayData.toClipboardText())
    }
}
