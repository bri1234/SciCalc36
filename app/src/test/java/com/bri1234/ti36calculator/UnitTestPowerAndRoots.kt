package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestPowerAndRoots {
    @Test
    fun testPowersAndRoots() {
        val sim = CalculatorSimulator()

        sim.buttonPressed(CalculatorButton.AC_ON)
        sim.input("8 1/x")
        sim.assertDisplay("0.125", "")
        sim.input("+ 4 1/x")
        sim.assertDisplay("0.25", "")
        sim.input("=")
        sim.assertDisplay("0.375", "")

        sim.buttonPressed(CalculatorButton.AC_ON)
        sim.input("6 x^2")
        sim.assertDisplay("36.", "")
        sim.input("+ 2")
        sim.assertDisplay("2", "")
        sim.input("=")
        sim.assertDisplay("38.", "")

        sim.buttonPressed(CalculatorButton.AC_ON)
        sim.input("2 5 6 sqrt")
        sim.assertDisplay("16.", "")
        sim.input("+ 4 sqrt")
        sim.assertDisplay("2.", "")
        sim.input("=")
        sim.assertDisplay("18.", "")

        sim.buttonPressed(CalculatorButton.AC_ON)
        sim.input("8 cbrt")
        sim.assertDisplay("2.", "")
        sim.input("+ 2 7 cbrt")
        sim.assertDisplay("3.", "")
        sim.input("=")
        sim.assertDisplay("5.", "")

        sim.buttonPressed(CalculatorButton.AC_ON)
        sim.input("5 y^x 3 =")
        sim.assertDisplay("125.", "")

        sim.buttonPressed(CalculatorButton.AC_ON)
        sim.input("8 yrootx 3 =")
        sim.assertDisplay("2.", "")

    }
}

