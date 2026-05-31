package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestMemory {

    @Test
    fun testMemory() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON 4 2 STO", "42.", "", "DEG")
        calc.testStep("1", "42.", "", "DEG M")
        calc.testStep("CE/C", "0.", "", "DEG M")
        calc.testStep("AC/ON", "0.", "", "DEG")

        calc.testStep("AC/ON 4 2 STO 1", "42.", "", "DEG M")
        calc.testStep("1 2 STO 1", "12.", "", "DEG M")
        calc.testStep("0 STO 1", "0.", "", "DEG")

        // STO: store integer, decimal, and negative values in separate cells.
        calc.testStep("AC/ON 4 2 STO 1", "42.", "", "DEG M")
        calc.testStep("1 . 2 5 STO 2", "1.25", "", "DEG M")
        calc.testStep("7 +/- STO 3", "-7.", "", "DEG M")

        // RCL: recall each previously stored value.
        calc.testStep("RCL 1", "42.", "", "DEG M")
        calc.testStep("RCL 2", "1.25", "", "DEG M")
        calc.testStep("RCL 3", "-7.", "", "DEG M")

        // SUM: add positive and negative values to existing memory cells.
        calc.testStep("2 . 5 SUM 2", "2.5", "", "DEG M")
        calc.testStep("RCL 2", "3.75", "", "DEG M")
        calc.testStep("2 +/- SUM 1", "-2.", "", "DEG M")
        calc.testStep("RCL 1", "40.", "", "DEG M")

        // EXC: exchange the displayed value with a memory cell in both directions.
        calc.testStep("9 . 5 EXC 2", "3.75", "", "DEG M")
        calc.testStep("RCL 2", "9.5", "", "DEG M")
        calc.testStep("8 +/- EXC 3", "-7.", "", "DEG M")
        calc.testStep("RCL 3", "-8.", "", "DEG M")

    }

    @Test
    fun testMemoryUnusual() {
        val calc = CalculatorCore()

        calc.testStep("1 . 1 STO 1 2 . 2 RCL STO 2", "2.2", "", "DEG M")
        calc.testStep("RCL 1", "1.1", "", "DEG M")
        calc.testStep("RCL 2", "2.2", "", "DEG M")
    }
}
