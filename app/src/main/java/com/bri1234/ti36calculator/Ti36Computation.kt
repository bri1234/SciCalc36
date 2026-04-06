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

    /** Resets all registers and operations to their initial state. */
    fun reset() {
        registerArray.fill(0.0)
        registerIndex = 0
        operationArray.fill(Operation.NONE)
        operationIndex = 0
    }

    /**
     * Returns the current top-of-stack register value.
     * @return The value stored in the current register slot.
     */
    fun getValue() : Double {
        return registerArray[registerIndex]
    }

    /**
     * Sets the current top-of-stack register to [newValue].
     * @param newValue The value to store in the current register.
     */
    fun setValue(newValue: Double) {
        registerArray[registerIndex] = newValue
    }

    /**
     * Sets the current register to [newValue] and notifies observers.
     * @param newValue The new result value.
     */
    fun setResult(newValue: Double) {
        setValue(newValue)
        onResultChanged(newValue)
    }

    /**
     * Returns [base] raised to the power of [exponent].
     * @param base The base value.
     * @param exponent The exponent value.
     * @return base ^ exponent.
     * @throws IllegalArgumentException if the result is NaN or infinite.
     */
    private fun yPowerX(base: Double, exponent: Double): Double {
        val result = base.pow(exponent)
        if (result.isNaN() || result.isInfinite())
            throw IllegalArgumentException("Invalid result for y ^ x: $result")

        return result
    }

    /**
     * Returns the [exponent]-th root of [base].
     * @param base The radicand.
     * @param exponent The degree of the root.
     * @return The [exponent]-th root of [base].
     * @throws IllegalArgumentException if [exponent] is 0 or the result is invalid.
     */
    private fun yRootX(base: Double, exponent: Double): Double {
        if (exponent == 0.0)
            throw IllegalArgumentException("Cannot take root with exponent 0")

        val result = base.pow(1.0 / exponent)
        if (result.isNaN() || result.isInfinite())
            throw IllegalArgumentException("Invalid result for y root x: $result")

        return result
    }

    /**
     * Divides [left] by [right].
     * @param left The dividend.
     * @param right The divisor.
     * @return The quotient left / right.
     * @throws IllegalArgumentException if [right] is 0.
     */
    private fun divide(left: Double, right: Double): Double {
        if (right == 0.0)
            throw IllegalArgumentException("Division by zero")

        return left / right
    }

    /**
     * Applies [operation] to [left] and [right] and returns the result.
     * @param operation The operation to perform.
     * @param left The left operand.
     * @param right The right operand.
     * @return The computed result.
     * @throws IllegalArgumentException for unsupported operations.
     */
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

    /**
     * Reduces the operation stack by evaluating all applicable operations.
     * @param evaluateAll If true, collapses the entire stack to a single result.
     */
    private fun evaluateStack(evaluateAll : Boolean = false) {
        var done = false

        while (!done) {
            done = true

            for (idx in 0 until operationIndex) {
                if ((operationArray[idx].order <= operationArray[idx + 1].order)
                    || (evaluateAll && (idx == operationIndex - 1))) {

                    val leftValue = registerArray[idx]
                    val rightValue = registerArray[idx + 1]
                    val result = calculate(operationArray[idx], leftValue, rightValue)

                    if (operationIndex > 1) { // to keep the last operation in memory for repeated evaluation
                        removeElementAt(operationArray, idx, Operation.NONE)
                        removeElementAt(registerArray, idx, 0.0)
                    }

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

    /** Advances the register index so the next input writes to a new slot. */
    fun enterNewNumber() {
        if (registerIndex >= REGISTER_COUNT - 1)
            throw IllegalStateException("Register stack overflow")

        registerIndex = operationIndex
    }

    /**
     * Pushes [operation] onto the stack and triggers a partial evaluation.
     * @param operation The operation to enqueue.
     */
    fun operation(operation: Operation) {
        if (operationIndex >= OPERATION_COUNT - 1)
            throw IllegalStateException("Operation stack overflow")

        if (registerIndex >= REGISTER_COUNT - 1)
            throw IllegalStateException("Register stack overflow")

        operationArray[operationIndex] = operation
        operationIndex++

        evaluateStack()
    }

    /** Collapses the entire stack to a single result; repeats the last operation if the stack is empty. */
    fun evaluateAll() {
        if ((operationIndex == 0) && (registerIndex == 0)) {
            // repeat operation with the last two operands
            if (operationArray[0] != Operation.NONE) {
                operationIndex++
                registerIndex++
            }
        }

        evaluateStack(true)
        check(operationIndex == 0 && registerIndex == 0)
    }

    /** Swaps the top two register values and notifies observers. */
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

/**
 * Removes the element at [index] from [array], shifting remaining elements left.
 * @param array The array to modify.
 * @param index The index of the element to remove.
 * @param defaultValue Value written to the last slot after shifting.
 */
private fun <T> removeElementAt(array: Array<T>, index: Int, defaultValue: T) {
    for (idx in index until array.size - 1)
        array[idx] = array[idx + 1]

    array[array.size - 1] = defaultValue
}

