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

class UnitTestConversions {

    @Test
    fun testMetricToEnglish() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 2 . 5 4 >in", "1.", "", "DEG")
        calc.testStep("AC/ON 3 . 7 8 5 4 1 1 7 8 4 >gal", "1.", "", "DEG")
        calc.testStep("AC/ON . 4 5 3 5 9 2 3 7 >lb", "1.", "", "DEG")
        calc.testStep("AC/ON 1 0 0 0 >lb", "2204.622622", "", "DEG")
        calc.testStep("AC/ON 1 >oz", "0.035273962", "", "DEG")
        calc.testStep("AC/ON 0 >°F", "32.", "", "DEG")
        calc.testStep("AC/ON 1 0 0 >°F", "212.", "", "DEG")
    }

    @Test
    fun testEnglishToMetric() {
        val calc = CalculatorCore()

        calc.testStep("1 >cm", "2.54", "", "DEG")
        calc.testStep("1 >l", "3.785411784", "", "DEG")
        calc.testStep("1 >kg", "0.45359237", "", "DEG")
        calc.testStep("1 >g", "28.34952313", "", "DEG")
        calc.testStep("3 2 >°C", "0.", "", "DEG")
        calc.testStep("AC/ON 2 1 2 >°C", "100.", "", "DEG")
    }
}
