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

class UnitTestAngleUnit {
    @Test
    fun testAngleUnitCycle() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("4 5 SIN", "0.707106781", "", "DEG")
        calc.testStep("4 5 DRG", "45", "", "RAD")
        calc.testStep("SIN", "0.850903525", "", "RAD")
        calc.testStep("CE/C", "0.", "", "RAD")
        calc.testStep("4 5 DRG", "45", "", "GRAD")
        calc.testStep("SIN", "0.649448048", "", "GRAD")
        calc.testStep("DRG", "0.649448048", "", "DEG")
        calc.testStep("DRG", "0.649448048", "", "RAD")
        calc.testStep("AC/ON", "0.", "", "DEG")

    }

    @Test
    fun testAngleUnitCycleConvert() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("4 5 SIN", "0.707106781", "", "DEG")
        calc.testStep("4 5 DRG>", "0.785398163", "", "RAD")
        calc.testStep("SIN", "0.707106781", "", "RAD")
        calc.testStep("CE/C", "0.", "", "RAD")
        calc.testStep("4 5 DRG>", "2864.788976", "", "GRAD")
        calc.testStep("SIN", "0.850903525", "", "GRAD")
        calc.testStep("DRG>", "0.765813172", "", "DEG")
        calc.testStep("DRG>", "0.013365961", "", "RAD")
        calc.testStep("AC/ON", "0.", "", "DEG")

    }
}
