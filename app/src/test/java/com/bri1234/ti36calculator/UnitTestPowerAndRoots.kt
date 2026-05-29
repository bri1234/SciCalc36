package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestPowerAndRoots {
    @Test
    fun testPowersAndRoots() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 8 1/x", "0.125", "", "DEG")
        calc.testStep("+ 4 1/x", "0.25", "", "DEG")
        calc.testStep("=", "0.375", "", "DEG")
        calc.testStep("6 x^2", "36.", "", "DEG")
        calc.testStep("+ 2", "2", "", "DEG")
        calc.testStep("=", "38.", "", "DEG")
        calc.testStep("AC/ON 2 5 6 sqrt", "16.", "", "DEG")
        calc.testStep("+ 4 sqrt", "2.", "", "DEG")
        calc.testStep("=", "18.", "", "DEG")
        calc.testStep("AC/ON 8 cbrt", "2.", "", "DEG")
        calc.testStep("+ 2 7 cbrt", "3.", "", "DEG")
        calc.testStep("=", "5.", "", "DEG")
        calc.testStep("5 y^x 3 =", "125.", "", "DEG")
        calc.testStep("AC/ON 8 yrootx 3 =", "2.", "", "DEG")

    }
}
