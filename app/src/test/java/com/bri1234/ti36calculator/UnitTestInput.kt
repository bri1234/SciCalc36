package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestInput {
    @Test
    fun testInput() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("4", "4", "", "DEG")
        calc.testStep("2", "42", "", "DEG")
        calc.testStep("=", "42.", "", "DEG")

    }

    @Test
    fun testSecondThirdHyp() {
        val calc = CalculatorCore()

        calc.testStep("", "0.", "", "DEG")
        calc.testStep("2nd", "0.", "", "2nd", "DEG")
        calc.testStep("3rd", "0.", "", "3rd", "DEG")
        calc.testStep("2nd", "0.", "", "2nd", "DEG")
        calc.testStep("2nd", "0.", "", "DEG")
        calc.testStep("3rd", "0.", "", "3rd", "DEG")
        calc.testStep("3rd", "0.", "", "DEG")
        calc.testStep("HYP", "0.", "", "HYP", "DEG")
        calc.testStep("HYP", "0.", "", "DEG")
        calc.testStep("HYP 3rd", "0.", "", "3rd", "HYP", "DEG")
        calc.testStep("2nd", "0.", "", "2nd", "HYP", "DEG")
        calc.testStep("AC/ON 2nd HYP", "0.", "", "RAD")
        calc.testStep("3rd HYP", "0.", "", "GRAD")

    }

    @Test
    fun testUnusualInput() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 2", "2", "", "DEG")
        calc.testStep("*", "2.", "", "DEG")
        calc.testStep("*", "2.", "", "DEG")
        calc.testStep("*", "2.", "", "DEG")
        calc.testStep("5 =", "10.", "", "DEG")

    }

    @Test
    fun testSign() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON +/-", "0.", "", "DEG")
        calc.testStep("AC/ON . +/-", "-0.", "", "DEG")
        calc.testStep("AC/ON 3 +/-", "-3", "", "DEG")
        calc.testStep("AC/ON 0 +/-", "-0", "", "DEG")
        calc.testStep("AC/ON 1 8 0 sin +/-", "0.", "", "DEG")

        calc.testStep("AC/ON HEX 2 5 +/-", "FFFFFFFFdb", "", "HEX")
    }
}
