package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestConversions {

    @Test
    fun testMetricToEnglish() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")

        // TODO: add tests
    }

    @Test
    fun testEnglishToMetric() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")

        // TODO: add tests
    }
}

