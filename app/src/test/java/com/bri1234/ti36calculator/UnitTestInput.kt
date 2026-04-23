package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestInput {
    @Test
    fun testInput() {
        val sim = CalculatorSimulator()

        sim.buttonPressed(CalculatorButton.AC_ON)
        sim.assertDisplay("0.", "")
        sim.input("4")
        sim.assertDisplay("4", "")
        sim.input("2")
        sim.assertDisplay("42", "")
        sim.input("=")
        sim.assertDisplay("42.", "")

    }
}
