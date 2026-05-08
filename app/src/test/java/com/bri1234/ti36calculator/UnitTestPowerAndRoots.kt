package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestPowerAndRoots {
    @Test
    fun testPowersAndRoots() {
        val calc = CalculatorCore()

        calc.input("AC/ON 8 1/x")
        calc.assertDisplay("0.125", "")
        calc.input("+ 4 1/x")
        calc.assertDisplay("0.25", "")
        calc.input("=")
        calc.assertDisplay("0.375", "")

        calc.input("6 x^2")
        calc.assertDisplay("36.", "")
        calc.input("+ 2")
        calc.assertDisplay("2", "")
        calc.input("=")
        calc.assertDisplay("38.", "")

        calc.input("AC/ON 2 5 6 sqrt")
        calc.assertDisplay("16.", "")
        calc.input("+ 4 sqrt")
        calc.assertDisplay("2.", "")
        calc.input("=")
        calc.assertDisplay("18.", "")

        calc.input("AC/ON 8 cbrt")
        calc.assertDisplay("2.", "")
        calc.input("+ 2 7 cbrt")
        calc.assertDisplay("3.", "")
        calc.input("=")
        calc.assertDisplay("5.", "")

        calc.input("5 y^x 3 =")
        calc.assertDisplay("125.", "")

        calc.input("AC/ON 8 yrootx 3 =")
        calc.assertDisplay("2.", "")

    }
}

