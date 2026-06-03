package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestParenthesis {

    @Test
    fun testParenthesis() {
        val calc = CalculatorCore()

        calc.testStep("(", "0.", "", "DEG ()")

        // TODO: add tests for parenthesis
    }
}

