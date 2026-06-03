package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestConstants {

    @Test
    fun testConstants() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON PI", "3.141592654", "", "DEG")

        calc.testStep("AC/ON CONST sin", "299792458.", "", "DEG")
        calc.testStep("AC/ON CONST cos", "9.80665", "", "DEG")
        calc.testStep("AC/ON CONST tan", "9.109383714", "-31", "DEG")
        calc.testStep("AC/ON CONST y^x", "1.602176634", "-19", "DEG")
        calc.testStep("AC/ON CONST 1/x", "6.62607015", "-34", "DEG")
        calc.testStep("AC/ON CONST x^2", "6.02214076", "23", "DEG")
        calc.testStep("AC/ON CONST sqrt", "8.314462618", "", "DEG")
        calc.testStep("AC/ON CONST /", "6.6743015", "-11", "DEG")
    }

}
