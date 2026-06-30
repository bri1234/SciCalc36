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

class UnitTestArithmetic {

    @Test
    fun testArithmetic() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 6 0 + 5 * 1 2 =", "120.", "", "DEG")
        calc.testStep("2", "2", "", "DEG")
        calc.testStep("+", "2.", "", "DEG")
        calc.testStep("3", "3", "", "DEG")
        calc.testStep("*", "3.", "", "DEG")
        calc.testStep("4", "4", "", "DEG")
        calc.testStep("y^x", "4.", "", "DEG")
        calc.testStep("5", "5", "", "DEG")
        calc.testStep("=", "3074.", "", "DEG")
        calc.testStep("AC/ON 1 + 8 +/- + 1 2 =", "5.", "", "DEG")
        calc.testStep("2 / 3 * 3 =", "2.", "", "DEG")
        calc.testStep("1 / 3 * 3 =", "1.", "", "DEG")

        calc.testStep("AC/ON ( - 9 )", "-9.", "", "DEG")

        calc.testStep("AC/ON 1 + ( - 9 )", "-9.", "", "DEG")
        calc.testStep("=", "-8.", "", "DEG")
        calc.testStep("=", "-17.", "", "DEG")

        calc.testStep("AC/ON 3 / ( - 6 ) =", "-0.5", "", "DEG")
        calc.testStep("AC/ON 3 * ( - 9 ) =", "-27.", "", "DEG")

        calc.testStep("AC/ON 5 + - 3 =", "2.", "", "DEG")
        calc.testStep("AC/ON 5 - * 3 =", "15.", "", "DEG")
        calc.testStep("AC/ON 5 * / 4 =", "1.25", "", "DEG")
        calc.testStep("AC/ON 5 - + 3 =", "8.", "", "DEG")

        calc.testStep("AC/ON 5 * ( 6 + - 2 ) =", "20.", "", "DEG")
    }

    @Test
    fun testConstant() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 2 * PI", "3.141592654", "", "DEG")
        calc.testStep("=", "6.283185307", "", "DEG")
        calc.testStep("AC/ON PI * 2", "2", "", "DEG")
        calc.testStep("=", "6.283185307", "", "DEG")
        calc.testStep("AC/ON 2 * CONST SIN", "299792458.", "", "DEG")
        calc.testStep("=", "599584916.", "", "DEG")
        calc.testStep("AC/ON CONST SIN * 2 =", "599584916.", "", "DEG")

    }
}
