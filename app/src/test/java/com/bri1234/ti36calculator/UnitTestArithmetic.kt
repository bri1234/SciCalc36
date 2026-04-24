package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestArithmetic {

    @Test
    fun testArithmetic() {
        val sim = CalculatorSimulator()

        sim.input("AC/ON 6 0 + 5 * 1 2 =")
        sim.assertDisplay("120.", "")

        sim.input("2")
        sim.assertDisplay("2", "")
        sim.input("+")
        sim.assertDisplay("2.", "")
        sim.input("3")
        sim.assertDisplay("3", "")
        sim.input("*")
        sim.assertDisplay("3.", "")
        sim.input("4")
        sim.assertDisplay("4", "")
        sim.input("y^x")
        sim.assertDisplay("4.", "")
        sim.input("5")
        sim.assertDisplay("5", "")
        sim.input("=")
        sim.assertDisplay("3074.", "")
    }

}

