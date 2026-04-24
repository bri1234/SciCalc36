package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.views.DisplayLabels
import org.junit.Test

class UnitTestTrigonometric {

    @Test
    fun testTrigonometric() {
        val sim = CalculatorSimulator()

        sim.assertDisplayLabel(DisplayLabels.DEG, true)
        sim.assertDisplayLabel(DisplayLabels.RAD, false)
        sim.assertDisplayLabel(DisplayLabels.GRAD, false)

        sim.input("9 0 sin")
        sim.assertDisplay("1.", "")
        sim.input("- 3 0 cos")
        sim.assertDisplay("0.866025404", "")
        sim.input("=")
        sim.assertDisplay("0.133974596", "")

        sim.input("1 asin")
        sim.assertDisplay("90.", "")
        sim.input("- . 5 =")
        sim.assertDisplay("89.5", "")
    }

    @Test
    fun testSin() {
        val sim = CalculatorSimulator()

        sim.assertDisplayLabel(DisplayLabels.DEG, true)
        sim.assertDisplayLabel(DisplayLabels.RAD, false)
        sim.assertDisplayLabel(DisplayLabels.GRAD, false)
        sim.input("3 2 sin")
        sim.assertDisplay("0.529919264", "")
        sim.input("4 2 sin asin")
        sim.assertDisplay("42.", "")

        sim.input("DRG")
        sim.assertDisplayLabel(DisplayLabels.DEG, false)
        sim.assertDisplayLabel(DisplayLabels.RAD, true)
        sim.assertDisplayLabel(DisplayLabels.GRAD, false)
        sim.input("3 . 2 sin")
        sim.assertDisplay("-0.058374143", "")
        sim.input("1 . 1 +/- sin asin")
        sim.assertDisplay("-1.1", "")

        sim.input("DRG")
        sim.assertDisplayLabel(DisplayLabels.DEG, false)
        sim.assertDisplayLabel(DisplayLabels.RAD, false)
        sim.assertDisplayLabel(DisplayLabels.GRAD, true)
        sim.input("3 3 . 2 sin")
        sim.assertDisplay("0.498185105", "")
        sim.input("4 2 . 5 +/- sin asin")
        sim.assertDisplay("-42.5", "")
    }

    @Test
    fun testCos() {
        val sim = CalculatorSimulator()

        sim.assertDisplayLabel(DisplayLabels.DEG, true)
        sim.assertDisplayLabel(DisplayLabels.RAD, false)
        sim.assertDisplayLabel(DisplayLabels.GRAD, false)
        sim.input("3 2 cos")
        sim.assertDisplay("0.848048096", "")
        sim.input("4 2 cos acos")
        sim.assertDisplay("42.", "")

        sim.input("DRG")
        sim.assertDisplayLabel(DisplayLabels.DEG, false)
        sim.assertDisplayLabel(DisplayLabels.RAD, true)
        sim.assertDisplayLabel(DisplayLabels.GRAD, false)
        sim.input("3 . 2 cos")
        sim.assertDisplay("-0.998294776", "")
        sim.input("1 . 1 +/- cos acos")
        sim.assertDisplay("1.1", "")

        sim.input("DRG")
        sim.assertDisplayLabel(DisplayLabels.DEG, false)
        sim.assertDisplayLabel(DisplayLabels.RAD, false)
        sim.assertDisplayLabel(DisplayLabels.GRAD, true)
        sim.input("3 3 . 2 cos")
        sim.assertDisplay("0.867070701", "")
        sim.input("4 2 . 5 +/- cos acos")
        sim.assertDisplay("42.5", "")
    }

    @Test
    fun testTan() {
        val sim = CalculatorSimulator()

        sim.assertDisplayLabel(DisplayLabels.DEG, true)
        sim.assertDisplayLabel(DisplayLabels.RAD, false)
        sim.assertDisplayLabel(DisplayLabels.GRAD, false)
        sim.input("3 2 tan")
        sim.assertDisplay("0.624869352", "")
        sim.input("4 2 tan atan")
        sim.assertDisplay("42.", "")

        sim.input("DRG")
        sim.assertDisplayLabel(DisplayLabels.DEG, false)
        sim.assertDisplayLabel(DisplayLabels.RAD, true)
        sim.assertDisplayLabel(DisplayLabels.GRAD, false)
        sim.input("3 . 2 tan")
        sim.assertDisplay("0.058473854", "")
        sim.input("1 . 1 +/- tan atan")
        sim.assertDisplay("-1.1", "")

        sim.input("DRG")
        sim.assertDisplayLabel(DisplayLabels.DEG, false)
        sim.assertDisplayLabel(DisplayLabels.RAD, false)
        sim.assertDisplayLabel(DisplayLabels.GRAD, true)
        sim.input("3 3 . 2 tan")
        sim.assertDisplay("0.574561111", "")
        sim.input("4 2 . 5 +/- tan atan")
        sim.assertDisplay("-42.5", "")
    }
}

