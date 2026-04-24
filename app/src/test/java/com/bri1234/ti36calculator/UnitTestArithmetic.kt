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

        sim.input("AC/ON 1 + 8 +/- + 1 2 =")
        sim.assertDisplay("5.", "")

        sim.input("2 / 3 * 3 =")
        sim.assertDisplay("2.", "")

        sim.input("1 / 3 * 3 =")
        sim.assertDisplay("1.", "")
    }

    @Test
    fun testConstant() {
        val sim = CalculatorSimulator()

        sim.input("AC/ON 2 * PI")
        sim.assertDisplay("3.141592654", "")
        sim.input("=")
        sim.assertDisplay("6.283185307", "")

        sim.input("AC/ON PI * 2")
        sim.assertDisplay("2", "")
        sim.input("=")
        sim.assertDisplay("6.283185307", "")

        sim.input("AC/ON 2 * CONST SIN")
        sim.assertDisplay("299792458.", "")
        sim.input("=")
        sim.assertDisplay("599584916.", "")

        sim.input("AC/ON CONST SIN * 2 =")
        sim.assertDisplay("599584916.", "")
    }
}

