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

import org.junit.Test

class UnitTestNotation {

    @Test
    fun testNotation() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")

        calc.testStep("AC/ON 1 2 3 4 5 =", "12345.", "", "DEG")
        calc.testStep("SCI", "1.2345", "04", "DEG")
        calc.testStep("ENG", "12.345", "03", "DEG")
        calc.testStep("FLO", "12345.", "", "DEG")

        calc.testStep("FIX 2", "12345.00", "", "DEG")
        calc.testStep("ENG", "12.35", "03", "DEG")
        calc.testStep("FIX 5", "12.34500", "03", "DEG")
        calc.testStep("FIX .", "12.345", "03", "DEG")

        calc.testStep("AC/ON 1 . 2 3 4 5 +/- EE +/- 6 5", "-1.2345", "-65", "DEG")
    }
}

