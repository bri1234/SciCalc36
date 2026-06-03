package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestFractions {

    @Test
    fun testFractions() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")
    }
}

