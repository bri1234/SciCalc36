package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestHexOctBin {
    @Test
    fun testConvert() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON HEX 2 5 DEC", "37", "", "DEG")
        calc.testStep("AC/ON OCT 2 5 DEC", "21", "", "DEG")
        calc.testStep("AC/ON BIN 1 1 0 0 1 1 DEC", "51", "", "DEG")

        calc.testStep("AC/ON 2 5 HEX", "19", "", "HEX")
        calc.testStep("OCT", "31", "", "OCT")
        calc.testStep("BIN", "11001", "", "BIN")
        calc.testStep("DEC", "25", "", "DEG")

    }

    @Test
    fun testCalculate() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON HEX A B + 1 5 =", "bd", "", "HEX")
        calc.testStep("AC/ON HEX 1 0 - 1 =", "F", "", "HEX")
        calc.testStep("AC/ON HEX 3 * 5 =", "F", "", "HEX")
        calc.testStep("AC/ON HEX F / 3 =", "5", "", "HEX")
        calc.testStep("AC/ON HEX F F / 7 =", "24", "", "HEX")
        calc.testStep("* 7 =", "FC", "", "HEX")

        calc.testStep("AC/ON OCT 1 0 + 7 =", "17", "", "OCT")
        calc.testStep("AC/ON OCT 1 0 - 1 =", "7", "", "OCT")
        calc.testStep("AC/ON OCT 3 * 2 =", "6", "", "OCT")
        calc.testStep("AC/ON OCT 6 / 2 =", "3", "", "OCT")

        calc.testStep("AC/ON BIN 1 0 + 1 =", "11", "", "BIN")
        calc.testStep("AC/ON BIN 1 0 - 1 =", "1", "", "BIN")
        calc.testStep("AC/ON BIN 1 0 * 1 1 =", "110", "", "BIN")
        calc.testStep("AC/ON BIN 1 1 0 / 1 0 =", "11", "", "BIN")
    }

    @Test
    fun testSign() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON HEX 4 2 +/-", "FFFFFFFFbE", "", "HEX")
        calc.testStep("+/-", "42", "", "HEX")

        calc.testStep("AC/ON OCT 4 2 +/-", "7777777736", "", "OCT")
        calc.testStep("+/-", "42", "", "OCT")

        calc.testStep("AC/ON BIN 1 0 1 +/-", "1111111011", "", "BIN")
        calc.testStep("+/-", "101", "", "BIN")
    }
}
