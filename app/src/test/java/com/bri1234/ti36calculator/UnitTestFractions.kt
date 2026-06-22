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
    fun testDecimalFractionPresentationToggle() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON . 7 5 F<>D", "3;4", "", "DEG")
        calc.testStep("F<>D", "0.75", "", "DEG")
        calc.testStep("F<>D", "3;4", "", "DEG")
    }

    @Test
    fun testMixedImproperPresentationToggle() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 7 . 5 F<>D", "7_1;2", "", "DEG")
        calc.testStep("d/c", "15;2", "", "DEG")
        calc.testStep("d/c", "7_1;2", "", "DEG")
    }

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
        calc.testStep("+/-", "-6_2;3", "", "DEG")

        calc.testStep("AC/ON 6 ab/c 4 ab/c 6 +/-", "-6_4;6", "", "DEG")
        calc.testStep("=", "-6_2;3", "", "DEG")

        calc.testStep("AC/ON 6 ab/c 4", "6;4", "", "DEG")
        calc.testStep("+/-", "-6;4", "", "DEG")
        calc.testStep("ab/c", "-6_4;", "", "DEG")
        calc.testStep("8", "-6_4;8", "", "DEG")

        calc.testStep("AC/ON 5 EE 7", "5", "07", "DEG")
        calc.testStep("ab/c", "5", "07", "DEG")

        calc.testStep("AC/ON 1 3 ab/c 4 DEC", "3_1;4", "", "DEG")
        calc.testStep("AC/ON 1 3 ab/c 4 HEX", "3", "", "HEX")
        calc.testStep("AC/ON 1 3 ab/c 4 OCT", "3", "", "OCT")
        calc.testStep("AC/ON 1 3 ab/c 4 BIN", "11", "", "BIN")

        calc.testStep("AC/ON 1 3 ab/c DEC", "Error  ", "", "DEG")
        calc.testStep("AC/ON 1 3 ab/c HEX", "Error  ", "", "DEG")
        calc.testStep("AC/ON 1 3 ab/c OCT", "Error  ", "", "DEG")
        calc.testStep("AC/ON 1 3 ab/c BIN", "Error  ", "", "DEG")

        calc.testStep("AC/ON 1 3 ab/c 2 ab/c DEC", "Error  ", "", "DEG")
        calc.testStep("AC/ON 1 3 ab/c 2 ab/c HEX", "Error  ", "", "DEG")
        calc.testStep("AC/ON 1 3 ab/c 2 ab/c OCT", "Error  ", "", "DEG")
        calc.testStep("AC/ON 1 3 ab/c 2 ab/c BIN", "Error  ", "", "DEG")

        calc.testStep("AC/ON 1 3 ab/c +", "Error  ", "", "DEG")
        calc.testStep("AC/ON 1 3 ab/c -", "Error  ", "", "DEG")
        calc.testStep("AC/ON 1 3 ab/c *", "Error  ", "", "DEG")
        calc.testStep("AC/ON 1 3 ab/c /", "Error  ", "", "DEG")

        calc.testStep("AC/ON 1 3 ab/c .", "13;", "", "DEG")
        calc.testStep("AC/ON 1 ab/c 2 ab/c .", "1_2;", "", "DEG")

        calc.testStep("AC/ON 1 ab/c 2 ab/c 3 ab/c 4", "1_2;34", "", "DEG")
        calc.testStep("AC/ON 1 ab/c 2 ab/c 3 ab/c 4", "1_2;34", "", "DEG")

        calc.testStep("AC/ON 1 ab/c 2 F<>D ENG", "500.", "-03", "DEG")
        calc.testStep("AC/ON 1 ab/c 2 ENG F<>D", "500.", "-03", "DEG")
        calc.testStep("AC/ON 1 ab/c 2 F<>D SCI", "5.", "-01", "DEG")
        calc.testStep("AC/ON 1 ab/c 2 SCI F<>D", "5.", "-01", "DEG")

        calc.testStep("AC/ON 1 ab/c 2 = + 5 =", "5_1;2", "", "DEG")

        calc.testStep("AC/ON 1 . ab/c", "1.", "", "DEG")
        calc.testStep("AC/ON 1 . 5 ab/c", "1.5", "", "DEG")
    }

    @Test
    fun testFractionsConversion() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 5 5 ab/c 2 4", "55;24", "", "DEG")
        calc.testStep("F<>D", "2.291666667", "", "DEG")
        calc.testStep("F<>D", "2_7;24", "", "DEG")

        calc.testStep("AC/ON 0 . 3 3 3 3 3 3 3 3 3 3 3 3", "0.333333333", "", "DEG")
        calc.testStep("F<>D", "1;3", "", "DEG")

        calc.testStep("AC/ON 0 . 1 2 5", "0.125", "", "DEG")
        calc.testStep("F<>D", "1;8", "", "DEG")
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
        calc.testStep("x^2", "1;4", "", "DEG")

        calc.testStep("AC/ON 2 ab/c 5", "2;5", "", "DEG")
        calc.testStep("1/x", "2_1;2", "", "DEG")

        calc.testStep("AC/ON 2 ab/c 5", "2;5", "", "DEG")
        calc.testStep("y^x", "2;5", "", "DEG")
        calc.testStep("3 ab/c 2", "3;2", "", "DEG")
        calc.testStep("=", "0.252982213", "", "DEG")
    }

    @Test
    fun testFractionsCalc() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 1 ab/c 2", "1;2", "", "DEG")
        calc.testStep("* 3", "3", "", "DEG")
        calc.testStep("=", "1_1;2", "", "DEG")

        calc.testStep("AC/ON 1 ab/c 2", "1;2", "", "DEG")
        calc.testStep("* 3 ab/c 4", "3;4", "", "DEG")
        calc.testStep("=", "3;8", "", "DEG")

        calc.testStep("AC/ON 1 ab/c 2", "1;2", "", "DEG")
        calc.testStep("/ 3", "3", "", "DEG")
        calc.testStep("=", "1;6", "", "DEG")

        calc.testStep("AC/ON 1 ab/c 2", "1;2", "", "DEG")
        calc.testStep("/ 3 ab/c 4", "3;4", "", "DEG")
        calc.testStep("=", "2;3", "", "DEG")

        calc.testStep("AC/ON 1 ab/c 2", "1;2", "", "DEG")
        calc.testStep("+ 3", "3", "", "DEG")
        calc.testStep("=", "3_1;2", "", "DEG")

        calc.testStep("AC/ON 1 ab/c 2", "1;2", "", "DEG")
        calc.testStep("+ 3 ab/c 4", "3;4", "", "DEG")
        calc.testStep("=", "1_1;4", "", "DEG")

        calc.testStep("AC/ON 1 ab/c 2", "1;2", "", "DEG")
        calc.testStep("- 3", "3", "", "DEG")
        calc.testStep("=", "-2_1;2", "", "DEG")

        calc.testStep("AC/ON 1 ab/c 2", "1;2", "", "DEG")
        calc.testStep("- 3 ab/c 4", "3;4", "", "DEG")
        calc.testStep("=", "-1;4", "", "DEG")

    }

    @Test
    fun testFractionsParenthesesAndRepeat() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 2 * ( 1 ab/c 2 + 1 ab/c 4 ) =", "1_1;2", "", "DEG")

        calc.testStep("AC/ON 1 ab/c 2 + 1 ab/c 4 =", "3;4", "", "DEG")
        calc.testStep("=", "1", "", "DEG")
        calc.testStep("=", "1_1;4", "", "DEG")
    }

    @Test
    fun testFractionsInvalidInput() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 1 ab/c 0 =", "Error  ", "", "DEG")
        calc.testStep("AC/ON 1 ab/c =", "Error  ", "", "DEG")
        calc.testStep("AC/ON 1 ab/c 2 ab/c =", "Error  ", "", "DEG")
    }

    @Test
    fun testFractionsBackspace() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 1 2 ab/c 3 4 ->", "12;3", "", "DEG")
        calc.testStep("->", "12;", "", "DEG")
        calc.testStep("->", "12", "", "DEG")

        calc.testStep("AC/ON 1 ab/c 2 ab/c 3 ->", "1_2;", "", "DEG")
        calc.testStep("->", "1;2", "", "DEG")
    }

    @Test
    fun testNegativeFractionArithmetic() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 1 ab/c 2 +/- + 3 ab/c 4 =", "1;4", "", "DEG")
        calc.testStep("AC/ON 1 ab/c 2 - 3 ab/c 4 =", "-1;4", "", "DEG")
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

    @Test
    fun testFractionsMemorySumAndExchange() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 1 ab/c 2 STO 1", "1;2", "", "DEG M")
        calc.testStep("1 ab/c 4 SUM 1", "1;4", "", "DEG M")
        calc.testStep("RCL 1", "3;4", "", "DEG M")

        calc.testStep("1 ab/c 2 EXC 1", "3;4", "", "DEG M")
        calc.testStep("RCL 1", "1;2", "", "DEG M")
    }

    @Test
    fun testFractionsOverflow() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 1 2 3 4 5 ab/c 6 7 8 9", "12345;678", "", "DEG")
        calc.testStep("=", "18_47;226", "", "DEG")
        calc.testStep("* 1 2 =", "218_56;113", "", "DEG")
        calc.testStep("* 1 2 =", "2621.946903", "", "DEG")
        calc.testStep("/ 1 2 =", "218.4955752", "", "DEG")
        calc.testStep("F<>D =", "218_56;113", "", "DEG")

    }

    @Test
    fun testFractionsMisc() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 8 ab/c 1 0 x^2", "16;25", "", "DEG")
        calc.testStep("AC/ON 3 ab/c 4 1/x", "1_1;3", "", "DEG")

    }

}
