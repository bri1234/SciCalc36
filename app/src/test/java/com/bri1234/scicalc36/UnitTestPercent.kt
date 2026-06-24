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

class UnitTestPercent {
    @Test
    fun testPercent() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 2 5 0 * 5 %", "0.05", "", "DEG")
        calc.testStep("=", "12.5", "", "DEG")
        calc.testStep("AC/ON 2 5 0 / 5 %", "0.05", "", "DEG")
        calc.testStep("=", "5000.", "", "DEG")
        calc.testStep("2 5 0 + 5 %", "12.5", "", "DEG")
        calc.testStep("=", "262.5", "", "DEG")
        calc.testStep("2 5 0 - 5 %", "12.5", "", "DEG")
        calc.testStep("=", "237.5", "", "DEG")

    }
}
