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

class UnitTestRectangularPolar {

    @Test
    fun testRectangularToPolar() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("1 0 x<>y 8", "8", "", "DEG")
        calc.testStep("R>P", "12.80624847", "", "DEG r")
        calc.testStep("x<>y", "38.65980825", "", "DEG")
        calc.testStep("x<>y", "12.80624847", "", "DEG r")

        calc.testStep("AC/ON DRG", "0.", "", "RAD")
        calc.testStep("1 0 x<>y 8", "8", "", "RAD")
        calc.testStep("R>P", "12.80624847", "", "RAD r")
        calc.testStep("x<>y", "0.674740942", "", "RAD")
        calc.testStep("x<>y", "12.80624847", "", "RAD r")

        calc.testStep("AC/ON DRG DRG", "0.", "", "GRAD")
        calc.testStep("1 0 x<>y 8", "8", "", "GRAD")
        calc.testStep("R>P", "12.80624847", "", "GRAD r")
        calc.testStep("x<>y", "42.9553425", "", "GRAD")
        calc.testStep("x<>y", "12.80624847", "", "GRAD r")
    }

    @Test
    fun testPolarToRectangular() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("5 x<>y 3 0", "30", "", "DEG")
        calc.testStep("P>R", "4.330127019", "", "DEG x")
        calc.testStep("x<>y", "2.5", "", "DEG")
        calc.testStep("x<>y", "4.330127019", "", "DEG x")

        calc.testStep("AC/ON DRG", "0.", "", "RAD")
        calc.testStep("5 x<>y 3 0", "30", "", "RAD")
        calc.testStep("P>R", "0.771257249", "", "RAD x")
        calc.testStep("x<>y", "-4.94015812", "", "RAD")
        calc.testStep("x<>y", "0.771257249", "", "RAD x")

        calc.testStep("AC/ON DRG DRG", "0.", "", "GRAD")
        calc.testStep("5 x<>y 3 0", "30", "", "GRAD")
        calc.testStep("P>R", "4.455032621", "", "GRAD x")
        calc.testStep("x<>y", "2.269952499", "", "GRAD")
        calc.testStep("x<>y", "4.455032621", "", "GRAD x")
    }
}