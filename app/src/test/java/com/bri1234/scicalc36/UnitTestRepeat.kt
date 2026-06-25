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

class UnitTestRepeat {

    @Test
    fun testRepeat1() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("2 * PI =", "6.283185307", "", "DEG")
        calc.testStep("4 =", "12.56637061", "", "DEG")
        calc.testStep("8 =", "25.13274123", "", "DEG")

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("2 / 6 =", "0.333333333", "", "DEG")
        calc.testStep("4 =", "0.666666667", "", "DEG")
        calc.testStep("8 =", "1.333333333", "", "DEG")

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("5 + 7 =", "12.", "", "DEG")
        calc.testStep("3 =", "10.", "", "DEG")
        calc.testStep("9 =", "16.", "", "DEG")

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("5 - 7 =", "-2.", "", "DEG")
        calc.testStep("3 =", "-4.", "", "DEG")
        calc.testStep("9 =", "2.", "", "DEG")
    }

    @Test
    fun testRepeat2() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("5 + 6 =", "11.", "", "DEG")
        calc.testStep("=", "17.", "", "DEG")
        calc.testStep("=", "23.", "", "DEG")

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("2 3 - 5 =", "18.", "", "DEG")
        calc.testStep("=", "13.", "", "DEG")
        calc.testStep("=", "8.", "", "DEG")

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("5 * 3 =", "15.", "", "DEG")
        calc.testStep("=", "45.", "", "DEG")
        calc.testStep("=", "135.", "", "DEG")

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("2 / 4 =", "0.5", "", "DEG")
        calc.testStep("=", "0.125", "", "DEG")
        calc.testStep("=", "0.03125", "", "DEG")

    }

}

