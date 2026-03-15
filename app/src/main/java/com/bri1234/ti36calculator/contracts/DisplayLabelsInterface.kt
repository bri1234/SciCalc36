package com.bri1234.ti36calculator.contracts

import com.bri1234.ti36calculator.DisplayLabels

/**
 * An interface for managing display labels on the calculator.
 */
interface DisplayLabelsInterface {

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
     * @param label The label to check for.
     * @return true if the label is active, false otherwise.
     */
    fun hasLabel(label: DisplayLabels) : Boolean

    /**
     * Adds a label to the display.
     * @param label The label to add.
     */
    fun addLabel(label: DisplayLabels)

    /**
    * Removes a label from the display.
    * @param label The label to remove.
    */
    fun removeLabel(label: DisplayLabels)

    fun printNotImplemented()
}
