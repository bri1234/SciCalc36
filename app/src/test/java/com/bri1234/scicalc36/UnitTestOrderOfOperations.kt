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

class UnitTestOrderOfOperations {

    @Test
    fun testParenthesesHaveHighestPriority() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 2 * ( 3 + 4 ) =", "14.", "", "DEG")
        calc.testStep("AC/ON 2 + ( 3 * 4 ) =", "14.", "", "DEG")
        calc.testStep("AC/ON ( 2 + 3 ) y^x 2 =", "25.", "", "DEG")
    }

    @Test
    fun testFunctionsHavePriorityOverBinaryOperations() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 1 + 3 0 sin * 4 =", "3.", "", "DEG")
        calc.testStep("AC/ON 1 + 0 hyp cos * 4 =", "5.", "", "DEG")
        calc.testStep("AC/ON 2 + 3 x^2 * 4 =", "38.", "", "DEG")
        calc.testStep("AC/ON 5 + 4 sqrt * 3 =", "11.", "", "DEG")
        calc.testStep("AC/ON 5 + 8 cbrt * 3 =", "11.", "", "DEG")
        calc.testStep("AC/ON 5 + 3 x! * 2 =", "17.", "", "DEG")
        calc.testStep("AC/ON 5 + 4 1/x * 8 =", "7.", "", "DEG")
        calc.testStep("AC/ON 1 + 1 8 0 DRG> * 2 =", "7.283185307", "", "RAD")
        calc.testStep("AC/ON 5 x<>y 2 nCr * 3 + 1 =", "31.", "", "DEG")
        calc.testStep("AC/ON 5 x<>y 2 nPr + 1 =", "21.", "", "DEG")
        calc.testStep("AC/ON 5 0 % * 4 + 2 =", "4.", "", "DEG")
        calc.testStep("AC/ON 1 + 1 0 0 log * 3 =", "7.", "", "DEG")
        calc.testStep("AC/ON 2 + 3 +/- * 4 =", "-10.", "", "DEG")
        calc.testStep("AC/ON 1 >cm * 3 + 1 =", "8.62", "", "DEG")
        calc.testStep("AC/ON DEC 5 + 3 NOT * 2 =", "-3.", "", "DEG")
    }

    @Test
    fun testUniversalPowersAndRootsHavePriorityOverMultiplicationAndAddition() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 2 + 3 y^x 2 * 4 =", "38.", "", "DEG")
        calc.testStep("AC/ON 8 yrootx 3 * 5 + 1 =", "11.", "", "DEG")
    }

    @Test
    fun testMultiplicationAndDivisionHavePriorityOverAdditionAndSubtraction() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 2 + 3 * 4 - 8 / 2 =", "10.", "", "DEG")
        calc.testStep("AC/ON 2 0 - 1 2 / 3 + 2 * 5 =", "26.", "", "DEG")
    }

    @Test
    fun testAdditionAndSubtractionHavePriorityOverLogicalAnd() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON DEC 1 2 AND 1 0 - 3 =", "4.", "", "DEG")
        calc.testStep("AC/ON DEC 1 2 + 3 AND 1 0 =", "10.", "", "DEG")
    }

    @Test
    fun testLogicalAndHasPriorityOverOrXorXnor() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON DEC 1 2 OR 1 0 AND 3 =", "14.", "", "DEG")
        calc.testStep("AC/ON DEC 1 2 XOR 1 0 AND 3 =", "14.", "", "DEG")
        calc.testStep("AC/ON DEC 1 2 XNOR 1 0 AND 3 =", "-15.", "", "DEG")
    }

    @Test
    fun testLogicalOrXorXnorHaveSamePriority() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON DEC 1 2 OR 3 XOR 5 =", "10.", "", "DEG")
        calc.testStep("AC/ON DEC 1 2 XOR 3 OR 5 =", "15.", "", "DEG")
        calc.testStep("AC/ON DEC 1 2 OR 3 XNOR 5 =", "-11.", "", "DEG")
    }

    @Test
    fun testEqualsEvaluatesRemainingOperations() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 2 + 3", "3", "", "DEG")
        calc.testStep("=", "5.", "", "DEG")
        calc.testStep("AC/ON DEC 1 2 AND 1 0", "10", "", "DEG")
        calc.testStep("=", "8.", "", "DEG")
    }

}
