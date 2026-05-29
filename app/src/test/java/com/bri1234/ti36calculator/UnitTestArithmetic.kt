package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestArithmetic {

    @Test
    fun testArithmetic() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 6 0 + 5 * 1 2 =", "120.", "", "DEG")
        calc.testStep("2", "2", "", "DEG")
        calc.testStep("+", "2.", "", "DEG")
        calc.testStep("3", "3", "", "DEG")
        calc.testStep("*", "3.", "", "DEG")
        calc.testStep("4", "4", "", "DEG")
        calc.testStep("y^x", "4.", "", "DEG")
        calc.testStep("5", "5", "", "DEG")
        calc.testStep("=", "3074.", "", "DEG")
        calc.testStep("AC/ON 1 + 8 +/- + 1 2 =", "5.", "", "DEG")
        calc.testStep("2 / 3 * 3 =", "2.", "", "DEG")
        calc.testStep("1 / 3 * 3 =", "1.", "", "DEG")

    }

    @Test
    fun testConstant() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 2 * PI", "3.141592654", "", "DEG")
        calc.testStep("=", "6.283185307", "", "DEG")
        calc.testStep("AC/ON PI * 2", "2", "", "DEG")
        calc.testStep("=", "6.283185307", "", "DEG")
        calc.testStep("AC/ON 2 * CONST SIN", "299792458.", "", "DEG")
        calc.testStep("=", "599584916.", "", "DEG")
        calc.testStep("AC/ON CONST SIN * 2 =", "599584916.", "", "DEG")

    }
}
