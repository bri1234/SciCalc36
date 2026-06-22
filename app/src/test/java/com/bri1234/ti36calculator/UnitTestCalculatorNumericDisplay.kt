/*
 * Ti36Calculator - A TI-36 calculator simulator for Android.
 * Copyright (C) 2026 Torsten Brischalle <torsten@brischalle.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */

package com.bri1234.ti36calculator

import org.junit.Assert.assertEquals
import org.junit.Test

class UnitTestCalculatorNumericDisplay {

    @Test
    fun testSpecialFloatingPointValues() {
        val display = CalculatorNumericDisplay(CalculatorState())

        display.printValue(CalculatorValue(Double.NaN))
        assertEquals("    nAn    ", display.displayMantissa.concatToString())
        assertEquals(-1, display.displayDecimalPointPos)
        assertEquals("   ", display.displayExponent.concatToString())

        display.printValue(CalculatorValue(Double.POSITIVE_INFINITY))
        assertEquals("    InF    ", display.displayMantissa.concatToString())

        display.printValue(CalculatorValue(Double.NEGATIVE_INFINITY))
        assertEquals("   -InF    ", display.displayMantissa.concatToString())
    }

    @Test
    fun testDisplayAllSegments() {
        val display = CalculatorNumericDisplay(CalculatorState())

        display.displayViewAllSegments()

        assertEquals("-8888888888", display.displayMantissa.concatToString())
        assertEquals(1, display.displayDecimalPointPos)
        assertEquals("-88", display.displayExponent.concatToString())
    }
}
