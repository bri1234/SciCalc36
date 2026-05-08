package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.views.DisplayLabels
import org.junit.Test

class UnitTestAngleUnit {
    @Test
    fun testAngleUnitCycle() {
        val calc = CalculatorCore()

        calc.input("AC/ON")
        calc.assertDisplayLabel(DisplayLabels.DEG, true)
        calc.assertDisplayLabel(DisplayLabels.RAD, false)
        calc.assertDisplayLabel(DisplayLabels.GRAD, false)

        calc.input("4 5 SIN")
        calc.assertDisplay("0.707106781", "")

        calc.input("4 5 DRG")
        calc.assertDisplay("45", "")
        calc.input("SIN")
        calc.assertDisplay("0.850903525", "")
        calc.assertDisplayLabel(DisplayLabels.DEG, false)
        calc.assertDisplayLabel(DisplayLabels.RAD, true)
        calc.assertDisplayLabel(DisplayLabels.GRAD, false)

        calc.input("CE/C")
        calc.assertDisplay("0.", "")
        calc.assertDisplayLabel(DisplayLabels.DEG, false)
        calc.assertDisplayLabel(DisplayLabels.RAD, true)
        calc.assertDisplayLabel(DisplayLabels.GRAD, false)

        calc.input("4 5 DRG")
        calc.assertDisplay("45", "")
        calc.input("SIN")
        calc.assertDisplay("0.649448048", "")
        calc.assertDisplayLabel(DisplayLabels.DEG, false)
        calc.assertDisplayLabel(DisplayLabels.RAD, false)
        calc.assertDisplayLabel(DisplayLabels.GRAD, true)

        calc.input("DRG")
        calc.assertDisplay("0.649448048", "")
        calc.assertDisplayLabel(DisplayLabels.DEG, true)
        calc.assertDisplayLabel(DisplayLabels.RAD, false)
        calc.assertDisplayLabel(DisplayLabels.GRAD, false)

        calc.input("DRG")
        calc.assertDisplayLabel(DisplayLabels.DEG, false)
        calc.assertDisplayLabel(DisplayLabels.RAD, true)
        calc.assertDisplayLabel(DisplayLabels.GRAD, false)

        calc.input("AC/ON")
        calc.assertDisplayLabel(DisplayLabels.DEG, true)
        calc.assertDisplayLabel(DisplayLabels.RAD, false)
        calc.assertDisplayLabel(DisplayLabels.GRAD, false)
    }

    @Test
    fun testAngleUnitCycleConvert() {
        val calc = CalculatorCore()

        calc.input("AC/ON")
        calc.assertDisplayLabel(DisplayLabels.DEG, true)
        calc.assertDisplayLabel(DisplayLabels.RAD, false)
        calc.assertDisplayLabel(DisplayLabels.GRAD, false)

        calc.input("4 5 SIN")
        calc.assertDisplay("0.707106781", "")

        calc.input("4 5 DRG>")
        calc.assertDisplay("0.785398163", "")
        calc.input("SIN")
        calc.assertDisplay("0.707106781", "")
        calc.assertDisplayLabel(DisplayLabels.DEG, false)
        calc.assertDisplayLabel(DisplayLabels.RAD, true)
        calc.assertDisplayLabel(DisplayLabels.GRAD, false)

        calc.input("CE/C")
        calc.assertDisplay("0.", "")
        calc.assertDisplayLabel(DisplayLabels.DEG, false)
        calc.assertDisplayLabel(DisplayLabels.RAD, true)
        calc.assertDisplayLabel(DisplayLabels.GRAD, false)

        calc.input("4 5 DRG>")
        calc.assertDisplay("2864.788976", "")
        calc.input("SIN")
        calc.assertDisplay("0.850903525", "")
        calc.assertDisplayLabel(DisplayLabels.DEG, false)
        calc.assertDisplayLabel(DisplayLabels.RAD, false)
        calc.assertDisplayLabel(DisplayLabels.GRAD, true)

        calc.input("DRG>")
        calc.assertDisplay("0.765813172", "")
        calc.assertDisplayLabel(DisplayLabels.DEG, true)
        calc.assertDisplayLabel(DisplayLabels.RAD, false)
        calc.assertDisplayLabel(DisplayLabels.GRAD, false)

        calc.input("DRG>")
        calc.assertDisplay("0.013365961", "")
        calc.assertDisplayLabel(DisplayLabels.DEG, false)
        calc.assertDisplayLabel(DisplayLabels.RAD, true)
        calc.assertDisplayLabel(DisplayLabels.GRAD, false)

        calc.input("AC/ON")
        calc.assertDisplayLabel(DisplayLabels.DEG, true)
        calc.assertDisplayLabel(DisplayLabels.RAD, false)
        calc.assertDisplayLabel(DisplayLabels.GRAD, false)
    }
}
