package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestErrorConditions {

    @Test
    fun testErrorConditions() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")

        // TODO: add tests
    }
}

