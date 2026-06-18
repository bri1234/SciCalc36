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

class UnitTestFractions {

    @Test
    fun testFractionsInput() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 5 5 ab/c", "55;", "", "DEG")
        calc.testStep("2", "55;2", "", "DEG")
        calc.testStep("4", "55;24", "", "DEG")
        calc.testStep("ab/c", "55_24;", "", "DEG")
        calc.testStep("3", "55_24;3", "", "DEG")
        calc.testStep("3", "55_24;33", "", "DEG")

        calc.testStep("AC/ON 6 ab/c 4 ab/c 6", "6_4;6", "", "DEG")
        calc.testStep("=", "6_2;3", "", "DEG")
    }

    @Test
    fun testFractionsConversion() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 5 5 ab/c 2 4", "55;24", "", "DEG")
        calc.testStep("F<>D", "2.291666667", "", "DEG")
        calc.testStep("F<>D", "2_7;24", "", "DEG")
    }

    @Test
    fun testFractionsToggle() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 3 0 ab/c 4", "30;4", "", "DEG")
        calc.testStep("d/c", "7_1;2", "", "DEG")
        calc.testStep("d/c", "15;2", "", "DEG")
        calc.testStep("d/c", "7_1;2", "", "DEG")
    }

    @Test
    fun testFractionsFunctions() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 1 ab/c 2", "1;2", "", "DEG")
        calc.testStep("x^2", "0.25", "", "DEG")

        calc.testStep("AC/ON 2 ab/c 5", "2;5", "", "DEG")
        calc.testStep("1/x", "2.5", "", "DEG")

        calc.testStep("AC/ON 2 ab/c 5", "2;5", "", "DEG")
        calc.testStep("y^x", "2;5", "", "DEG")
        calc.testStep("3 ab/c 2", "3;2", "", "DEG")
        calc.testStep("=", "0.252982213", "", "DEG")
    }

    @Test
    fun testFractionsCalc() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 1 ab/c 2", "1;2", "", "DEG")
        calc.testStep("x^2", "0.25", "", "DEG")
    }

    @Test
    fun testFractionsExchange() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 1 ab/c 2", "1;2", "", "DEG")
        calc.testStep("x<>y", "0.", "", "DEG")
        calc.testStep("3 ab/c 4", "3;4", "", "DEG")
        calc.testStep("x<>y", "1;2", "", "DEG")
        calc.testStep("x<>y", "3;4", "", "DEG")
    }

    @Test
    fun testFractionsStore() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 1 ab/c 2", "1;2", "", "DEG")
        calc.testStep("STO 1", "1;2", "", "DEG M")
        calc.testStep("3 ab/c 4", "3;4", "", "DEG M")
        calc.testStep("STO 2", "3;4", "", "DEG M")
        calc.testStep("RCL 1", "1;2", "", "DEG M")
        calc.testStep("RCL 2", "3;4", "", "DEG M")
    }
}

