package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestStat1 {

    @Test
    fun testStat1() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("STAT1", "0.", "", "DEG STAT")
        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("STAT1", "0.", "", "DEG STAT")
        calc.testStep("4 S+", "1.", "", "DEG STAT")
        calc.testStep("+ 5 =", "6.", "", "DEG STAT")
        calc.testStep("3 S+", "2.", "", "DEG STAT")
        calc.testStep("2 S+", "3.", "", "DEG STAT")
        calc.testStep("+ 5 =", "8.", "", "DEG STAT")
        calc.testStep("1 S+", "4.", "", "DEG STAT")
        calc.testStep("Sx", "10.", "", "DEG STAT")
        calc.testStep("Sx2", "30.", "", "DEG STAT")
        calc.testStep("n", "4.", "", "DEG STAT")
        calc.testStep("x_", "2.5", "", "DEG STAT")
        calc.testStep("Sxn", "1.118033989", "", "DEG STAT")
        calc.testStep("Sxn-1", "1.290994449", "", "DEG STAT")
        calc.testStep("8 S-", "3.", "", "DEG STAT")
        calc.testStep("9 S-", "2.", "", "DEG STAT")
        calc.testStep("Sx", "-7.", "", "DEG STAT")
        calc.testStep("Sx2", "-115.", "", "DEG STAT")
        calc.testStep("CSR Sx", "0.", "", "DEG STAT")
        calc.testStep("Sx2", "0.", "", "DEG STAT")

        calc.testStep("AC/ON 5", "5", "", "DEG")
        calc.testStep("STAT1", "5.", "", "DEG STAT")

        calc.testStep("AC/ON 5", "5", "", "DEG")
        calc.testStep("STAT2", "5.", "", "DEG STAT")
    }

    @Test
    fun testStat1Frq() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("STAT1", "0.", "", "DEG STAT")
        calc.testStep("3 . 1 4 FRQ", "Fr 00", "", "DEG STAT")
        calc.testStep("4", "Fr 04", "", "DEG STAT")
        calc.testStep("2", "Fr 42", "", "DEG STAT")
        calc.testStep("S+", "42.", "", "DEG STAT")
        calc.testStep("Sx", "131.88", "", "DEG STAT")
        calc.testStep("Sx2", "414.1032", "", "DEG STAT")
        calc.testStep("2 . 7 1 FRQ 1", "Fr 01", "", "DEG STAT")
        calc.testStep("2", "Fr 12", "", "DEG STAT")
        calc.testStep("S-", "30.", "", "DEG STAT")
        calc.testStep("Sx", "99.36", "", "DEG STAT")
        calc.testStep("Sx2", "325.974", "", "DEG STAT")
        calc.testStep("CE/C Sx", "99.36", "", "DEG STAT")
        calc.testStep("AC/ON Sx", "Error  ", "", "DEG")
        calc.testStep("AC/ON STAT1 3 . 1 4 FRQ 8 6 SIN", "0.05477591", "", "DEG STAT")
        calc.testStep("AC/ON STAT1 FRQ +/-", "0.", "", "DEG STAT")
        calc.testStep("AC/ON FRQ", "Error  ", "", "DEG")
        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("STAT1", "0.", "", "DEG STAT")
        calc.testStep("1", "1", "", "DEG STAT")
        calc.testStep(".", "1.", "", "DEG STAT")
        calc.testStep("2", "1.2", "", "DEG STAT")
        calc.testStep("EE", "1.2", "00", "DEG STAT")
        calc.testStep("3", "1.2", "03", "DEG STAT")
        calc.testStep("FRQ", "Fr 00", "", "DEG STAT")
        calc.testStep("5", "Fr 05", "", "DEG STAT")
        calc.testStep("S+", "5.", "", "DEG STAT")
        calc.testStep("Sx", "6000.", "", "DEG STAT")

    }
}
