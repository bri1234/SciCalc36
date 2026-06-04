/*
 * Ti36Calculator - A TI-36 calculator simulator for Android.
 * Copyright (C) 2026 Torsten Brischalle <torsten@brischalle.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://gnu.org>.
 */

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
