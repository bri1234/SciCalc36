package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.views.DisplayLabels
import org.junit.Test

class UnitTestAngleUnit {
    @Test
    fun testAngleUnitCycle() {
        val sim = CalculatorSimulator()

        sim.buttonPressed(CalculatorButton.AC_ON)
        sim.assertDisplayLabel(DisplayLabels.DEG, true)
        sim.assertDisplayLabel(DisplayLabels.RAD, false)
        sim.assertDisplayLabel(DisplayLabels.GRAD, false)

        sim.input("4 5 sin")
        sim.assertDisplay("0.707106781", "")

        sim.input("4 5 2nd hyp")
        sim.assertDisplay("45", "")
        sim.input("sin")
        sim.assertDisplay("0.850903525", "")
        sim.assertDisplayLabel(DisplayLabels.DEG, false)
        sim.assertDisplayLabel(DisplayLabels.RAD, true)
        sim.assertDisplayLabel(DisplayLabels.GRAD, false)

        sim.input(CalculatorButton.CE_C)
        sim.assertDisplay("0.", "")
        sim.assertDisplayLabel(DisplayLabels.DEG, false)
        sim.assertDisplayLabel(DisplayLabels.RAD, true)
        sim.assertDisplayLabel(DisplayLabels.GRAD, false)

        sim.input("4 5 2nd hyp")
        sim.assertDisplay("45", "")
        sim.input("sin")
        sim.assertDisplay("0.649448048", "")
        sim.assertDisplayLabel(DisplayLabels.DEG, false)
        sim.assertDisplayLabel(DisplayLabels.RAD, false)
        sim.assertDisplayLabel(DisplayLabels.GRAD, true)

        sim.input("2nd hyp")
        sim.assertDisplay("0.649448048", "")
        sim.assertDisplayLabel(DisplayLabels.DEG, true)
        sim.assertDisplayLabel(DisplayLabels.RAD, false)
        sim.assertDisplayLabel(DisplayLabels.GRAD, false)

        sim.input("2nd hyp")
        sim.assertDisplayLabel(DisplayLabels.DEG, false)
        sim.assertDisplayLabel(DisplayLabels.RAD, true)
        sim.assertDisplayLabel(DisplayLabels.GRAD, false)

        sim.buttonPressed(CalculatorButton.AC_ON)
        sim.assertDisplayLabel(DisplayLabels.DEG, true)
        sim.assertDisplayLabel(DisplayLabels.RAD, false)
        sim.assertDisplayLabel(DisplayLabels.GRAD, false)
    }

    @Test
    fun testAngleUnitCycleConvert() {
        val sim = CalculatorSimulator()

        sim.buttonPressed(CalculatorButton.AC_ON)
        sim.assertDisplayLabel(DisplayLabels.DEG, true)
        sim.assertDisplayLabel(DisplayLabels.RAD, false)
        sim.assertDisplayLabel(DisplayLabels.GRAD, false)

        sim.input("4 5 sin")
        sim.assertDisplay("0.707106781", "")

        sim.input("4 5 3rd hyp")
        sim.assertDisplay("0.785398163", "")
        sim.input("sin")
        sim.assertDisplay("0.707106781", "")
        sim.assertDisplayLabel(DisplayLabels.DEG, false)
        sim.assertDisplayLabel(DisplayLabels.RAD, true)
        sim.assertDisplayLabel(DisplayLabels.GRAD, false)

        sim.input(CalculatorButton.CE_C)
        sim.assertDisplay("0.", "")
        sim.assertDisplayLabel(DisplayLabels.DEG, false)
        sim.assertDisplayLabel(DisplayLabels.RAD, true)
        sim.assertDisplayLabel(DisplayLabels.GRAD, false)

        sim.input("4 5 3rd hyp")
        sim.assertDisplay("2864.788976", "")
        sim.input("sin")
        sim.assertDisplay("0.850903525", "")
        sim.assertDisplayLabel(DisplayLabels.DEG, false)
        sim.assertDisplayLabel(DisplayLabels.RAD, false)
        sim.assertDisplayLabel(DisplayLabels.GRAD, true)

        sim.input("3rd hyp")
        sim.assertDisplay("0.765813172", "")
        sim.assertDisplayLabel(DisplayLabels.DEG, true)
        sim.assertDisplayLabel(DisplayLabels.RAD, false)
        sim.assertDisplayLabel(DisplayLabels.GRAD, false)

        sim.input("3rd hyp")
        sim.assertDisplay("0.013365961", "")
        sim.assertDisplayLabel(DisplayLabels.DEG, false)
        sim.assertDisplayLabel(DisplayLabels.RAD, true)
        sim.assertDisplayLabel(DisplayLabels.GRAD, false)

        sim.buttonPressed(CalculatorButton.AC_ON)
        sim.assertDisplayLabel(DisplayLabels.DEG, true)
        sim.assertDisplayLabel(DisplayLabels.RAD, false)
        sim.assertDisplayLabel(DisplayLabels.GRAD, false)
    }
}
