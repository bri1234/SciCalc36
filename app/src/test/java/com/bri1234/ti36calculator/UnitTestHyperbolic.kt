package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.views.DisplayLabels
import org.junit.Test

class UnitTestHyperbolic {
    @Test
    fun testHyperbolic() {
        val sim = CalculatorSimulator()

        sim.input("5 hyp")
        sim.assertDisplayLabel(DisplayLabels.HYP, true)
        sim.assertDisplayLabel(DisplayLabels.DEG, true)
        sim.input("sin")
        sim.assertDisplayLabel(DisplayLabels.HYP, false)
        sim.assertDisplay("74.20321058", "")
        sim.input("+ 2 =")
        sim.assertDisplay("76.20321058", "")

        sim.input("5 hyp")
        sim.assertDisplayLabel(DisplayLabels.HYP, true)
        sim.input("asin")
        sim.assertDisplayLabel(DisplayLabels.HYP, false)
        sim.assertDisplay("2.312438341", "")
        sim.input("+ 2 =")
        sim.assertDisplay("4.312438341", "")

        sim.input("4 hyp")
        sim.assertDisplayLabel(DisplayLabels.HYP, true)
        sim.input("cos")
        sim.assertDisplayLabel(DisplayLabels.HYP, false)
        sim.assertDisplay("27.30823284", "")
        sim.input("+ 2 =")
        sim.assertDisplay("29.30823284", "")

        sim.input("4 hyp")
        sim.assertDisplayLabel(DisplayLabels.HYP, true)
        sim.input("acos")
        sim.assertDisplayLabel(DisplayLabels.HYP, false)
        sim.assertDisplay("2.063437069", "")
        sim.input("+ 2 =")
        sim.assertDisplay("4.063437069", "")

        sim.input("4 hyp")
        sim.assertDisplayLabel(DisplayLabels.HYP, true)
        sim.input("tan")
        sim.assertDisplayLabel(DisplayLabels.HYP, false)
        sim.assertDisplay("0.9993293", "")
        sim.input("+ 2 =")
        sim.assertDisplay("2.9993293", "")

        sim.input(". 6 hyp")
        sim.assertDisplayLabel(DisplayLabels.HYP, true)
        sim.input("atan")
        sim.assertDisplayLabel(DisplayLabels.HYP, false)
        sim.assertDisplay("0.693147181", "")
        sim.input("+ 2 =")
        sim.assertDisplay("2.693147181", "")

        sim.input("DRG 5 hyp")
        sim.assertDisplayLabel(DisplayLabels.HYP, true)
        sim.assertDisplayLabel(DisplayLabels.RAD, true)
        sim.input("sin")
        sim.assertDisplayLabel(DisplayLabels.HYP, false)
        sim.assertDisplay("74.20321058", "")
        sim.input("+ 2 =")
        sim.assertDisplay("76.20321058", "")

        sim.input("DRG 5 hyp")
        sim.assertDisplayLabel(DisplayLabels.HYP, true)
        sim.assertDisplayLabel(DisplayLabels.GRAD, true)
        sim.input("sin")
        sim.assertDisplayLabel(DisplayLabels.HYP, false)
        sim.assertDisplay("74.20321058", "")
        sim.input("+ 2 =")
        sim.assertDisplay("76.20321058", "")


    }
}

