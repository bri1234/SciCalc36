package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestLogarithm {
    @Test
    fun testLogarithm() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("1 5 . 2 3 log", "1.182699903", "", "DEG")
        calc.testStep("+ 1 2 . 4 5 log =", "2.277869255", "", "DEG")
        calc.testStep("AC/ON 2 10^x", "100.", "", "DEG")
        calc.testStep("- 1 1 x^2", "121.", "", "DEG")
        calc.testStep("=", "-21.", "", "DEG")
        calc.testStep("AC/ON", "0.", "", "DEG")
        calc.testStep("1 5 . 2 3 ln", "2.723267167", "", "DEG")
        calc.testStep("+ 1 2 . 4 5 ln =", "5.24498779", "", "DEG")
        calc.testStep("AC/ON . 6 9 3", "0.693", "", "DEG")
        calc.testStep("e^x", "1.999705661", "", "DEG")
        calc.testStep("+ 1 =", "2.999705661", "", "DEG")

    }
}
