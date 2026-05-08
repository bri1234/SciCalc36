package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.views.DisplayLabels
import org.junit.Test

class UnitTestStat1 {

    @Test
    fun testStat1() {
        val calc = CalculatorCore()

        calc.input("AC/ON")
        calc.assertDisplay("0.", "")

        calc.input("STAT1")
        calc.assertDisplayLabel(DisplayLabels.STAT, true)
        calc.input("AC/ON")
        calc.assertDisplayLabel(DisplayLabels.STAT, false)

        calc.input("STAT1")
        calc.assertDisplayLabel(DisplayLabels.STAT, true)

        calc.input("4 S+")
        calc.assertDisplay("1.", "")
        calc.input("+ 5 =")
        calc.assertDisplay("6.", "")
        calc.input("3 S+")
        calc.assertDisplay("2.", "")
        calc.input("2 S+")
        calc.assertDisplay("3.", "")
        calc.input("+ 5 =")
        calc.assertDisplay("8.", "")
        calc.input("1 S+")
        calc.assertDisplay("4.", "")

        calc.input("Sx")
        calc.assertDisplay("10.", "")
        calc.input("Sx2")
        calc.assertDisplay("30.", "")
        calc.input("n")
        calc.assertDisplay("4.", "")
        calc.input("x_")
        calc.assertDisplay("2.5", "")
        calc.input("Sxn")
        calc.assertDisplay("1.118033989", "")
        calc.input("Sxn-1")
        calc.assertDisplay("1.290994449", "")


        calc.input("8 S-")
        calc.assertDisplay("3.", "")
        calc.input("9 S-")
        calc.assertDisplay("2.", "")

        calc.input("Sx")
        calc.assertDisplay("-7.", "")
        calc.input("Sx2")
        calc.assertDisplay("-115.", "")

        calc.input("CSR")

        calc.input("Sx")
        calc.assertDisplay("0.", "")
        calc.input("Sx2")
        calc.assertDisplay("0.", "")
    }

    @Test
    fun testStat1Frq() {
        val calc = CalculatorCore()

        calc.input("AC/ON")
        calc.assertDisplay("0.", "")

        calc.input("STAT1")
        calc.assertDisplayLabel(DisplayLabels.STAT, true)

        calc.input("3 . 1 4 FRQ")
        calc.assertDisplay("Fr 00", "")

        calc.input("4")
        calc.assertDisplay("Fr 04", "")
        calc.input("2")
        calc.assertDisplay("Fr 42", "")
        calc.input("S+")
        calc.assertDisplay("42.", "")

        calc.input("Sx")
        calc.assertDisplay("131.88", "")
        calc.input("Sx2")
        calc.assertDisplay("414.1032", "")

        calc.input("2 . 7 1 FRQ")
        calc.input("1")
        calc.assertDisplay("Fr 01", "")
        calc.input("2")
        calc.assertDisplay("Fr 12", "")
        calc.input("S-")
        calc.assertDisplay("30.", "")

        calc.input("Sx")
        calc.assertDisplay("99.36", "")
        calc.input("Sx2")
        calc.assertDisplay("325.974", "")

        calc.input("CE/C Sx")
        calc.assertDisplay("99.36", "")

        calc.input("AC/ON Sx")
        calc.assertDisplay("Error  ", "")

        calc.input("AC/ON STAT1 3 . 1 4 FRQ 8 6 SIN")
        calc.assertDisplay("0.05477591", "")

        calc.input("AC/ON STAT1 FRQ +/-")
        calc.assertDisplay("0.", "")

        calc.input("AC/ON FRQ")
        calc.assertDisplay("Error  ", "")
        calc.assertDisplayLabel(DisplayLabels.SECOND, false)

    }
}
