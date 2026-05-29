package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestAngleUnit {
    @Test
    fun testAngleUnitCycle() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("4 5 SIN", "0.707106781", "", "DEG")
        calc.testStep("4 5 DRG", "45", "", "RAD")
        calc.testStep("SIN", "0.850903525", "", "RAD")
        calc.testStep("CE/C", "0.", "", "RAD")
        calc.testStep("4 5 DRG", "45", "", "GRAD")
        calc.testStep("SIN", "0.649448048", "", "GRAD")
        calc.testStep("DRG", "0.649448048", "", "DEG")
        calc.testStep("DRG", "0.649448048", "", "RAD")
        calc.testStep("AC/ON", "0.", "", "DEG")

    }

    @Test
    fun testAngleUnitCycleConvert() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("4 5 SIN", "0.707106781", "", "DEG")
        calc.testStep("4 5 DRG>", "0.785398163", "", "RAD")
        calc.testStep("SIN", "0.707106781", "", "RAD")
        calc.testStep("CE/C", "0.", "", "RAD")
        calc.testStep("4 5 DRG>", "2864.788976", "", "GRAD")
        calc.testStep("SIN", "0.850903525", "", "GRAD")
        calc.testStep("DRG>", "0.765813172", "", "DEG")
        calc.testStep("DRG>", "0.013365961", "", "RAD")
        calc.testStep("AC/ON", "0.", "", "DEG")

    }
}
