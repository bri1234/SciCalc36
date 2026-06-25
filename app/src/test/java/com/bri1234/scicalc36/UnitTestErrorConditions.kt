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
 * along with this program.  If not, see <https://gnu.org>.
 */

package com.bri1234.scicalc36

import org.junit.Test

class UnitTestErrorConditions {

    @Test
    fun testErrorConditions() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 9 . 9 EE 9 9 * 1 EE 1 0 =", "Error  ", "", "DEG")

        calc.testStep("AC/ON 9 / 0 =", "Error  ", "", "DEG")

        calc.testStep("AC/ON 0 log", "Error  ", "", "DEG")
        calc.testStep("AC/ON 0 ln", "Error  ", "", "DEG")
        calc.testStep("AC/ON 0 1/x", "Error  ", "", "DEG")

        calc.testStep("AC/ON 2 +/- log", "Error  ", "", "DEG")
        calc.testStep("AC/ON 2 +/- ln", "Error  ", "", "DEG")
        calc.testStep("AC/ON 2 +/- sqrt", "Error  ", "", "DEG")
        calc.testStep("AC/ON 2 +/- yrootx 4 =", "Error  ", "", "DEG")
        calc.testStep("AC/ON 2 +/- y^x 4 1/x =", "Error  ", "", "DEG")

        calc.testStep("AC/ON 0 y^x 0 =", "1.", "", "DEG")
        calc.testStep("AC/ON 8 yrootx 0 =", "Error  ", "", "DEG")

        calc.testStep("AC/ON 9 0 tan", "Error  ", "", "DEG")
        calc.testStep("AC/ON 2 asin", "Error  ", "", "DEG")
        calc.testStep("AC/ON 2 acos", "Error  ", "", "DEG")
        calc.testStep("AC/ON 2 hyp atan", "Error  ", "", "DEG HYP")

        calc.testStep("AC/ON 2 +/- x!", "Error  ", "", "DEG")

        calc.testStep("AC/ON 2 +/- x<>y 3 +/- nPr", "Error  ", "", "DEG")
        calc.testStep("AC/ON 2 +/- x<>y 3 +/- nCr", "Error  ", "", "DEG")
    }
}

