package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.views.DisplayLabels
import org.junit.Test

class UnitTestInput {
    @Test
    fun testInput() {
        val sim = CalculatorSimulator()

        sim.buttonPressed(CalculatorButton.AC_ON)
        sim.assertDisplay("0.", "")
        sim.input("4")
        sim.assertDisplay("4", "")
        sim.input("2")
        sim.assertDisplay("42", "")
        sim.input("=")
        sim.assertDisplay("42.", "")

    }

    @Test
    fun testSecondThirdHyp() {
        val sim = CalculatorSimulator()

        sim.assertDisplay("0.", "")

        sim.assertDisplayLabel(DisplayLabels.SECOND, false)
        sim.assertDisplayLabel(DisplayLabels.THIRD, false)
        sim.assertDisplayLabel(DisplayLabels.HYP, false)

        sim.input("2nd")
        sim.assertDisplayLabel(DisplayLabels.SECOND, true)
        sim.assertDisplayLabel(DisplayLabels.THIRD, false)
        sim.assertDisplayLabel(DisplayLabels.HYP, false)

        sim.input("3rd")
        sim.assertDisplayLabel(DisplayLabels.SECOND, false)
        sim.assertDisplayLabel(DisplayLabels.THIRD, true)
        sim.assertDisplayLabel(DisplayLabels.HYP, false)

        sim.input("2nd")
        sim.assertDisplayLabel(DisplayLabels.SECOND, true)
        sim.assertDisplayLabel(DisplayLabels.THIRD, false)
        sim.assertDisplayLabel(DisplayLabels.HYP, false)

        sim.input("2nd")
        sim.assertDisplayLabel(DisplayLabels.SECOND, false)
        sim.assertDisplayLabel(DisplayLabels.THIRD, false)
        sim.assertDisplayLabel(DisplayLabels.HYP, false)

        sim.input("3rd")
        sim.assertDisplayLabel(DisplayLabels.SECOND, false)
        sim.assertDisplayLabel(DisplayLabels.THIRD, true)
        sim.assertDisplayLabel(DisplayLabels.HYP, false)

        sim.input("3rd")
        sim.assertDisplayLabel(DisplayLabels.SECOND, false)
        sim.assertDisplayLabel(DisplayLabels.THIRD, false)
        sim.assertDisplayLabel(DisplayLabels.HYP, false)

        sim.input("hyp")
        sim.assertDisplayLabel(DisplayLabels.SECOND, false)
        sim.assertDisplayLabel(DisplayLabels.THIRD, false)
        sim.assertDisplayLabel(DisplayLabels.HYP, true)

        sim.input("hyp")
        sim.assertDisplayLabel(DisplayLabels.SECOND, false)
        sim.assertDisplayLabel(DisplayLabels.THIRD, false)
        sim.assertDisplayLabel(DisplayLabels.HYP, false)

        sim.input("hyp 3rd")
        sim.assertDisplayLabel(DisplayLabels.SECOND, false)
        sim.assertDisplayLabel(DisplayLabels.THIRD, true)
        sim.assertDisplayLabel(DisplayLabels.HYP, true)
        sim.input("2nd")
        sim.assertDisplayLabel(DisplayLabels.SECOND, true)
        sim.assertDisplayLabel(DisplayLabels.THIRD, false)
        sim.assertDisplayLabel(DisplayLabels.HYP, true)

        sim.input(CalculatorButton.AC_ON)
        sim.input("2nd hyp")
        sim.assertDisplayLabel(DisplayLabels.SECOND, false)
        sim.assertDisplayLabel(DisplayLabels.THIRD, false)
        sim.assertDisplayLabel(DisplayLabels.HYP, false)
        
        sim.input("3rd hyp")
        sim.assertDisplayLabel(DisplayLabels.SECOND, false)
        sim.assertDisplayLabel(DisplayLabels.THIRD, false)
        sim.assertDisplayLabel(DisplayLabels.HYP, false)

    }
}
