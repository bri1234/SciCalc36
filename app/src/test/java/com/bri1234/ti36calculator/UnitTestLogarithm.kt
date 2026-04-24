package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestLogarithm {
    @Test
    fun testLogarithm() {
        val sim = CalculatorSimulator()

        sim.input("AC/ON")
        sim.assertDisplay("0.", "")
        sim.input("1 5 . 2 3 log")
        sim.assertDisplay("1.182699903", "")
        sim.input("+ 1 2 . 4 5 log =")
        sim.assertDisplay("2.277869255", "")

        sim.input("AC/ON 2 10^x")
        sim.assertDisplay("100.", "")
        sim.input("- 1 1 x^2")
        sim.assertDisplay("121.", "")
        sim.input("=")
        sim.assertDisplay("-21.", "")

        sim.input("AC/ON")
        sim.assertDisplay("0.", "")
        sim.input("1 5 . 2 3 ln")
        sim.assertDisplay("2.723267167", "")
        sim.input("+ 1 2 . 4 5 ln =")
        sim.assertDisplay("5.24498779", "")

        sim.input("AC/ON . 6 9 3")
        sim.assertDisplay("0.693", "")
        sim.input("e^x")
        sim.assertDisplay("1.999705661", "")
        sim.input("+ 1 =")
        sim.assertDisplay("2.999705661", "")
    }
}
