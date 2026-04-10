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

import com.bri1234.ti36calculator.utils.ObserverSubject
import kotlin.math.pow
import kotlin.math.round

private const val REGISTER_COUNT = 64
private const val OPERATION_COUNT = 256

/**
 * These are the supported stack operations, ordered by precedence (lower order means higher precedence).
 */
private enum class Operation(val order: Int, val caption: String) {
    NONE(-1, "none"),
    Y_POW_X(3, "^"),
    Y_ROOT_X(3, "sqrt"),
    MULTIPLICATION(4, "*"),
    DIVISION(4, "/"),
    ADDITION(5, "+"),
    SUBTRACTION(5, "-"),
    BITWISE_AND(6, "and"),
    BITWISE_OR(7, "or"),
    BITWISE_XOR(7, "xor"),
    BITWISE_XNOR(7, "xnor"),
}

private enum class StackEvaluationMode {
    PARTIAL, // evaluates the stack until the next operation with higher precedence is encountered
    PARENTHESES, // evaluates the stack until the next left parentheses is encountered
    FULL, // evaluates the entire stack to a single result
}

/**
 * Manages the computation stack for the TI-36 calculator, including registers and operations.
 * It supports basic arithmetic, exponentiation, roots, and bitwise operations.
 */
class Ti36Computation {

    val onResultChanged: ObserverSubject<Double> = ObserverSubject()

    private val registerArray: Array<Double> = Array(REGISTER_COUNT) { 0.0 }
    private var registerIndex: Int = 0
    private val operationArray: Array<Operation> = Array(OPERATION_COUNT) { Operation.NONE }
    private var operationIndex: Int = 0

    private val parenthesesArray: Array<Int> = Array(OPERATION_COUNT) { 0 }


    /** Resets all registers and operations to their initial state. */
    fun reset() {
        registerArray.fill(0.0)
        registerIndex = 0

        operationArray.fill(Operation.NONE)
        operationIndex = 0

        parenthesesArray.fill(0)
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
     * Evaluates the operation at [idx] using the values in the register array and updates the stack accordingly.
     * @param idx The index of the operation to evaluate.
     */
    private fun calculateStackAtIndex(idx : Int) {

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
    }

    /**
     * Reduces the operation stack by evaluating all applicable operations.
     * @param evaluateAll If true, collapses the entire stack to a single result.
     */
    private fun evaluateStack(mode : StackEvaluationMode) {
        var done = false

        while (!done) {
            done = true

            for (idx in 0 until operationIndex) {

                if ((operationArray[idx].order <= operationArray[idx + 1].order)
                    || ((mode == StackEvaluationMode.FULL) && (idx == operationIndex - 1))) {

                    calculateStackAtIndex(idx)
                    done = false
                    break
                }

            }
        }

        onResultChanged(registerArray[registerIndex])
    }

    /**
     * Advances the register index so the next input writes to a new slot.
     */
    fun enterNewNumber() {
        require(registerIndex < REGISTER_COUNT - 1)

        if (parenthesesArray[operationIndex] > 0)
            return

        registerIndex = operationIndex
    }

    /**
     * Pushes [operation] onto the stack and triggers a partial evaluation.
     * @param operation The operation to enqueue.
     */
    private fun operation(operation: Operation) {
        require(operationIndex < OPERATION_COUNT - 1)
        require(registerIndex < REGISTER_COUNT - 1)

        operationArray[operationIndex] = operation
        operationIndex++

        evaluateStack(StackEvaluationMode.PARTIAL)
    }

    /**
     * Collapses the entire stack to a single result; repeats the last operation if the stack is empty.
     */
    fun evaluateAll() {
        // repeat operation with the last two operands?
        if ((operationIndex == 0) && (registerIndex == 0)) {
            if (operationArray[0] != Operation.NONE) {
                operationIndex++
                registerIndex++
            }
        }

        evaluateStack(StackEvaluationMode.FULL)
        check(operationIndex == 0 && registerIndex == 0)
    }

    /**
     * Swaps the top two register values and notifies observers.
     */
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

    /**
     * Prints the current state of the computation stack for debugging purposes.
     */
    fun printInfo() {
        var idx = 0

        println("~~~ Computation stack info ~~~")
        println("register index: $registerIndex, operation index: $operationIndex")
        println("Idx  ()    Op  Value")

        while (idx <= registerIndex || idx <= operationIndex) {
            val reg = registerArray[idx]
            val op = operationArray[idx].name

            val str = "%02d:  %02d  %4s  %g".format(idx,
                parenthesesArray[idx], operationArray[idx].caption, registerArray[idx])

            println(str)
            idx++
        }
    }

    fun leftParentheses() {
        require(operationIndex < OPERATION_COUNT - 1)
        require(registerIndex < REGISTER_COUNT - 1)

        if ((parenthesesArray[operationIndex] == 0) && (operationIndex > 0))
            registerIndex++

        parenthesesArray[operationIndex]++

        onResultChanged(registerArray[registerIndex])
    }

    fun rightParentheses() {
        evaluateStack(StackEvaluationMode.PARENTHESES)
    }

    fun addition() = operation(Operation.ADDITION)
    fun subtraction() = operation(Operation.SUBTRACTION)
    fun multiplication() = operation(Operation.MULTIPLICATION)
    fun division() = operation(Operation.DIVISION)
    fun yPowerX() = operation(Operation.Y_POW_X)
    fun yRootX() = operation(Operation.Y_ROOT_X)
    fun bitwiseAnd() = operation(Operation.BITWISE_AND)
    fun bitwiseOr() = operation(Operation.BITWISE_OR)
    fun bitwiseXor() = operation(Operation.BITWISE_XOR)
    fun bitwiseXnor() = operation(Operation.BITWISE_XNOR)

    fun hasParentheses() : Boolean {
        for (idx in 0 .. operationIndex) {
            if (parenthesesArray[idx] > 0)
                return true
        }
        return false
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

