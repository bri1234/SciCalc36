package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestLogarithm {
    @Test
    fun testLogarithm() {
        val calc = CalculatorCore()

        calc.input("AC/ON")
        calc.assertDisplay("0.", "")
        calc.input("1 5 . 2 3 log")
        calc.assertDisplay("1.182699903", "")
        calc.input("+ 1 2 . 4 5 log =")
        calc.assertDisplay("2.277869255", "")

        calc.input("AC/ON 2 10^x")
        calc.assertDisplay("100.", "")
        calc.input("- 1 1 x^2")
        calc.assertDisplay("121.", "")
        calc.input("=")
        calc.assertDisplay("-21.", "")

        calc.input("AC/ON")
        calc.assertDisplay("0.", "")
        calc.input("1 5 . 2 3 ln")
        calc.assertDisplay("2.723267167", "")
        calc.input("+ 1 2 . 4 5 ln =")
        calc.assertDisplay("5.24498779", "")

        calc.input("AC/ON . 6 9 3")
        calc.assertDisplay("0.693", "")
        calc.input("e^x")
        calc.assertDisplay("1.999705661", "")
        calc.input("+ 1 =")
        calc.assertDisplay("2.999705661", "")
    }
}
