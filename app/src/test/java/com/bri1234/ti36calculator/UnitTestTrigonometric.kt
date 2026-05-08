package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.views.DisplayLabels
import org.junit.Test

class UnitTestTrigonometric {

    @Test
    fun testTrigonometric() {
        val calc = CalculatorCore()

        calc.assertDisplayLabel(DisplayLabels.DEG, true)
        calc.assertDisplayLabel(DisplayLabels.RAD, false)
        calc.assertDisplayLabel(DisplayLabels.GRAD, false)

        calc.input("9 0 sin")
        calc.assertDisplay("1.", "")
        calc.input("- 3 0 cos")
        calc.assertDisplay("0.866025404", "")
        calc.input("=")
        calc.assertDisplay("0.133974596", "")

        calc.input("1 asin")
        calc.assertDisplay("90.", "")
        calc.input("- . 5 =")
        calc.assertDisplay("89.5", "")
    }

    @Test
    fun testSin() {
        val calc = CalculatorCore()

        calc.assertDisplayLabel(DisplayLabels.DEG, true)
        calc.assertDisplayLabel(DisplayLabels.RAD, false)
        calc.assertDisplayLabel(DisplayLabels.GRAD, false)
        calc.input("3 2 sin")
        calc.assertDisplay("0.529919264", "")
        calc.input("4 2 sin asin")
        calc.assertDisplay("42.", "")

        calc.input("DRG")
        calc.assertDisplayLabel(DisplayLabels.DEG, false)
        calc.assertDisplayLabel(DisplayLabels.RAD, true)
        calc.assertDisplayLabel(DisplayLabels.GRAD, false)
        calc.input("3 . 2 sin")
        calc.assertDisplay("-0.058374143", "")
        calc.input("1 . 1 +/- sin asin")
        calc.assertDisplay("-1.1", "")

        calc.input("DRG")
        calc.assertDisplayLabel(DisplayLabels.DEG, false)
        calc.assertDisplayLabel(DisplayLabels.RAD, false)
        calc.assertDisplayLabel(DisplayLabels.GRAD, true)
        calc.input("3 3 . 2 sin")
        calc.assertDisplay("0.498185105", "")
        calc.input("4 2 . 5 +/- sin asin")
        calc.assertDisplay("-42.5", "")
    }

    @Test
    fun testCos() {
        val calc = CalculatorCore()

        calc.assertDisplayLabel(DisplayLabels.DEG, true)
        calc.assertDisplayLabel(DisplayLabels.RAD, false)
        calc.assertDisplayLabel(DisplayLabels.GRAD, false)
        calc.input("3 2 cos")
        calc.assertDisplay("0.848048096", "")
        calc.input("4 2 cos acos")
        calc.assertDisplay("42.", "")

        calc.input("DRG")
        calc.assertDisplayLabel(DisplayLabels.DEG, false)
        calc.assertDisplayLabel(DisplayLabels.RAD, true)
        calc.assertDisplayLabel(DisplayLabels.GRAD, false)
        calc.input("3 . 2 cos")
        calc.assertDisplay("-0.998294776", "")
        calc.input("1 . 1 +/- cos acos")
        calc.assertDisplay("1.1", "")

        calc.input("DRG")
        calc.assertDisplayLabel(DisplayLabels.DEG, false)
        calc.assertDisplayLabel(DisplayLabels.RAD, false)
        calc.assertDisplayLabel(DisplayLabels.GRAD, true)
        calc.input("3 3 . 2 cos")
        calc.assertDisplay("0.867070701", "")
        calc.input("4 2 . 5 +/- cos acos")
        calc.assertDisplay("42.5", "")
    }

    @Test
    fun testTan() {
        val calc = CalculatorCore()

        calc.assertDisplayLabel(DisplayLabels.DEG, true)
        calc.assertDisplayLabel(DisplayLabels.RAD, false)
        calc.assertDisplayLabel(DisplayLabels.GRAD, false)
        calc.input("3 2 tan")
        calc.assertDisplay("0.624869352", "")
        calc.input("4 2 tan atan")
        calc.assertDisplay("42.", "")

        calc.input("DRG")
        calc.assertDisplayLabel(DisplayLabels.DEG, false)
        calc.assertDisplayLabel(DisplayLabels.RAD, true)
        calc.assertDisplayLabel(DisplayLabels.GRAD, false)
        calc.input("3 . 2 tan")
        calc.assertDisplay("0.058473854", "")
        calc.input("1 . 1 +/- tan atan")
        calc.assertDisplay("-1.1", "")

        calc.input("DRG")
        calc.assertDisplayLabel(DisplayLabels.DEG, false)
        calc.assertDisplayLabel(DisplayLabels.RAD, false)
        calc.assertDisplayLabel(DisplayLabels.GRAD, true)
        calc.input("3 3 . 2 tan")
        calc.assertDisplay("0.574561111", "")
        calc.input("4 2 . 5 +/- tan atan")
        calc.assertDisplay("-42.5", "")
    }
}

