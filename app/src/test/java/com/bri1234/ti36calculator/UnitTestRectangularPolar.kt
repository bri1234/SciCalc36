package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestRectangularPolar {

    @Test
    fun testRectangularToPolar() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("1 0 x<>y 8", "8", "", "DEG")
        calc.testStep("R>P", "12.80624847", "", "DEG r")
        calc.testStep("x<>y", "38.65980825", "", "DEG")
        calc.testStep("x<>y", "12.80624847", "", "DEG r")

        calc.testStep("AC/ON DRG", "0.", "", "RAD")
        calc.testStep("1 0 x<>y 8", "8", "", "RAD")
        calc.testStep("R>P", "12.80624847", "", "RAD r")
        calc.testStep("x<>y", "0.674740942", "", "RAD")
        calc.testStep("x<>y", "12.80624847", "", "RAD r")

        calc.testStep("AC/ON DRG DRG", "0.", "", "GRAD")
        calc.testStep("1 0 x<>y 8", "8", "", "GRAD")
        calc.testStep("R>P", "12.80624847", "", "GRAD r")
        calc.testStep("x<>y", "42.9553425", "", "GRAD")
        calc.testStep("x<>y", "12.80624847", "", "GRAD r")
    }

    @Test
    fun testPolarToRectangular() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("5 x<>y 3 0", "30", "", "DEG")
        calc.testStep("P>R", "4.330127019", "", "DEG x")
        calc.testStep("x<>y", "2.5", "", "DEG")
        calc.testStep("x<>y", "4.330127019", "", "DEG x")

        calc.testStep("AC/ON DRG", "0.", "", "RAD")
        calc.testStep("5 x<>y 3 0", "30", "", "RAD")
        calc.testStep("P>R", "0.771257249", "", "RAD x")
        calc.testStep("x<>y", "-4.94015812", "", "RAD")
        calc.testStep("x<>y", "0.771257249", "", "RAD x")

        calc.testStep("AC/ON DRG DRG", "0.", "", "GRAD")
        calc.testStep("5 x<>y 3 0", "30", "", "GRAD")
        calc.testStep("P>R", "4.455032621", "", "GRAD x")
        calc.testStep("x<>y", "2.269952499", "", "GRAD")
        calc.testStep("x<>y", "4.455032621", "", "GRAD x")
    }
}