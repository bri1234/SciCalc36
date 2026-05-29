package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestDegreesMinutesSeconds {
    @Test
    fun testDegreesMinutesSeconds() {
        val calc = CalculatorCore()

        calc.testStep("3 0 . 0 9 0 9 0 >DD", "30.1525", "", "DEG")
        calc.testStep("AC/ON 3 0 . 1 5 2 5 >DMS", "30°09'09\"0", "", "DEG")
        calc.testStep("AC/ON 3 0 . 5 9 5 9 9 >DD >DMS", "30°59'59\"9", "", "DEG")
        calc.testStep("AC/ON 3 . 1 5 2 7 4 >DMS", "3°09'09\"86", "", "DEG")
        calc.testStep("AC/ON 3 0 . 1 5 2 7 4 >DMS", "30°09'09\"9", "", "DEG")
        calc.testStep("AC/ON 3 0 0 . 1 5 2 7 4 >DMS", "300°09'10\"", "", "DEG")
        calc.testStep("AC/ON 3 0 0 0 . 1 5 2 7 4 >DMS", "3000°09'10", "", "DEG")

    }
}
