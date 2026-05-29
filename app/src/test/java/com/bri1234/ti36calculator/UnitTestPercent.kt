package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestPercent {
    @Test
    fun testPercent() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 2 5 0 * 5 %", "0.05", "", "DEG")
        calc.testStep("=", "12.5", "", "DEG")
        calc.testStep("AC/ON 2 5 0 / 5 %", "0.05", "", "DEG")
        calc.testStep("=", "5000.", "", "DEG")
        calc.testStep("2 5 0 + 5 %", "12.5", "", "DEG")
        calc.testStep("=", "262.5", "", "DEG")
        calc.testStep("2 5 0 - 5 %", "12.5", "", "DEG")
        calc.testStep("=", "237.5", "", "DEG")

    }
}
