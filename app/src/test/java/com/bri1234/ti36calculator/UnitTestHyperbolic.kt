package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestHyperbolic {
    @Test
    fun testHyperbolic() {
        val calc = CalculatorCore()

        calc.testStep("5 hyp", "5", "", "HYP DEG")
        calc.testStep("sin", "74.20321058", "", "DEG")
        calc.testStep("+ 2 =", "76.20321058", "", "DEG")
        calc.testStep("5 hyp", "5", "", "HYP DEG")
        calc.testStep("asin", "2.312438341", "", "DEG")
        calc.testStep("+ 2 =", "4.312438341", "", "DEG")
        calc.testStep("4 hyp", "4", "", "HYP DEG")
        calc.testStep("cos", "27.30823284", "", "DEG")
        calc.testStep("+ 2 =", "29.30823284", "", "DEG")
        calc.testStep("4 hyp", "4", "", "HYP DEG")
        calc.testStep("acos", "2.063437069", "", "DEG")
        calc.testStep("+ 2 =", "4.063437069", "", "DEG")
        calc.testStep("4 hyp", "4", "", "HYP DEG")
        calc.testStep("tan", "0.9993293", "", "DEG")
        calc.testStep("+ 2 =", "2.9993293", "", "DEG")
        calc.testStep(". 6 hyp", "0.6", "", "HYP DEG")
        calc.testStep("atan", "0.693147181", "", "DEG")
        calc.testStep("+ 2 =", "2.693147181", "", "DEG")
        calc.testStep("DRG 5 hyp", "5", "", "HYP RAD")
        calc.testStep("sin", "74.20321058", "", "RAD")
        calc.testStep("+ 2 =", "76.20321058", "", "RAD")
        calc.testStep("DRG 5 hyp", "5", "", "HYP GRAD")
        calc.testStep("sin", "74.20321058", "", "GRAD")
        calc.testStep("+ 2 =", "76.20321058", "", "GRAD")

    }
}
