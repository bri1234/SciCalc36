package com.bri1234.ti36calculator

import org.junit.Test

class TestMode {
    @Test
    fun testMode() {
        val calc = CalculatorCore()

        calc.testStep("STAT1", "0.", "", "STAT", "DEG")
        calc.testStep("HEX", "0", "", "HEX")
        calc.testStep("DEC", "0.", "", "DEG")
        calc.testStep("HEX", "0", "", "HEX")
        calc.testStep("STAT1", "0.", "", "STAT", "DEG")
        calc.testStep("STAT1", "0.", "", "STAT", "DEG")
        calc.testStep("BIN", "0", "", "BIN")
        calc.testStep("STAT2", "0.", "", "STAT", "DEG")
        calc.testStep("OCT", "0", "", "OCT")

        calc.testStep("AC/ON DRG 5 6", "56", "", "RAD")
        calc.testStep("HEX", "38", "", "HEX")
        calc.testStep("DEC", "56.", "", "RAD")
        calc.testStep("BIN", "111000", "", "BIN")
        calc.testStep("DEC", "56.", "", "RAD")

        calc.testStep("AC/ON 0 . 0 0 1 2 3 ENG", "1.23", "-03", "DEG")
        calc.testStep("DEC", "1.23", "-03", "DEG")
        calc.testStep("HEX", "0", "", "HEX")
        calc.testStep("DEC", "0.", "00", "DEG")
    }
}
