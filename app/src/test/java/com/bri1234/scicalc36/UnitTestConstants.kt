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

class UnitTestConstants {

    @Test
    fun testConstants() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON PI", "3.141592654", "", "DEG")

        calc.testStep("AC/ON CONST sin", "299792458.", "", "DEG")
        calc.testStep("AC/ON CONST cos", "9.80665", "", "DEG")
        calc.testStep("AC/ON CONST tan", "9.109383714", "-31", "DEG")
        calc.testStep("AC/ON CONST y^x", "1.602176634", "-19", "DEG")
        calc.testStep("AC/ON CONST 1/x", "6.62607015", "-34", "DEG")
        calc.testStep("AC/ON CONST x^2", "6.02214076", "23", "DEG")
        calc.testStep("AC/ON CONST sqrt", "8.314462618", "", "DEG")
        calc.testStep("AC/ON CONST /", "6.6743015", "-11", "DEG")
    }

}
