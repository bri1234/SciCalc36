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

class UnitTestInput {
    @Test
    fun testInput() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("4", "4", "", "DEG")
        calc.testStep("2", "42", "", "DEG")
        calc.testStep("=", "42.", "", "DEG")

        calc.testStep("AC/ON 1 2 3 4 5 6 7 8 9 0", "1234567890", "", "DEG")
        calc.testStep("1 2 3 4 5 6 7 8 9 0", "1234567890", "", "DEG")
        calc.testStep("AC/ON 1 +/- 2 3 4 5 6 7 8 9 0", "-1234567890", "", "DEG")
        calc.testStep("1 2 3 4 5 6 7 8 9 0", "-1234567890", "", "DEG")
    }

    @Test
    fun testBack() {
        val calc = CalculatorCore()

        calc.testStep("0", "0", "", "DEG")
        calc.testStep(".", "0.", "", "DEG")
        calc.testStep("0", "0.0", "", "DEG")
        calc.testStep("0", "0.00", "", "DEG")
        calc.testStep("1", "0.001", "", "DEG")
        calc.testStep("->", "0.00", "", "DEG")
        calc.testStep("->", "0.0", "", "DEG")
        calc.testStep("->", "0.", "", "DEG")
        calc.testStep("->", "0", "", "DEG")
        calc.testStep("->", "0", "", "DEG")

        calc.testStep("AC/ON 0 . ->", "0", "", "DEG")
        calc.testStep("AC/ON 5 . ->", "5", "", "DEG")
        calc.testStep("->", "0", "", "DEG")
        calc.testStep("->", "0", "", "DEG")

        calc.testStep("AC/ON 0 . ->", "0", "", "DEG")
    }

    @Test
    fun testSecondThirdHyp() {
        val calc = CalculatorCore()

        calc.testStep("", "0.", "", "DEG")
        calc.testStep("2nd", "0.", "", "2nd DEG")
        calc.testStep("3rd", "0.", "", "3rd DEG")
        calc.testStep("2nd", "0.", "", "2nd DEG")
        calc.testStep("2nd", "0.", "", "DEG")
        calc.testStep("3rd", "0.", "", "3rd DEG")
        calc.testStep("3rd", "0.", "", "DEG")
        calc.testStep("HYP", "0.", "", "HYP DEG")
        calc.testStep("HYP", "0.", "", "DEG")
        calc.testStep("HYP 3rd", "0.", "", "3rd HYP DEG")
        calc.testStep("2nd", "0.", "", "2nd HYP DEG")
        calc.testStep("AC/ON 2nd HYP", "0.", "", "RAD")
        calc.testStep("3rd HYP", "0.", "", "GRAD")

    }

    @Test
    fun testUnusualInput() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 2", "2", "", "DEG")
        calc.testStep("*", "2.", "", "DEG")
        calc.testStep("*", "2.", "", "DEG")
        calc.testStep("*", "2.", "", "DEG")
        calc.testStep("5 =", "10.", "", "DEG")

    }

    @Test
    fun testSign() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON +/-", "0.", "", "DEG")
        calc.testStep("AC/ON . +/-", "-0.", "", "DEG")
        calc.testStep("AC/ON 3 +/-", "-3", "", "DEG")
        calc.testStep("AC/ON 0 +/-", "-0", "", "DEG")
        calc.testStep("AC/ON 1 8 0 sin +/-", "0.", "", "DEG")

        calc.testStep("AC/ON HEX 2 5 +/-", "FFFFFFFFdb", "", "HEX")
    }
}
