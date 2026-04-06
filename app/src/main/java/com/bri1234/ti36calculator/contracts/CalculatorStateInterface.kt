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

package com.bri1234.ti36calculator.contracts

import com.bri1234.ti36calculator.CalculatorState

/**
 * An interface for managing display labels on the calculator.
 */
interface CalculatorStateInterface {

    /**
     * Checks if the second function is active.
     * @return true if the second function is active, false otherwise.
     */
    fun isSecondFunctionActive() : Boolean

    /**
     * Checks if the third function is active.
     * @return true if the third function is active, false otherwise.
     */
    fun isThirdFunctionActive() : Boolean

    /**
     * Checks if a specific label is active on the display.
     * @param state The label to check for.
     * @return true if the label is active, false otherwise.
     */
    fun hasState(state: CalculatorState) : Boolean

    /**
     * Adds a label to the display.
     * @param state The label to add.
     */
    fun addState(state: CalculatorState)

    /**
    * Removes a label from the display.
    * @param state The label to remove.
    */
    fun removeState(state: CalculatorState)

    fun printNotImplemented()
}
