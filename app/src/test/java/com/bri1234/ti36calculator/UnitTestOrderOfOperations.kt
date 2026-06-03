package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestOrderOfOperations {

    @Test
    fun testOrderOfOperations() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")

        // TODO: add tests
    }

}

