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

class UnitTestStat1 {

    @Test
    fun testStat1DegenerateData() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON STAT1 x_", "Error  ", "", "DEG STAT")
        calc.testStep("AC/ON STAT1 4 S+ Sxn-1", "Error  ", "", "DEG STAT")
    }

    @Test
    fun testStat1() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("STAT1", "0.", "", "DEG STAT")
        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("STAT1", "0.", "", "DEG STAT")
        calc.testStep("4 S+", "1.", "", "DEG STAT")
        calc.testStep("+ 5 =", "6.", "", "DEG STAT")
        calc.testStep("3 S+", "2.", "", "DEG STAT")
        calc.testStep("2 S+", "3.", "", "DEG STAT")
        calc.testStep("+ 5 =", "8.", "", "DEG STAT")
        calc.testStep("1 S+", "4.", "", "DEG STAT")
        calc.testStep("Sx", "10.", "", "DEG STAT")
        calc.testStep("Sx2", "30.", "", "DEG STAT")
        calc.testStep("n", "4.", "", "DEG STAT")
        calc.testStep("x_", "2.5", "", "DEG STAT")
        calc.testStep("Sxn", "1.118033989", "", "DEG STAT")
        calc.testStep("Sxn-1", "1.290994449", "", "DEG STAT")
        calc.testStep("8 S-", "3.", "", "DEG STAT")
        calc.testStep("9 S-", "2.", "", "DEG STAT")
        calc.testStep("Sx", "-7.", "", "DEG STAT")
        calc.testStep("Sx2", "-115.", "", "DEG STAT")
        calc.testStep("CSR Sx", "0.", "", "DEG STAT")
        calc.testStep("Sx2", "0.", "", "DEG STAT")

        calc.testStep("AC/ON 5", "5", "", "DEG")
        calc.testStep("STAT1", "5.", "", "DEG STAT")

        calc.testStep("AC/ON 5", "5", "", "DEG")
        calc.testStep("STAT2", "5.", "", "DEG STAT")
    }

    @Test
    fun testStat1Frq() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("STAT1", "0.", "", "DEG STAT")
        calc.testStep("3 . 1 4 FRQ", "Fr 00", "", "DEG STAT")
        calc.testStep("4", "Fr 04", "", "DEG STAT")
        calc.testStep("2", "Fr 42", "", "DEG STAT")
        calc.testStep("S+", "42.", "", "DEG STAT")
        calc.testStep("Sx", "131.88", "", "DEG STAT")
        calc.testStep("Sx2", "414.1032", "", "DEG STAT")
        calc.testStep("2 . 7 1 FRQ 1", "Fr 01", "", "DEG STAT")
        calc.testStep("2", "Fr 12", "", "DEG STAT")
        calc.testStep("S-", "30.", "", "DEG STAT")
        calc.testStep("Sx", "99.36", "", "DEG STAT")
        calc.testStep("Sx2", "325.974", "", "DEG STAT")
        calc.testStep("CE/C Sx", "99.36", "", "DEG STAT")
        calc.testStep("AC/ON Sx", "Error  ", "", "DEG")
        calc.testStep("AC/ON STAT1 3 . 1 4 FRQ 8 6 SIN", "0.05477591", "", "DEG STAT")
        calc.testStep("AC/ON STAT1 FRQ +/-", "0.", "", "DEG STAT")
        calc.testStep("AC/ON FRQ", "Error  ", "", "DEG")

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("STAT1", "0.", "", "DEG STAT")
        calc.testStep("1", "1", "", "DEG STAT")
        calc.testStep(".", "1.", "", "DEG STAT")
        calc.testStep("2", "1.2", "", "DEG STAT")
        calc.testStep("EE", "1.2", "00", "DEG STAT")
        calc.testStep("3", "1.2", "03", "DEG STAT")
        calc.testStep("FRQ", "Fr 00", "", "DEG STAT")
        calc.testStep("5", "Fr 05", "", "DEG STAT")
        calc.testStep("S+", "5.", "", "DEG STAT")
        calc.testStep("Sx", "6000.", "", "DEG STAT")

    }
    @Test
    fun testStat1Invalid() {
        val calc = CalculatorCore()

        calc.testStep("STAT1 1 . 1 FRQ 1 1 S+", "11.", "", "DEG STAT")
        calc.testStep("2", "2", "", "DEG STAT")
        calc.testStep(". 2 FRQ 3 S-", "8.", "", "DEG STAT")
        calc.testStep("3 . 3 FRQ 1 3 S-", "Error  ", "", "DEG STAT")
    }

    @Test
    fun testStat1DefaultFrequency() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON STAT1 4 S+", "1.", "", "DEG STAT")
        calc.testStep("Sx", "4.", "", "DEG STAT")
    }

    @Test
    fun testStat1ZeroFrequency() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON STAT1 4 S+", "1.", "", "DEG STAT")
        calc.testStep("5 FRQ 0 S+", "Error  ", "", "DEG STAT")

        calc.testStep("AC/ON STAT1 4 S+", "1.", "", "DEG STAT")
        calc.testStep("4 FRQ 0 S-", "Error  ", "", "DEG STAT")
    }

    @Test
    fun testStat1FromManual() {
        val calc = CalculatorCore()

        calc.testStep("STAT1", "0.", "", "DEG STAT")
        calc.testStep("CSR", "0.", "", "DEG STAT")
        calc.testStep("4 5 S+", "1.", "", "DEG STAT")
        calc.testStep("5 5 FRQ 3 S+", "4.", "", "DEG STAT")
        calc.testStep("6 0 S+", "5.", "", "DEG STAT")
        calc.testStep("8 S+", "6.", "", "DEG STAT")
        calc.testStep("8 S-", "5.", "", "DEG STAT")
        calc.testStep("8 0 S+", "6.", "", "DEG STAT")
        calc.testStep("Sx", "350.", "", "DEG STAT")
        calc.testStep("x_", "58.33333333", "", "DEG STAT")
        calc.testStep("Sx2", "21100.", "", "DEG STAT")
        calc.testStep("Sxn", "10.67187373", "", "DEG STAT")
        calc.testStep("Sxn-1", "11.69045194", "", "DEG STAT")

    }
}
