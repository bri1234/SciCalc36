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
