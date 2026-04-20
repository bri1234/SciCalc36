package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestArithmetic {
    @Test
    fun testBasicArithmetic() {
        val sim = CalculatorSimulator()

        sim.buttonPressed(CalculatorButton.AC_ON)
        sim.input("6 0 + 5 * 1 2 =")
        sim.assertDisplay("120.", "")
    }

    @Test
    fun testPercent() {
        val sim = CalculatorSimulator()

        sim.buttonPressed(CalculatorButton.AC_ON)
        sim.input("2 5 0 * 5 %")
        sim.assertDisplay("0.05", "")
        sim.input("=")
        sim.assertDisplay("12.5", "")
    }
}

