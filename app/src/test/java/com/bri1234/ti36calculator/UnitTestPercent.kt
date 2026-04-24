package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestPercent {
    @Test
    fun testPercent() {
        val sim = CalculatorSimulator()

        sim.input("AC/ON 2 5 0 * 5 %")
        sim.assertDisplay("0.05", "")
        sim.input("=")
        sim.assertDisplay("12.5", "")

        sim.input("AC/ON 2 5 0 / 5 %")
        sim.assertDisplay("0.05", "")
        sim.input("=")
        sim.assertDisplay("5000.", "")

        sim.input("2 5 0 + 5 %")
        sim.assertDisplay("12.5", "")
        sim.input("=")
        sim.assertDisplay("262.5", "")

        sim.input("2 5 0 - 5 %")
        sim.assertDisplay("12.5", "")
        sim.input("=")
        sim.assertDisplay("237.5", "")
    }
}
