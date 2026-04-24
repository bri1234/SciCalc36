package com.bri1234.ti36calculator

import org.junit.Test

class UnitTestDegreesMinutesSeconds {
    @Test
    fun testDegreesMinutesSeconds() {
        val sim = CalculatorSimulator()

        sim.input("3 0 . 0 9 0 9 0 >DD")
        sim.assertDisplay("30.1525", "")

        sim.input("AC/ON 3 0 . 1 5 2 5 >DMS")
        sim.assertDisplay("30°09'09\"0", "")

        sim.input("AC/ON 3 0 . 5 9 5 9 9 >DD >DMS")
        sim.assertDisplay("30°59'59\"9", "")

        sim.input("AC/ON 3 . 1 5 2 7 4 >DMS")
        sim.assertDisplay("3°09'09\"86", "")

        sim.input("AC/ON 3 0 . 1 5 2 7 4 >DMS")
        sim.assertDisplay("30°09'09\"9", "")

        sim.input("AC/ON 3 0 0 . 1 5 2 7 4 >DMS")
        sim.assertDisplay("300°09'10\"", "")

        sim.input("AC/ON 3 0 0 0 . 1 5 2 7 4 >DMS")
        sim.assertDisplay("3000°09'10", "")
    }
}
