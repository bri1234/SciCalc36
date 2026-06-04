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

class UnitTestDisplayIndicators {

    @Test
    fun testDisplayIndicators() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("4 2 STO 1", "42.", "", "DEG M")
        calc.testStep("AC/ON 2nd", "0.", "", "DEG 2nd")
        calc.testStep("AC/ON 3rd", "0.", "", "DEG 3rd")
        calc.testStep("AC/ON hyp", "0.", "", "DEG HYP")
        calc.testStep("AC/ON HEX", "0", "", "HEX")
        calc.testStep("AC/ON OCT", "0", "", "OCT")
        calc.testStep("AC/ON BIN", "0", "", "BIN")
        calc.testStep("AC/ON STAT1", "0.", "", "DEG STAT")
        calc.testStep("AC/ON STAT2", "0.", "", "DEG STAT")
        calc.testStep("AC/ON DRG", "0.", "", "RAD")
        calc.testStep("AC/ON DRG DRG", "0.", "", "GRAD")
        calc.testStep("AC/ON 5 x<>y 3 0 P>R", "4.330127019", "", "DEG x")
        calc.testStep("AC/ON 1 0 x<>y 8 R>P", "12.80624847", "", "DEG r")
        calc.testStep("AC/ON (", "0.", "", "DEG ()")
    }
}
