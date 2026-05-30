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

        calc.testStep("AC/ON 1 2 3 0 0 0 ENG", "123.", "03", "DEG")
        calc.testStep("DEC", "123.", "03", "DEG")
        calc.testStep("HEX", "1E078", "", "HEX")
        calc.testStep("DEC", "123.", "03", "DEG")

        calc.testStep("AC/ON 0 . 7 HEX", "0", "", "HEX")
        calc.testStep("AC/ON 1 . 7 HEX", "1", "", "HEX")
        calc.testStep("AC/ON 1 . 7 BIN", "1", "", "BIN")
        calc.testStep("AC/ON 1 . 7 OCT", "1", "", "OCT")

        calc.testStep("AC/ON 1 . 7 +/- HEX", "FFFFFFFFFF", "", "HEX")
        calc.testStep("DEC", "-1", "", "DEG")

        calc.testStep("AC/ON 1 . 7 HEX FLO", "1", "", "HEX")
        calc.testStep("AC/ON 1 . 7 HEX SCI", "1", "", "HEX")
        calc.testStep("AC/ON 1 . 7 HEX ENG", "1", "", "HEX")

    }
}
