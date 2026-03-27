package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.utils.ObserverSubject
import kotlin.math.pow
import kotlin.math.round

private const val REGISTER_COUNT = 64
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

    private fun yPowerX(base: Double, exponent: Double): Double {
        val result = base.pow(exponent)
        if (result.isNaN() || result.isInfinite())
            throw IllegalArgumentException("Invalid result for y ^ x: $result")

        return result
    }

    private fun yRootX(base: Double, exponent: Double): Double {
        if (exponent == 0.0)
            throw IllegalArgumentException("Cannot take root with exponent 0")

        val result = base.pow(1.0 / exponent)
        if (result.isNaN() || result.isInfinite())
            throw IllegalArgumentException("Invalid result for y root x: $result")

        return result
    }

    private fun divide(left: Double, right: Double): Double {
        if (right == 0.0)
            throw IllegalArgumentException("Division by zero")

        return left / right
    }

    private fun calculate(operation: Operation, left: Double, right: Double): Double {
        return when (operation) {
            Operation.ADDITION -> left + right
            Operation.SUBTRACTION -> left - right
            Operation.MULTIPLICATION -> left * right
            Operation.DIVISION -> divide(left, right)
            Operation.Y_POW_X -> yPowerX(left, right)
            Operation.Y_ROOT_X -> yRootX(left, right)
            Operation.BITWISE_AND -> (round(left).toLong() and round(right).toLong()).toDouble()
            Operation.BITWISE_OR -> (round(left).toLong() or round(right).toLong()).toDouble()
            Operation.BITWISE_XOR -> (round(left).toLong() xor round(right).toLong()).toDouble()
            Operation.BITWISE_XNOR -> (round(left).toLong() xor round(right).toLong()).inv().toDouble()
            else -> throw IllegalArgumentException("Unsupported operation: $operation")
        }
    }

    private fun evaluateStack(evaluateAll : Boolean = false) {
        var done = false

        while (!done) {
            done = true

            for (idx in 0 until operationIndex) {
                if ((operationArray[idx].order <= operationArray[idx + 1].order)
                    || (evaluateAll && (operationArray[idx + 1] == Operation.NONE))) {

                    val result = calculate(operationArray[idx], registerArray[idx], registerArray[idx + 1])
                    removeElementAt(operationArray, idx, Operation.NONE)
                    removeElementAt(registerArray, idx, 0.0)
                    registerArray[idx] = result

                    operationIndex--
                    registerIndex--
                    check(operationIndex >= 0 && registerIndex >= 0)

                    done = false
                    break
                }
            }
        }

        onResultChanged(registerArray[registerIndex])
    }

    fun enterNewNumber() {
        if (registerIndex >= REGISTER_COUNT - 1)
            throw IllegalStateException("Register stack overflow")

        registerIndex = operationIndex
    }

    fun operation(operation: Operation) {
        if (operationIndex >= OPERATION_COUNT - 1)
            throw IllegalStateException("Operation stack overflow")

        if (registerIndex >= REGISTER_COUNT - 1)
            throw IllegalStateException("Register stack overflow")

        operationArray[operationIndex] = operation
        operationIndex++

        evaluateStack()
    }

    fun evaluateAll() {
        evaluateStack(true)
        check(operationIndex == 0 && registerIndex == 0)
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

private fun <T> removeElementAt(array: Array<T>, index: Int, defaultValue: T) {
    for (idx in index until array.size - 1)
        array[idx] = array[idx + 1]

    array[array.size - 1] = defaultValue
}

