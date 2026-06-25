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

class UnitTestParenthesis {

    @Test
    fun testParenthesis() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON (", "0.", "", "DEG ()")
        calc.testStep(")", "0.", "", "DEG")

        calc.testStep("AC/ON (", "0.", "", "DEG ()")
        calc.testStep("2 + 3", "3", "", "DEG ()")
        calc.testStep(")", "5.", "", "DEG")

        calc.testStep("AC/ON ( (", "0.", "", "DEG ()")
        calc.testStep(")", "0.", "", "DEG ()")
        calc.testStep(")", "0.", "", "DEG")

    }

    @Test
    fun testParenthesisCalculations() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON ( 2 + 3 ) * 4 =", "20.", "", "DEG")
        calc.testStep("AC/ON 2 * ( 3 + 4 ) =", "14.", "", "DEG")
        calc.testStep("AC/ON ( 8 - 3 ) * ( 2 + 5 ) =", "35.", "", "DEG")
        calc.testStep("AC/ON ( 9 + 6 ) / ( 5 - 2 ) =", "5.", "", "DEG")
        calc.testStep("AC/ON ( 2 + 3 ) * ( 4 + 5 ) =", "45.", "", "DEG")
        calc.testStep("AC/ON 7 * ( 8 - ( 3 + 2 ) ) =", "21.", "", "DEG")
        calc.testStep("AC/ON ( 1 + 2 + 3 ) * ( 4 + 5 ) =", "54.", "", "DEG")
        calc.testStep("AC/ON 5 + ( 6 * ( 7 - 2 ) ) =", "35.", "", "DEG")
        calc.testStep("AC/ON ( 8 / 2 ) + ( 9 / 3 ) =", "7.", "", "DEG")
        calc.testStep("AC/ON ( 2 + 3 ) * ( 4 - 1 ) + ( 6 / 2 ) =", "18.", "", "DEG")
        calc.testStep("AC/ON ( 1 2 - 5 ) * ( 1 8 / ( 3 + 3 ) ) =", "21.", "", "DEG")
        calc.testStep("AC/ON ( 1 0 + 2 ) / ( 3 + 1 ) * ( 5 - 2 ) =", "9.", "", "DEG")
        calc.testStep("AC/ON 1 0 0 / ( 5 * ( 2 + 3 ) ) =", "4.", "", "DEG")
        calc.testStep("AC/ON ( 5 0 - ( 6 * 7 ) ) / ( 2 + 2 ) =", "2.", "", "DEG")
        calc.testStep("AC/ON ( 9 - 4 ) * ( 8 - 6 ) + ( 7 * ( 3 + 1 ) ) =", "38.", "", "DEG")
        calc.testStep("AC/ON ( 3 + 5 ) * ( 2 + 6 ) / 4 =", "16.", "", "DEG")
        calc.testStep("AC/ON ( 7 + 8 ) * ( 9 - 5 ) / ( 3 + 2 ) =", "12.", "", "DEG")
        calc.testStep("AC/ON ( 2 0 / ( 2 + 3 ) ) + ( 6 * ( 4 - 1 ) ) =", "22.", "", "DEG")
        calc.testStep("AC/ON ( 4 + 6 ) / 2 * ( 3 + 1 ) =", "20.", "", "DEG")
        calc.testStep("AC/ON 9 - ( 3 + 2 ) * ( 4 - 1 ) =", "-6.", "", "DEG")
        calc.testStep("AC/ON 1 / ( 2 + 3 ) =", "0.2", "", "DEG")
        calc.testStep("AC/ON ( 1 + 2 ) / ( 4 + 5 ) =", "0.333333333", "", "DEG")
        calc.testStep("AC/ON 2 y^x ( 1 + 2 ) =", "8.", "", "DEG")
        calc.testStep("AC/ON ( 2 + 3 ) y^x ( 1 + 1 ) =", "25.", "", "DEG")
        calc.testStep("AC/ON ( 2 + 1 ) y^x 2 + ( 3 * ( 4 + 1 ) ) =", "24.", "", "DEG")
    }

    @Test
    fun testMultipleParenthesesCalculations() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON ( ( 2 + 3 ) )", "5.", "", "DEG")
        calc.testStep("AC/ON ( ( 2 + 3 ) * ( 4 + 5 ) )", "45.", "", "DEG")
        calc.testStep("AC/ON ( ( ( 1 + 2 ) + 3 ) * ( 4 + ( 5 + 6 ) ) )", "90.", "", "DEG")
        calc.testStep("AC/ON 2 * ( ( 3 + 4 ) * ( 5 + ( 6 - 2 ) ) ) =", "126.", "", "DEG")
        calc.testStep("AC/ON ( ( 2 0 / ( 2 + 3 ) ) + ( 6 * ( 4 - 1 ) ) )", "22.", "", "DEG")
        calc.testStep("AC/ON ( ( 2 + 1 ) y^x 2 ) + ( 3 * ( 4 + 1 ) ) =", "24.", "", "DEG")
        calc.testStep("AC/ON ( ( ( ( ( 1 + 1 ) ) ) ) )", "2.", "", "DEG")
        calc.testStep("AC/ON ( 2 + ( ( 3 + 4 ) * ( 5 - 1 ) ) )", "30.", "", "DEG")
        calc.testStep("AC/ON ( ( 8 - ( 3 + 2 ) ) * ( 7 + ( 1 + 2 ) ) )", "30.", "", "DEG")
        calc.testStep("AC/ON 1 0 0 / ( ( 5 * ( 2 + 3 ) ) ) =", "4.", "", "DEG")
        calc.testStep("AC/ON 9 - ( ( 3 + 2 ) * ( 4 - 1 ) ) =", "-6.", "", "DEG")
    }

    @Test
    fun testAdditional() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON (", "0.", "", "DEG ()")
        calc.testStep("2", "2", "", "DEG ()")
        calc.testStep("*", "2.", "", "DEG ()")
        calc.testStep("(", "0.", "", "DEG ()")
        calc.testStep("3", "3", "", "DEG ()")
        calc.testStep("+", "3.", "", "DEG ()")
        calc.testStep("7", "7", "", "DEG ()")
        calc.testStep(")", "10.", "", "DEG ()")
        calc.testStep(")", "20.", "", "DEG")
        calc.testStep("=", "20.", "", "DEG")

        calc.testStep("AC/ON ( ( 2 + 3 ) * 7 )", "35.", "", "DEG")
        calc.testStep("=", "35.", "", "DEG")

        calc.testStep("AC/ON ( 2 * ( 3 + 7 ) )", "20.", "", "DEG")
        calc.testStep("=", "20.", "", "DEG")

        calc.testStep("AC/ON ( ( 2 + 3 ) )", "5.", "", "DEG")
        calc.testStep("=", "5.", "", "DEG")
        calc.testStep("=", "5.", "", "DEG")

        calc.testStep("AC/ON ( 2 * ( 3 + 7 ) )", "20.", "", "DEG")
        calc.testStep("x<>y", "2.", "", "DEG")

        calc.testStep("AC/ON ( ( 2 + 3 ) * 7 )", "35.", "", "DEG")
        calc.testStep("x<>y", "5.", "", "DEG")
    }
}
