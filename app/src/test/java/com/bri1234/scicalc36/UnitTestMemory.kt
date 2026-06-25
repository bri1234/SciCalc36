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

class UnitTestMemory {

    @Test
    fun testMemory() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 4 2 STO", "42.", "", "DEG")
        calc.testStep("1", "42.", "", "DEG M")
        calc.testStep("CE/C", "0.", "", "DEG M")
        calc.testStep("AC/ON", "0.", "", "DEG")

        calc.testStep("AC/ON 4 2 STO 1", "42.", "", "DEG M")
        calc.testStep("1 2 STO 1", "12.", "", "DEG M")
        calc.testStep("0 STO 1", "0.", "", "DEG")

        // STO: store integer, decimal, and negative values in separate cells.
        calc.testStep("AC/ON 4 2 STO 1", "42.", "", "DEG M")
        calc.testStep("1 . 2 5 STO 2", "1.25", "", "DEG M")
        calc.testStep("7 +/- STO 3", "-7.", "", "DEG M")

        // RCL: recall each previously stored value.
        calc.testStep("RCL 1", "42.", "", "DEG M")
        calc.testStep("RCL 2", "1.25", "", "DEG M")
        calc.testStep("RCL 3", "-7.", "", "DEG M")

        // SUM: add positive and negative values to existing memory cells.
        calc.testStep("2 . 5 SUM 2", "2.5", "", "DEG M")
        calc.testStep("RCL 2", "3.75", "", "DEG M")
        calc.testStep("2 +/- SUM 1", "-2.", "", "DEG M")
        calc.testStep("RCL 1", "40.", "", "DEG M")

        // EXC: exchange the displayed value with a memory cell in both directions.
        calc.testStep("9 . 5 EXC 2", "3.75", "", "DEG M")
        calc.testStep("RCL 2", "9.5", "", "DEG M")
        calc.testStep("8 +/- EXC 3", "-7.", "", "DEG M")
        calc.testStep("RCL 3", "-8.", "", "DEG M")

    }

    @Test
    fun testMemoryUnusual() {
        val calc = CalculatorCore()

        calc.testStep("1 . 1 STO 1 2 . 2 RCL STO 2", "2.2", "", "DEG M")
        calc.testStep("RCL 1", "1.1", "", "DEG M")
        calc.testStep("RCL 2", "2.2", "", "DEG M")
    }
}
