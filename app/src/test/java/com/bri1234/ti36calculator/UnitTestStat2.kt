package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestStat2 {

    @Test
    fun testStat2() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("STAT2", "0.", "", "DEG STAT")

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("STAT2", "0.", "", "DEG STAT")

        calc.testStep("3 x<>y 9 S+", "1.", "", "DEG STAT")
        calc.testStep("Sx", "3.", "", "DEG STAT")
        calc.testStep("Sy", "9.", "", "DEG STAT")

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("STAT2", "0.", "", "DEG STAT")
        calc.testStep("9 x<>y 4 S+", "1.", "", "DEG STAT")
        calc.testStep("+ 5 =", "6.", "", "DEG STAT")

        calc.testStep("7 x<>y", "5.", "", "DEG STAT")
        calc.testStep("3 S+", "2.", "", "DEG STAT")

        calc.testStep("5 x<>y", "7.", "", "DEG STAT")
        calc.testStep("2 S+", "3.", "", "DEG STAT")

        calc.testStep("+ 5 =", "8.", "", "DEG STAT")

        calc.testStep("3 x<>y", "5.", "", "DEG STAT")
        calc.testStep("1 S+", "4.", "", "DEG STAT")

        calc.testStep("Sx", "24.", "", "DEG STAT")
        calc.testStep("Sx2", "164.", "", "DEG STAT")
        calc.testStep("Sy", "10.", "", "DEG STAT")
        calc.testStep("Sy2", "30.", "", "DEG STAT")
        calc.testStep("Sxy", "70.", "", "DEG STAT")
        calc.testStep("n", "4.", "", "DEG STAT")
        calc.testStep("x_", "6.", "", "DEG STAT")
        calc.testStep("y_", "2.5", "", "DEG STAT")

        calc.testStep("Sxn", "2.236067977", "", "DEG STAT")
        calc.testStep("Sxn-1", "2.581988897", "", "DEG STAT")
        calc.testStep("Syn", "1.118033989", "", "DEG STAT")
        calc.testStep("Syn-1", "1.290994449", "", "DEG STAT")

        calc.testStep("COR", "1.", "", "DEG STAT")
        calc.testStep("ITC", "-0.5", "", "DEG STAT")
        calc.testStep("SLP", "0.5", "", "DEG STAT")

        calc.testStep("1 1 x'", "23.", "", "DEG STAT")
        calc.testStep("6 y'", "2.5", "", "DEG STAT")
        calc.testStep("1 7 x<>y 8 S-", "3.", "", "DEG STAT")
        calc.testStep("1 9 x<>y 9 S-", "2.", "", "DEG STAT")

        calc.testStep("Sx", "-12.", "", "DEG STAT")
        calc.testStep("Sx2", "-486.", "", "DEG STAT")
        calc.testStep("Sy", "-7.", "", "DEG STAT")
        calc.testStep("Sy2", "-115.", "", "DEG STAT")
        calc.testStep("Sxy", "-237.", "", "DEG STAT")

        calc.testStep("CSR", "-237.", "", "DEG STAT")
        calc.testStep("Sx", "0.", "", "DEG STAT")
        calc.testStep("Sx2", "0.", "", "DEG STAT")
        calc.testStep("Sy", "0.", "", "DEG STAT")
        calc.testStep("Sy2", "0.", "", "DEG STAT")
        calc.testStep("Sxy", "0.", "", "DEG STAT")

        calc.testStep("AC/ON 5", "5", "", "DEG")
        calc.testStep("STAT2", "5.", "", "DEG STAT")

        calc.testStep("AC/ON 5", "5", "", "DEG")
        calc.testStep("STAT1", "5.", "", "DEG STAT")
    }

    @Test
    fun testStat2Frq() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("STAT2", "0.", "", "DEG STAT")
        calc.testStep("6 . 2 8 x<>y 3 . 1 4 FRQ", "Fr 00", "", "DEG STAT")
        calc.testStep("4", "Fr 04", "", "DEG STAT")
        calc.testStep("2", "Fr 42", "", "DEG STAT")
        calc.testStep("S+", "42.", "", "DEG STAT")
        calc.testStep("Sx", "263.76", "", "DEG STAT")
        calc.testStep("Sx2", "1656.4128", "", "DEG STAT")
        calc.testStep("Sy", "131.88", "", "DEG STAT")
        calc.testStep("Sy2", "414.1032", "", "DEG STAT")
        calc.testStep("Sxy", "828.2064", "", "DEG STAT")
        calc.testStep("5 . 4 2 x<>y", "6.28", "", "DEG STAT")
        calc.testStep("2 . 7 1 FRQ 1", "Fr 01", "", "DEG STAT")
        calc.testStep("2", "Fr 12", "", "DEG STAT")
        calc.testStep("S-", "30.", "", "DEG STAT")
        calc.testStep("Sx", "198.72", "", "DEG STAT")
        calc.testStep("Sx2", "1303.896", "", "DEG STAT")
        calc.testStep("Sy", "99.36", "", "DEG STAT")
        calc.testStep("Sy2", "325.974", "", "DEG STAT")
        calc.testStep("Sxy", "651.948", "", "DEG STAT")
        calc.testStep("CE/C Sx", "198.72", "", "DEG STAT")
        calc.testStep("AC/ON Sx", "Error  ", "", "DEG")
        calc.testStep("AC/ON STAT2 6 . 2 8 x<>y 3 . 1 4 FRQ 8 6 SIN", "0.05477591", "", "DEG STAT")
        calc.testStep("AC/ON STAT2 FRQ +/-", "0.", "", "DEG STAT")
        calc.testStep("AC/ON FRQ", "Error  ", "", "DEG")

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("STAT2", "0.", "", "DEG STAT")
        calc.testStep("2 . 4 EE 3 x<>y", "0.", "", "DEG STAT")
        calc.testStep("1", "1", "", "DEG STAT")
        calc.testStep(".", "1.", "", "DEG STAT")
        calc.testStep("2", "1.2", "", "DEG STAT")
        calc.testStep("EE", "1.2", "00", "DEG STAT")
        calc.testStep("3", "1.2", "03", "DEG STAT")
        calc.testStep("FRQ", "Fr 00", "", "DEG STAT")
        calc.testStep("5", "Fr 05", "", "DEG STAT")
        calc.testStep("S+", "5.", "", "DEG STAT")
        calc.testStep("Sx", "12000.", "", "DEG STAT")
        calc.testStep("Sy", "6000.", "", "DEG STAT")
    }

    @Test
    fun testStat2Invalid() {
        val calc = CalculatorCore()

        calc.testStep("STAT2 1 . 2 x<>y 1 . 1 FRQ 1 1 S+", "11.", "", "DEG STAT")
        calc.testStep("2 . 4 x<>y 2", "2", "", "DEG STAT")
        calc.testStep(". 2 FRQ 3 S-", "8.", "", "DEG STAT")
        calc.testStep("3 . 6 x<>y 3 . 3 FRQ 1 3 S-", "Error  ", "", "DEG STAT")
    }

    @Test
    fun testStat2DefaultFrequency() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON STAT2 9 x<>y 4 S+", "1.", "", "DEG STAT")
        calc.testStep("Sx", "9.", "", "DEG STAT")
        calc.testStep("Sy", "4.", "", "DEG STAT")
    }

    @Test
    fun testStat2ZeroFrequency() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON STAT2 9 x<>y 4 S+", "1.", "", "DEG STAT")
        calc.testStep("1 1 x<>y 5 FRQ 0 S+", "Error  ", "", "DEG STAT")

        calc.testStep("AC/ON STAT2 9 x<>y 4 S+", "1.", "", "DEG STAT")
        calc.testStep("9 x<>y 4 FRQ 0 S-", "Error  ", "", "DEG STAT")
    }

    @Test
    fun testStat2FromManual() {
        val calc = CalculatorCore()

        calc.testStep("STAT2", "0.", "", "DEG STAT")
        calc.testStep("CSR", "0.", "", "DEG STAT")
        calc.testStep("4 x<>y 5 FRQ 2 S+", "2.", "", "DEG STAT")
        calc.testStep("9 x<>y 9 S+", "3.", "", "DEG STAT")
        calc.testStep("2 x<>y 3 S+", "4.", "", "DEG STAT")
        calc.testStep("9 y'", "9.074766355", "", "DEG STAT")
        calc.testStep("COR", "0.998030525", "", "DEG STAT")
        calc.testStep("SLP", "0.841121495", "", "DEG STAT")
        calc.testStep("ITC", "1.504672897", "", "DEG STAT")
        calc.testStep("x_", "4.75", "", "DEG STAT")
        calc.testStep("y_", "5.5", "", "DEG STAT")

    }
}

