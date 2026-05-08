package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.views.DisplayLabels
import org.junit.Test

class UnitTestInput {
    @Test
    fun testInput() {
        val calc = CalculatorCore()

        calc.input("AC/ON")
        calc.assertDisplay("0.", "")
        calc.input("4")
        calc.assertDisplay("4", "")
        calc.input("2")
        calc.assertDisplay("42", "")
        calc.input("=")
        calc.assertDisplay("42.", "")

    }

    @Test
    fun testSecondThirdHyp() {
        val calc = CalculatorCore()

        calc.assertDisplay("0.", "")

        calc.assertDisplayLabel(DisplayLabels.SECOND, false)
        calc.assertDisplayLabel(DisplayLabels.THIRD, false)
        calc.assertDisplayLabel(DisplayLabels.HYP, false)

        calc.input("2nd")
        calc.assertDisplayLabel(DisplayLabels.SECOND, true)
        calc.assertDisplayLabel(DisplayLabels.THIRD, false)
        calc.assertDisplayLabel(DisplayLabels.HYP, false)

        calc.input("3rd")
        calc.assertDisplayLabel(DisplayLabels.SECOND, false)
        calc.assertDisplayLabel(DisplayLabels.THIRD, true)
        calc.assertDisplayLabel(DisplayLabels.HYP, false)

        calc.input("2nd")
        calc.assertDisplayLabel(DisplayLabels.SECOND, true)
        calc.assertDisplayLabel(DisplayLabels.THIRD, false)
        calc.assertDisplayLabel(DisplayLabels.HYP, false)

        calc.input("2nd")
        calc.assertDisplayLabel(DisplayLabels.SECOND, false)
        calc.assertDisplayLabel(DisplayLabels.THIRD, false)
        calc.assertDisplayLabel(DisplayLabels.HYP, false)

        calc.input("3rd")
        calc.assertDisplayLabel(DisplayLabels.SECOND, false)
        calc.assertDisplayLabel(DisplayLabels.THIRD, true)
        calc.assertDisplayLabel(DisplayLabels.HYP, false)

        calc.input("3rd")
        calc.assertDisplayLabel(DisplayLabels.SECOND, false)
        calc.assertDisplayLabel(DisplayLabels.THIRD, false)
        calc.assertDisplayLabel(DisplayLabels.HYP, false)

        calc.input("HYP")
        calc.assertDisplayLabel(DisplayLabels.SECOND, false)
        calc.assertDisplayLabel(DisplayLabels.THIRD, false)
        calc.assertDisplayLabel(DisplayLabels.HYP, true)

        calc.input("HYP")
        calc.assertDisplayLabel(DisplayLabels.SECOND, false)
        calc.assertDisplayLabel(DisplayLabels.THIRD, false)
        calc.assertDisplayLabel(DisplayLabels.HYP, false)

        calc.input("HYP 3rd")
        calc.assertDisplayLabel(DisplayLabels.SECOND, false)
        calc.assertDisplayLabel(DisplayLabels.THIRD, true)
        calc.assertDisplayLabel(DisplayLabels.HYP, true)
        calc.input("2nd")
        calc.assertDisplayLabel(DisplayLabels.SECOND, true)
        calc.assertDisplayLabel(DisplayLabels.THIRD, false)
        calc.assertDisplayLabel(DisplayLabels.HYP, true)

        calc.input("AC/ON 2nd HYP")
        calc.assertDisplayLabel(DisplayLabels.SECOND, false)
        calc.assertDisplayLabel(DisplayLabels.THIRD, false)
        calc.assertDisplayLabel(DisplayLabels.HYP, false)
        
        calc.input("3rd HYP")
        calc.assertDisplayLabel(DisplayLabels.SECOND, false)
        calc.assertDisplayLabel(DisplayLabels.THIRD, false)
        calc.assertDisplayLabel(DisplayLabels.HYP, false)

    }

    @Test
    fun testUnusualInput() {
        val calc = CalculatorCore()

        calc.input("AC/ON 2")
        calc.assertDisplay("2", "")
        calc.input("*")
        calc.assertDisplay("2.", "")
        calc.input("*")
        calc.assertDisplay("2.", "")
        calc.input("*")
        calc.assertDisplay("2.", "")
        calc.input("5 =")
        calc.assertDisplay("10.", "")

        calc.input("AC/ON +/-")
        calc.assertDisplay("0.", "")
    }
}
