/*
 * SciCalc 36 - A classic-style scientific calculator inspired by traditional handheld calculator workflows.
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

package com.bri1234.scicalc36

import com.bri1234.scicalc36.enums.CalculatorAngleUnit
import com.bri1234.scicalc36.enums.CalculatorFunction
import com.bri1234.scicalc36.enums.CalculatorHypMode
import com.bri1234.scicalc36.enums.CalculatorNumberMode
import com.bri1234.scicalc36.enums.CalculatorStatisticMode
import com.bri1234.scicalc36.enums.RectangularPolarView

/**
 * A class to hold the state of the calculator, including the current angle unit, function,
 * statistic mode, hyp mode, and number mode.
 */
class CalculatorState {

    var calculatorAngleUnit: CalculatorAngleUnit = CalculatorAngleUnit.DEG

    var calculatorFunction: CalculatorFunction = CalculatorFunction.FIRST

    var calculatorStatisticMode: CalculatorStatisticMode = CalculatorStatisticMode.OFF

    var calculatorHypMode: CalculatorHypMode = CalculatorHypMode.OFF

    var calculatorNumberMode: CalculatorNumberMode = CalculatorNumberMode.DECIMAL

    /**
     * The current rectangular polar view mode for conversions between rectangular and polar coordinates.
     */
    var rectangularPolarView: RectangularPolarView = RectangularPolarView.OFF

    /**
     * Resets the calculator state to the default values.
     */
    fun reset() {
        calculatorAngleUnit = CalculatorAngleUnit.DEG
        calculatorFunction = CalculatorFunction.FIRST
        calculatorStatisticMode = CalculatorStatisticMode.OFF
        calculatorHypMode = CalculatorHypMode.OFF
        calculatorNumberMode = CalculatorNumberMode.DECIMAL
        rectangularPolarView = RectangularPolarView.OFF
    }
}
