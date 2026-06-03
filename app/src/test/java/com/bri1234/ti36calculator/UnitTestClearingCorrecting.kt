package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestClearingCorrecting {

    @Test
    fun testClearing() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")

        // TODO: add tests
    }

    @Test
    fun testCorrecting() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")

        // TODO: add tests
    }
}

