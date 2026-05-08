package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestPercent {
    @Test
    fun testPercent() {
        val calc = CalculatorCore()

        calc.input("AC/ON 2 5 0 * 5 %")
        calc.assertDisplay("0.05", "")
        calc.input("=")
        calc.assertDisplay("12.5", "")

        calc.input("AC/ON 2 5 0 / 5 %")
        calc.assertDisplay("0.05", "")
        calc.input("=")
        calc.assertDisplay("5000.", "")

        calc.input("2 5 0 + 5 %")
        calc.assertDisplay("12.5", "")
        calc.input("=")
        calc.assertDisplay("262.5", "")

        calc.input("2 5 0 - 5 %")
        calc.assertDisplay("12.5", "")
        calc.input("=")
        calc.assertDisplay("237.5", "")
    }
}
