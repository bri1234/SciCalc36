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

class UnitTestPowerAndRoots {
    @Test
    fun testPowersAndRoots() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 8 1/x", "0.125", "", "DEG")
        calc.testStep("+ 4 1/x", "0.25", "", "DEG")
        calc.testStep("=", "0.375", "", "DEG")
        calc.testStep("6 x^2", "36.", "", "DEG")
        calc.testStep("+ 2", "2", "", "DEG")
        calc.testStep("=", "38.", "", "DEG")
        calc.testStep("AC/ON 2 5 6 sqrt", "16.", "", "DEG")
        calc.testStep("+ 4 sqrt", "2.", "", "DEG")
        calc.testStep("=", "18.", "", "DEG")
        calc.testStep("AC/ON 8 cbrt", "2.", "", "DEG")
        calc.testStep("+ 2 7 cbrt", "3.", "", "DEG")
        calc.testStep("=", "5.", "", "DEG")
        calc.testStep("5 y^x 3 =", "125.", "", "DEG")
        calc.testStep("AC/ON 8 yrootx 3 =", "2.", "", "DEG")

    }
}
