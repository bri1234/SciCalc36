package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestTrigonometric {

    @Test
    fun testTrigonometric() {
        val calc = CalculatorCore()

        calc.testStep("", "0.", "", "DEG")
        calc.testStep("9 0 sin", "1.", "", "DEG")
        calc.testStep("- 3 0 cos", "0.866025404", "", "DEG")
        calc.testStep("=", "0.133974596", "", "DEG")
        calc.testStep("1 asin", "90.", "", "DEG")
        calc.testStep("- . 5 =", "89.5", "", "DEG")
    }

    @Test
    fun testTrigonometricSpecialCases() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 0 sin", "0.", "", "DEG")
        calc.testStep("AC/ON 9 0 sin", "1.", "", "DEG")
        calc.testStep("AC/ON 1 8 0 sin", "0.", "", "DEG")
        calc.testStep("AC/ON 2 7 0 sin", "-1.", "", "DEG")
        calc.testStep("AC/ON 3 6 0 sin", "0.", "", "DEG")

        calc.testStep("AC/ON 0 cos", "1.", "", "DEG")
        calc.testStep("AC/ON 9 0 cos", "0.", "", "DEG")
        calc.testStep("AC/ON 1 8 0 cos", "-1.", "", "DEG")
        calc.testStep("AC/ON 2 7 0 cos", "0.", "", "DEG")
        calc.testStep("AC/ON 3 6 0 cos", "1.", "", "DEG")

        calc.testStep("AC/ON 0 tan", "0.", "", "DEG")
        calc.testStep("AC/ON 9 0 tan", "Error  ", "", "DEG")
        calc.testStep("AC/ON 1 8 0 tan", "0.", "", "DEG")
        calc.testStep("AC/ON 2 7 0 tan", "Error  ", "", "DEG")
        calc.testStep("AC/ON 3 6 0 tan", "0.", "", "DEG")

        calc.testStep("AC/ON 1 +/- asin", "-90.", "", "DEG")
        calc.testStep("AC/ON 0 asin", "0.", "", "DEG")
        calc.testStep("AC/ON 1 asin", "90.", "", "DEG")

        calc.testStep("AC/ON 1 +/- acos", "180.", "", "DEG")
        calc.testStep("AC/ON 0 acos", "90.", "", "DEG")
        calc.testStep("AC/ON 1 acos", "0.", "", "DEG")

        calc.testStep("AC/ON 1 +/- atan", "-45.", "", "DEG")
        calc.testStep("AC/ON 0 atan", "0.", "", "DEG")
        calc.testStep("AC/ON 1 atan", "45.", "", "DEG")
    }

    @Test
    fun testSin() {
        val calc = CalculatorCore()

        calc.testStep("", "0.", "", "DEG")
        calc.testStep("3 2 sin", "0.529919264", "", "DEG")
        calc.testStep("4 2 sin asin", "42.", "", "DEG")
        calc.testStep("DRG", "42.", "", "RAD")
        calc.testStep("3 . 2 sin", "-0.058374143", "", "RAD")
        calc.testStep("1 . 1 +/- sin asin", "-1.1", "", "RAD")
        calc.testStep("DRG", "-1.1", "", "GRAD")
        calc.testStep("3 3 . 2 sin", "0.498185105", "", "GRAD")
        calc.testStep("4 2 . 5 +/- sin asin", "-42.5", "", "GRAD")

    }

    @Test
    fun testCos() {
        val calc = CalculatorCore()

        calc.testStep("", "0.", "", "DEG")
        calc.testStep("3 2 cos", "0.848048096", "", "DEG")
        calc.testStep("4 2 cos acos", "42.", "", "DEG")
        calc.testStep("DRG", "42.", "", "RAD")
        calc.testStep("3 . 2 cos", "-0.998294776", "", "RAD")
        calc.testStep("1 . 1 +/- cos acos", "1.1", "", "RAD")
        calc.testStep("DRG", "1.1", "", "GRAD")
        calc.testStep("3 3 . 2 cos", "0.867070701", "", "GRAD")
        calc.testStep("4 2 . 5 +/- cos acos", "42.5", "", "GRAD")

    }

    @Test
    fun testTan() {
        val calc = CalculatorCore()

        calc.testStep("", "0.", "", "DEG")
        calc.testStep("3 2 tan", "0.624869352", "", "DEG")
        calc.testStep("4 2 tan atan", "42.", "", "DEG")
        calc.testStep("DRG", "42.", "", "RAD")
        calc.testStep("3 . 2 tan", "0.058473854", "", "RAD")
        calc.testStep("1 . 1 +/- tan atan", "-1.1", "", "RAD")
        calc.testStep("DRG", "-1.1", "", "GRAD")
        calc.testStep("3 3 . 2 tan", "0.574561111", "", "GRAD")
        calc.testStep("4 2 . 5 +/- tan atan", "-42.5", "", "GRAD")

    }
}
