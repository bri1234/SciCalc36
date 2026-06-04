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

class UnitTestHyperbolic {
    @Test
    fun testHyperbolic() {
        val calc = CalculatorCore()

        calc.testStep("5 hyp", "5", "", "HYP DEG")
        calc.testStep("sin", "74.20321058", "", "DEG")
        calc.testStep("+ 2 =", "76.20321058", "", "DEG")
        calc.testStep("5 hyp", "5", "", "HYP DEG")
        calc.testStep("asin", "2.312438341", "", "DEG")
        calc.testStep("+ 2 =", "4.312438341", "", "DEG")
        calc.testStep("4 hyp", "4", "", "HYP DEG")
        calc.testStep("cos", "27.30823284", "", "DEG")
        calc.testStep("+ 2 =", "29.30823284", "", "DEG")
        calc.testStep("4 hyp", "4", "", "HYP DEG")
        calc.testStep("acos", "2.063437069", "", "DEG")
        calc.testStep("+ 2 =", "4.063437069", "", "DEG")
        calc.testStep("4 hyp", "4", "", "HYP DEG")
        calc.testStep("tan", "0.9993293", "", "DEG")
        calc.testStep("+ 2 =", "2.9993293", "", "DEG")
        calc.testStep(". 6 hyp", "0.6", "", "HYP DEG")
        calc.testStep("atan", "0.693147181", "", "DEG")
        calc.testStep("+ 2 =", "2.693147181", "", "DEG")
        calc.testStep("DRG 5 hyp", "5", "", "HYP RAD")
        calc.testStep("sin", "74.20321058", "", "RAD")
        calc.testStep("+ 2 =", "76.20321058", "", "RAD")
        calc.testStep("DRG 5 hyp", "5", "", "HYP GRAD")
        calc.testStep("sin", "74.20321058", "", "GRAD")
        calc.testStep("+ 2 =", "76.20321058", "", "GRAD")

    }
}
