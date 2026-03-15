package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.utils.ObserverSubject
import kotlin.text.get
import kotlin.text.set

private const val REGISTER_COUNT = 32
private const val OPERATION_COUNT = 256

private enum class Operation {
    NONE,
    ADDITION,
    SUBTRACTION,
    MULTIPLICATION,
    DIVISION,
    X_POW_Y,
    LEFT_PARENTHESES,
    RIGHT_PARENTHESES,
}

class Ti36Computation {

    val onResultChanged: ObserverSubject<Double> = ObserverSubject()

    private val registerArray: Array<Double> = Array(REGISTER_COUNT) { 0.0 }
    private var registerIndex: Int = 0
    private val operationArray: Array<Operation> = Array(OPERATION_COUNT) { Operation.NONE }
    private var operationIndex: Int = 0

    fun reset() {
        registerArray.fill(0.0)
        registerIndex = 0
        operationArray.fill(Operation.NONE)
        operationIndex = 0
    }

    fun getValue() : Double {
        return registerArray[registerIndex]
    }

    fun setValue(newValue: Double) {
        registerArray[registerIndex] = newValue
    }

    fun setResult(newValue: Double) {
        setValue(newValue)
        onResultChanged(newValue)
    }

}