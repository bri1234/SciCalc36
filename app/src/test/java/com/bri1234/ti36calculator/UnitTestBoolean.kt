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

class UnitTestBoolean {

    @Test
    fun testAnd() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON HEX A AND C =", "8", "", "HEX")
        calc.testStep("AC/ON HEX F 0 AND 3 C =", "30", "", "HEX")

        calc.testStep("AC/ON OCT 7 0 AND 3 6 =", "30", "", "OCT")
        calc.testStep("AC/ON OCT 1 2 3 AND 7 7 =", "23", "", "OCT")

        calc.testStep("AC/ON BIN 1 0 1 0 AND 1 1 0 0 =", "1000", "", "BIN")
        calc.testStep("AC/ON BIN 1 1 1 1 AND 1 0 1 =", "101", "", "BIN")

        calc.testStep("AC/ON DEC 1 2 AND 1 0 =", "8.", "", "DEG")
        calc.testStep("AC/ON DEC 7 AND 3 =", "3.", "", "DEG")
    }

    @Test
    fun testOr() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON HEX A OR 5 =", "F", "", "HEX")
        calc.testStep("AC/ON HEX C 3 OR 5 C =", "dF", "", "HEX")

        calc.testStep("AC/ON OCT 7 0 OR 3 6 =", "76", "", "OCT")
        calc.testStep("AC/ON OCT 1 2 3 OR 7 7 =", "177", "", "OCT")

        calc.testStep("AC/ON BIN 1 0 1 0 OR 1 1 0 0 =", "1110", "", "BIN")
        calc.testStep("AC/ON BIN 1 1 1 1 OR 1 0 1 =", "1111", "", "BIN")

        calc.testStep("AC/ON DEC 1 2 OR 1 0 =", "14.", "", "DEG")
        calc.testStep("AC/ON DEC 7 OR 8 =", "15.", "", "DEG")
    }

    @Test
    fun testXor() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON HEX 9 F XOR 1 =", "9E", "", "HEX")
        calc.testStep("AC/ON HEX 7 F XOR 3 C =", "43", "", "HEX")

        calc.testStep("AC/ON OCT 7 0 XOR 3 6 =", "46", "", "OCT")
        calc.testStep("AC/ON OCT 1 2 3 XOR 7 7 =", "154", "", "OCT")

        calc.testStep("AC/ON BIN 1 0 1 0 XOR 1 1 0 0 =", "110", "", "BIN")
        calc.testStep("AC/ON BIN 1 1 1 1 XOR 1 0 1 =", "1010", "", "BIN")

        calc.testStep("AC/ON DEC 1 2 XOR 1 0 =", "6.", "", "DEG")
        calc.testStep("AC/ON DEC 7 XOR 3 =", "4.", "", "DEG")
    }

    @Test
    fun testXnor() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON HEX A XNOR C =", "FFFFFFFFF9", "", "HEX")
        calc.testStep("AC/ON HEX F F XNOR 0 =", "FFFFFFFF00", "", "HEX")

        calc.testStep("AC/ON OCT 7 0 XNOR 3 6 =", "7777777731", "", "OCT")
        calc.testStep("AC/ON OCT 1 2 3 XNOR 7 7 =", "7777777623", "", "OCT")

        calc.testStep("AC/ON BIN 1 0 1 0 XNOR 1 1 0 0 =", "1111111001", "", "BIN")
        calc.testStep("AC/ON BIN 1 1 1 1 XNOR 1 0 1 =", "1111110101", "", "BIN")

        calc.testStep("AC/ON DEC 1 2 XNOR 1 0 =", "-7.", "", "DEG")
        calc.testStep("AC/ON DEC 7 XNOR 3 =", "-5.", "", "DEG")
    }

    @Test
    fun testNot() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON HEX A NOT", "FFFFFFFFF5", "", "HEX")
        calc.testStep("AC/ON HEX 0 NOT", "FFFFFFFFFF", "", "HEX")

        calc.testStep("AC/ON OCT 5 NOT", "7777777772", "", "OCT")
        calc.testStep("AC/ON OCT 0 NOT", "7777777777", "", "OCT")

        calc.testStep("AC/ON BIN 1 0 1 NOT", "1111111010", "", "BIN")
        calc.testStep("AC/ON BIN 0 NOT", "1111111111", "", "BIN")

        calc.testStep("AC/ON DEC 5 NOT", "-6.", "", "DEG")
        calc.testStep("AC/ON DEC 0 NOT", "-1.", "", "DEG")
    }

    @Test
    fun testBooleanWithArithmetic() {
        val calc = CalculatorCore()

        calc.testStep("AC/ON HEX A + 5 AND F =", "F", "", "HEX")
        calc.testStep("AC/ON HEX F 0 - A OR 3 =", "E7", "", "HEX")
        calc.testStep("AC/ON HEX 6 * 7 XOR 1 =", "2b", "", "HEX")
        calc.testStep("AC/ON HEX 8 0 / 4 XNOR F =", "FFFFFFFFd0", "", "HEX")

        calc.testStep("AC/ON OCT 1 2 + 5 AND 7 =", "7", "", "OCT")
        calc.testStep("AC/ON OCT 7 0 - 3 OR 1 0 =", "75", "", "OCT")
        calc.testStep("AC/ON OCT 6 * 7 XOR 1 =", "53", "", "OCT")
        calc.testStep("AC/ON OCT 1 0 0 / 4 XNOR 7 =", "7777777750", "", "OCT")

        calc.testStep("AC/ON BIN 1 0 1 + 1 AND 1 1 1 =", "110", "", "BIN")
        calc.testStep("AC/ON BIN 1 1 1 1 - 1 0 OR 1 =", "1101", "", "BIN")
        calc.testStep("AC/ON BIN 1 0 1 * 1 1 XOR 1 =", "1110", "", "BIN")
        calc.testStep("AC/ON BIN 1 0 0 0 / 1 0 XNOR 1 =", "1111111010", "", "BIN")

        calc.testStep("AC/ON DEC 5 + 3 AND 6 =", "0.", "", "DEG")
        calc.testStep("AC/ON DEC 1 2 - 5 OR 8 =", "15.", "", "DEG")
        calc.testStep("AC/ON DEC 3 * 4 XOR 1 0 =", "6.", "", "DEG")
        calc.testStep("AC/ON DEC 2 0 / 4 XNOR 3 =", "-7.", "", "DEG")
    }
}
