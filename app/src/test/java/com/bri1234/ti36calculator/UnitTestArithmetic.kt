package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestArithmetic {

    @Test
    fun testArithmetic() {
        val calc = CalculatorCore()

        calc.input("AC/ON 6 0 + 5 * 1 2 =")
        calc.assertDisplay("120.", "")

        calc.input("2")
        calc.assertDisplay("2", "")
        calc.input("+")
        calc.assertDisplay("2.", "")
        calc.input("3")
        calc.assertDisplay("3", "")
        calc.input("*")
        calc.assertDisplay("3.", "")
        calc.input("4")
        calc.assertDisplay("4", "")
        calc.input("y^x")
        calc.assertDisplay("4.", "")
        calc.input("5")
        calc.assertDisplay("5", "")
        calc.input("=")
        calc.assertDisplay("3074.", "")

        calc.input("AC/ON 1 + 8 +/- + 1 2 =")
        calc.assertDisplay("5.", "")

        calc.input("2 / 3 * 3 =")
        calc.assertDisplay("2.", "")

        calc.input("1 / 3 * 3 =")
        calc.assertDisplay("1.", "")
    }

    @Test
    fun testConstant() {
        val calc = CalculatorCore()

        calc.input("AC/ON 2 * PI")
        calc.assertDisplay("3.141592654", "")
        calc.input("=")
        calc.assertDisplay("6.283185307", "")

        calc.input("AC/ON PI * 2")
        calc.assertDisplay("2", "")
        calc.input("=")
        calc.assertDisplay("6.283185307", "")

        calc.input("AC/ON 2 * CONST SIN")
        calc.assertDisplay("299792458.", "")
        calc.input("=")
        calc.assertDisplay("599584916.", "")

        calc.input("AC/ON CONST SIN * 2 =")
        calc.assertDisplay("599584916.", "")
    }
}

