package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestProbability {

    @Test
    fun testProbability() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 5 2 x<>y 5 nCr", "2598960.", "", "DEG")
        calc.testStep("AC/ON 8 x<>y 3 nPr", "336.", "", "DEG")

        calc.testStep("AC/ON 4 x!", "24.", "", "DEG")
        calc.testStep("AC/ON 5 2 x!", "8.065817517", "67", "DEG")
    }
}