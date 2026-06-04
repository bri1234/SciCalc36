/*
 * Ti36Calculator - A TI-36 calculator simulator for Android.
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

package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestLogarithm {
    @Test
    fun testLogarithm() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("1 5 . 2 3 log", "1.182699903", "", "DEG")
        calc.testStep("+ 1 2 . 4 5 log =", "2.277869255", "", "DEG")
        calc.testStep("AC/ON 2 10^x", "100.", "", "DEG")
        calc.testStep("- 1 1 x^2", "121.", "", "DEG")
        calc.testStep("=", "-21.", "", "DEG")
        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("1 5 . 2 3 ln", "2.723267167", "", "DEG")
        calc.testStep("+ 1 2 . 4 5 ln =", "5.24498779", "", "DEG")
        calc.testStep("AC/ON . 6 9 3", "0.693", "", "DEG")
        calc.testStep("e^x", "1.999705661", "", "DEG")
        calc.testStep("+ 1 =", "2.999705661", "", "DEG")

    }
}
