package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.views.DisplayLabels
import org.junit.Test

class TestMode {
    @Test
    fun testMode() {
        val calc = CalculatorCore()

        calc.input("STAT1")
        calc.assertDisplayLabel(DisplayLabels.DEG, true)
        calc.assertDisplayLabel(DisplayLabels.STAT, true)
        calc.assertDisplayLabel(DisplayLabels.HEX, false)

        calc.input("HEX")
        calc.assertDisplayLabel(DisplayLabels.DEG, true)
        calc.assertDisplayLabel(DisplayLabels.STAT, false)
        calc.assertDisplayLabel(DisplayLabels.HEX, true)

        calc.input("STAT1")
        calc.assertDisplayLabel(DisplayLabels.DEG, true)
        calc.assertDisplayLabel(DisplayLabels.STAT, true)
        calc.assertDisplayLabel(DisplayLabels.HEX, false)

        calc.input("STAT1")
        calc.assertDisplayLabel(DisplayLabels.STAT, true)
        calc.assertDisplayLabel(DisplayLabels.BIN, false)

        calc.input("BIN")
        calc.assertDisplayLabel(DisplayLabels.STAT, false)
        calc.assertDisplayLabel(DisplayLabels.BIN, true)

        calc.input("STAT1")
        calc.assertDisplayLabel(DisplayLabels.STAT, true)
        calc.assertDisplayLabel(DisplayLabels.OCT, false)

        calc.input("OCT")
        calc.assertDisplayLabel(DisplayLabels.STAT, false)
        calc.assertDisplayLabel(DisplayLabels.OCT, true)
    }
}

