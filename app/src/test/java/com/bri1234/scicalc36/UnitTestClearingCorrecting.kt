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

class UnitTestClearingCorrecting {

    @Test
    fun testBack() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("1 2 3 4 5", "12345", "", "DEG")
        calc.testStep("->", "1234", "", "DEG")
        calc.testStep("-> ->", "12", "", "DEG")
        calc.testStep("->", "1", "", "DEG")
        calc.testStep("->", "0", "", "DEG")
        calc.testStep("->", "0", "", "DEG")
        calc.testStep("1 2 3 4 5", "12345", "", "DEG")
        calc.testStep("-> -> -> -> -> ->", "0", "", "DEG")

        calc.testStep("1 2 3 EE", "123", "00", "DEG")
        calc.testStep("1 2", "123", "12", "DEG")
        calc.testStep("->", "123", "12", "DEG")
        calc.testStep("->", "123", "12", "DEG")

    }

    @Test
    fun testAcOn() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")

        calc.testStep("1 2 3 4 5 AC/ON", "0.", "", "DEG")

        calc.testStep("2 + 3 AC/ON", "0.", "", "DEG")
        calc.testStep("4 =", "4.", "", "DEG")
        calc.testStep("( 2 + 3 AC/ON", "0.", "", "DEG")

        calc.testStep("9 / 0 =", "Error  ", "", "DEG")
        calc.testStep("AC/ON", "0.", "", "DEG")

        calc.testStep("4 2 STO 1", "42.", "", "DEG M")
        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("RCL 1", "0.", "", "DEG")

        calc.testStep("STAT1 4 S+", "1.", "", "DEG STAT")
        calc.testStep("AC/ON", "0.", "", "DEG")

        calc.testStep("DRG", "0.", "", "RAD")
        calc.testStep("AC/ON", "0.", "", "DEG")

        calc.testStep("FIX 2 1 / 3 =", "0.33", "", "DEG")
        calc.testStep("AC/ON 1 / 3 =", "0.333333333", "", "DEG")

        calc.testStep("1 2 3 0 0 0 SCI", "1.23", "05", "DEG")
        calc.testStep("AC/ON 1 2 3 0 0 0 =", "123000.", "", "DEG")

        calc.testStep("1 2 3 0 0 0 ENG", "123.", "03", "DEG")
        calc.testStep("AC/ON 1 2 3 0 0 0 =", "123000.", "", "DEG")
    }

    @Test
    fun testMem() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("4 2 STO 3", "42.", "", "DEG M")
        calc.testStep("2 4 STO 1", "24.", "", "DEG M")
        calc.testStep("0 STO 3", "0.", "", "DEG M")
        calc.testStep("0 STO 1", "0.", "", "DEG")
    }

    @Test
    fun testFloFix() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")

        calc.testStep("1 2 3 4 5 ENG", "12.345", "03", "DEG")
        calc.testStep("FLO", "12345.", "", "DEG")

        calc.testStep("FIX 2", "12345.00", "", "DEG")
        calc.testStep("FIX .", "12345.", "", "DEG")
    }

    @Test
    fun testStat() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON STAT1 5 FRQ 7 S+", "7.", "", "DEG STAT")
        calc.testStep("Sx", "35.", "", "DEG STAT")
        calc.testStep("CSR", "35.", "", "DEG STAT")
        calc.testStep("Sx", "0.", "", "DEG STAT")

        calc.testStep("AC/ON STAT2 5 x<>y 9 FRQ 7 S+", "7.", "", "DEG STAT")
        calc.testStep("Sx", "35.", "", "DEG STAT")
        calc.testStep("Sy", "63.", "", "DEG STAT")
        calc.testStep("CSR", "63.", "", "DEG STAT")
        calc.testStep("Sx", "0.", "", "DEG STAT")
        calc.testStep("Sy", "0.", "", "DEG STAT")
    }

    @Test
    fun testCeC() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")

        calc.testStep("3", "3", "", "DEG")
        calc.testStep("*", "3.", "", "DEG")
        calc.testStep("7", "7", "", "DEG")
        calc.testStep("CE/C", "0.", "", "DEG")
        calc.testStep("5", "5", "", "DEG")
        calc.testStep("=", "15.", "", "DEG")

        calc.testStep("3 * 7", "7", "", "DEG")
        calc.testStep("CE/C", "0.", "", "DEG")
        calc.testStep("CE/C", "0.", "", "DEG")
        calc.testStep("5", "5", "", "DEG")
        calc.testStep("=", "5.", "", "DEG")

        calc.testStep("AC/ON LN", "Error  ", "", "DEG")
        calc.testStep("CE/C", "0.", "", "DEG")

        calc.testStep("AC/ON 8 + 9 * 0 LN", "Error  ", "", "DEG")
        calc.testStep("CE/C =", "0.", "", "DEG")

        calc.testStep("AC/ON 8 + 9 * 3 +/- SQRT", "Error  ", "", "DEG")
        calc.testStep("CE/C =", "0.", "", "DEG")

        calc.testStep("AC/ON 5 STO", "5.", "", "DEG")
        calc.testStep("CE/C", "0.", "", "DEG")

        calc.testStep("AC/ON 5 STO 2 + 3 =", "8.", "", "DEG M")
        calc.testStep("AC/ON 5 STO CE/C 2 + 3 =", "5.", "", "DEG")

        calc.testStep("AC/ON 6 STO 2 8 + RCL 2 =", "14.", "", "DEG M")
        calc.testStep("AC/ON 6 STO CE/C 2 8 + RCL 2 =", "28.", "", "DEG")

        calc.testStep("AC/ON FIX 3", "0.000", "", "DEG")
        calc.testStep("CE/C", "0.000", "", "DEG")
        calc.testStep("CE/C", "0.000", "", "DEG")
        calc.testStep("AC/ON FIX CE/C 3", "3", "", "DEG")

        calc.testStep("AC/ON 2 CONST SIN", "299792458.", "", "DEG")
        calc.testStep("AC/ON 2 CONST CE/C SIN", "0.", "", "DEG")

        calc.testStep("AC/ON LN", "Error  ", "", "DEG")
        calc.testStep("2nd", "Error  ", "", "DEG")
        calc.testStep("CE/C", "0.", "", "DEG")

        calc.testStep("AC/ON LN", "Error  ", "", "DEG")
        calc.testStep("3rd", "Error  ", "", "DEG")
        calc.testStep("CE/C", "0.", "", "DEG")
    }

    @Test
    fun testCeCCeC() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")

        calc.testStep("3 * 7 CE/C CE/C 5 =", "5.", "", "DEG")

        calc.testStep("1 2 3 CE/C CE/C", "0.", "", "DEG")

        calc.testStep("2 + 3 CE/C CE/C", "0.", "", "DEG")
        calc.testStep("4 =", "4.", "", "DEG")

        calc.testStep("2 + 3 * 4 CE/C CE/C", "0.", "", "DEG")
        calc.testStep("5 =", "5.", "", "DEG")

        calc.testStep("( 2 + 3", "3", "", "DEG ()")
        calc.testStep("CE/C", "0.", "", "DEG ()")
        calc.testStep("CE/C", "0.", "", "DEG")
        calc.testStep("4 =", "4.", "", "DEG")

        calc.testStep("AC/ON HEX A CE/C CE/C", "0", "", "HEX")
        calc.testStep("F", "F", "", "HEX")

        calc.testStep("AC/ON FIX 2 1 / 3 =", "0.33", "", "DEG")
        calc.testStep("CE/C CE/C", "0.00", "", "DEG")
        calc.testStep("1 / 3 =", "0.33", "", "DEG")

        calc.testStep("AC/ON DRG 1 2 CE/C CE/C", "0.", "", "RAD")
        calc.testStep("9 0 sin", "0.893996664", "", "RAD")

        calc.testStep("AC/ON 4 2 STO 1", "42.", "", "DEG M")
        calc.testStep("CE/C CE/C", "0.", "", "DEG M")
        calc.testStep("RCL 1", "42.", "", "DEG M")

        calc.testStep("AC/ON STAT1 5 FRQ 7 S+", "7.", "", "DEG STAT")
        calc.testStep("CE/C CE/C", "0.", "", "DEG STAT")
        calc.testStep("Sx", "35.", "", "DEG STAT")

    }
}
