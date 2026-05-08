package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestDegreesMinutesSeconds {
    @Test
    fun testDegreesMinutesSeconds() {
        val calc = CalculatorCore()

        calc.input("3 0 . 0 9 0 9 0 >DD")
        calc.assertDisplay("30.1525", "")

        calc.input("AC/ON 3 0 . 1 5 2 5 >DMS")
        calc.assertDisplay("30°09'09\"0", "")

        calc.input("AC/ON 3 0 . 5 9 5 9 9 >DD >DMS")
        calc.assertDisplay("30°59'59\"9", "")

        calc.input("AC/ON 3 . 1 5 2 7 4 >DMS")
        calc.assertDisplay("3°09'09\"86", "")

        calc.input("AC/ON 3 0 . 1 5 2 7 4 >DMS")
        calc.assertDisplay("30°09'09\"9", "")

        calc.input("AC/ON 3 0 0 . 1 5 2 7 4 >DMS")
        calc.assertDisplay("300°09'10\"", "")

        calc.input("AC/ON 3 0 0 0 . 1 5 2 7 4 >DMS")
        calc.assertDisplay("3000°09'10", "")
    }
}
