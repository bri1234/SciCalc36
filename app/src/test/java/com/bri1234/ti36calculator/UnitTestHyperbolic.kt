package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.views.DisplayLabels
import org.junit.Test

class UnitTestHyperbolic {
    @Test
    fun testHyperbolic() {
        val calc = CalculatorCore()

        calc.input("5 hyp")
        calc.assertDisplayLabel(DisplayLabels.HYP, true)
        calc.assertDisplayLabel(DisplayLabels.DEG, true)
        calc.input("sin")
        calc.assertDisplayLabel(DisplayLabels.HYP, false)
        calc.assertDisplay("74.20321058", "")
        calc.input("+ 2 =")
        calc.assertDisplay("76.20321058", "")

        calc.input("5 hyp")
        calc.assertDisplayLabel(DisplayLabels.HYP, true)
        calc.input("asin")
        calc.assertDisplayLabel(DisplayLabels.HYP, false)
        calc.assertDisplay("2.312438341", "")
        calc.input("+ 2 =")
        calc.assertDisplay("4.312438341", "")

        calc.input("4 hyp")
        calc.assertDisplayLabel(DisplayLabels.HYP, true)
        calc.input("cos")
        calc.assertDisplayLabel(DisplayLabels.HYP, false)
        calc.assertDisplay("27.30823284", "")
        calc.input("+ 2 =")
        calc.assertDisplay("29.30823284", "")

        calc.input("4 hyp")
        calc.assertDisplayLabel(DisplayLabels.HYP, true)
        calc.input("acos")
        calc.assertDisplayLabel(DisplayLabels.HYP, false)
        calc.assertDisplay("2.063437069", "")
        calc.input("+ 2 =")
        calc.assertDisplay("4.063437069", "")

        calc.input("4 hyp")
        calc.assertDisplayLabel(DisplayLabels.HYP, true)
        calc.input("tan")
        calc.assertDisplayLabel(DisplayLabels.HYP, false)
        calc.assertDisplay("0.9993293", "")
        calc.input("+ 2 =")
        calc.assertDisplay("2.9993293", "")

        calc.input(". 6 hyp")
        calc.assertDisplayLabel(DisplayLabels.HYP, true)
        calc.input("atan")
        calc.assertDisplayLabel(DisplayLabels.HYP, false)
        calc.assertDisplay("0.693147181", "")
        calc.input("+ 2 =")
        calc.assertDisplay("2.693147181", "")

        calc.input("DRG 5 hyp")
        calc.assertDisplayLabel(DisplayLabels.HYP, true)
        calc.assertDisplayLabel(DisplayLabels.RAD, true)
        calc.input("sin")
        calc.assertDisplayLabel(DisplayLabels.HYP, false)
        calc.assertDisplay("74.20321058", "")
        calc.input("+ 2 =")
        calc.assertDisplay("76.20321058", "")

        calc.input("DRG 5 hyp")
        calc.assertDisplayLabel(DisplayLabels.HYP, true)
        calc.assertDisplayLabel(DisplayLabels.GRAD, true)
        calc.input("sin")
        calc.assertDisplayLabel(DisplayLabels.HYP, false)
        calc.assertDisplay("74.20321058", "")
        calc.input("+ 2 =")
        calc.assertDisplay("76.20321058", "")


    }
}

