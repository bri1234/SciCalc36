package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestArithmetic {

    @Test
    fun testArithmetic() {
        val sim = CalculatorSimulator()

        sim.buttonPressed(CalculatorButton.AC_ON)
        sim.input("6 0 + 5 * 1 2 =")
        sim.assertDisplay("120.", "")
    }

}

