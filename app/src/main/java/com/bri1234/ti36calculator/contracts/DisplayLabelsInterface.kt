package com.bri1234.ti36calculator.contracts

import com.bri1234.ti36calculator.DisplayLabels

interface DisplayLabelsInterface {

    fun isSecondFunctionActive() : Boolean
    fun isThirdFunctionActive() : Boolean

    fun hasLabel(label: DisplayLabels) : Boolean
    fun addLabel(label: DisplayLabels)
    fun removeLabel(label: DisplayLabels)

}
