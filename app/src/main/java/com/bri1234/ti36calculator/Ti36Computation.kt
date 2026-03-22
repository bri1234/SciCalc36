package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.utils.ObserverSubject

private const val REGISTER_COUNT = 32
private const val OPERATION_COUNT = 256

enum class Operation(val order: Int) {
    NONE(-1),
    LEFT_PARENTHESES(1),
    RIGHT_PARENTHESES(1),
    Y_POW_X(3),
    Y_ROOT_X(3),
    MULTIPLICATION(4),
    DIVISION(4),
    ADDITION(5),
    SUBTRACTION(5),
    BITWISE_AND(6),
    BITWISE_OR(7),
    BITWISE_XOR(7),
    BITWISE_XNOR(7),
    EVALUATE(8),
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

    fun evaluate() {

        setResult(registerArray[registerIndex])
    }

    fun operation(operation: Operation) {
        if (operationIndex >= OPERATION_COUNT)
            throw IllegalStateException("Operation stack overflow")

        if (registerIndex >= REGISTER_COUNT)
            throw IllegalStateException("Register stack overflow")

        operationArray[operationIndex] = operation
        operationIndex++

        registerIndex++
        registerArray[registerIndex] = 0.0

        evaluate()
    }

    fun swap() {
        if (registerIndex == 0) {
            val tmp = registerArray[1]
            registerArray[1] = registerArray[0]
            setResult(tmp)
        } else {
            check(registerIndex > 0 && registerIndex < REGISTER_COUNT - 1)
            val tmp = registerArray[registerIndex - 1]
            registerArray[registerIndex - 1] = registerArray[registerIndex]
            setResult(tmp)
        }
    }
}


