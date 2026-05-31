package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestStat2 {

    @Test
    fun testStat2Frequency() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON STAT2 3 x<>y 2 FRQ 4 S+", "4.", "", "DEG STAT")
        calc.testStep("Sx", "8.", "", "DEG STAT")
        calc.testStep("Sy", "12.", "", "DEG STAT")
    }
}
