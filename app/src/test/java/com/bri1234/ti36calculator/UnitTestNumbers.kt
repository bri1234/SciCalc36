package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestNumbers {

    @Test
    fun testRounding() {
        val calc = CalculatorCore()

        // Reference values calculated with bc -l.
        calc.testStep("AC/ON 1 / 9 =", "0.111111111", "", "DEG")
        calc.testStep("AC/ON 2 / 3 =", "0.666666667", "", "DEG")
        calc.testStep("AC/ON 1 +/- / 9 =", "-0.111111111", "", "DEG")
        calc.testStep("AC/ON 2 +/- / 3 =", "-0.666666667", "", "DEG")
        calc.testStep("AC/ON 1 / 1 1 =", "0.090909091", "", "DEG")
        calc.testStep("AC/ON 1 0 / 1 1 =", "0.909090909", "", "DEG")
        calc.testStep("AC/ON 1 / 7 =", "0.142857143", "", "DEG")
        calc.testStep("AC/ON 6 / 7 =", "0.857142857", "", "DEG")
        calc.testStep("AC/ON 1 / 9 = SCI", "1.111111111", "-01", "DEG")
        calc.testStep("AC/ON 2 / 3 = SCI", "6.666666667", "-01", "DEG")
        calc.testStep("AC/ON 1 EE 1 1 / 3 =", "3.333333333", "10", "DEG")
        calc.testStep("AC/ON 1 EE 8 +/- / 3 =", "3.333333333", "-09", "DEG")

        calc.testStep("AC/ON 1 sin", "0.017452406", "", "DEG")
        calc.testStep("AC/ON 2 sin", "0.034899497", "", "DEG")
        calc.testStep("AC/ON 7 sin", "0.121869343", "", "DEG")
        calc.testStep("AC/ON 8 sin", "0.139173101", "", "DEG")
        calc.testStep("AC/ON 3 2 sin SCI", "5.299192642", "-01", "DEG")
        calc.testStep("AC/ON 1 sin SCI", "1.745240644", "-02", "DEG")

        calc.testStep("AC/ON 3 2 cos", "0.848048096", "", "DEG")
        calc.testStep("AC/ON 3 1 cos", "0.857167301", "", "DEG")
        calc.testStep("AC/ON 5 9 cos", "0.515038075", "", "DEG")
        calc.testStep("AC/ON 3 1 cos SCI", "8.571673007", "-01", "DEG")
        calc.testStep("AC/ON 3 2 cos SCI", "8.480480962", "-01", "DEG")
        calc.testStep("AC/ON 5 9 cos SCI", "5.150380749", "-01", "DEG")

        calc.testStep("AC/ON 1 2 . 4 5 log", "1.095169351", "", "DEG")
        calc.testStep("AC/ON 3 log", "0.477121255", "", "DEG")
        calc.testStep("AC/ON 7 log", "0.84509804", "", "DEG")
        calc.testStep("AC/ON 8 log", "0.903089987", "", "DEG")
        calc.testStep("AC/ON 3 log SCI", "4.771212547", "-01", "DEG")
        calc.testStep("AC/ON 2 log SCI", "3.010299957", "-01", "DEG")
        calc.testStep("AC/ON 7 log SCI", "8.4509804", "-01", "DEG")
        calc.testStep("AC/ON 8 log SCI", "9.03089987", "-01", "DEG")

        calc.testStep("AC/ON 9 9 9 9 9 9 9 9 9 4 / 1 EE 1 0 =", "0.999999999", "", "DEG")
        calc.testStep("AC/ON 9 9 9 9 9 9 9 9 9 6 / 1 EE 1 0 =", "1.", "", "DEG")
        calc.testStep("AC/ON 9 9 9 9 9 9 9 9 9 6 +/- / 1 EE 1 0 =", "-1.", "", "DEG")
        calc.testStep("AC/ON 2 / 3 = FIX 2", "0.67", "", "DEG")
        calc.testStep("AC/ON 1 / 9 = FIX 2", "0.11", "", "DEG")
    }
}
