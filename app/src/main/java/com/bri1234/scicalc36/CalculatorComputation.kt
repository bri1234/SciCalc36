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

import com.bri1234.scicalc36.enums.CalculatorNumberMode
import com.bri1234.scicalc36.enums.Operation
import com.bri1234.scicalc36.utils.ObserverSubject
import kotlin.math.pow
import kotlin.math.round

private const val REGISTER_COUNT = 64
private const val OPERATION_COUNT = 256


private enum class StackEvaluationMode {
    PARTIAL, // evaluates the stack until the next operation with higher precedence is encountered
    PARENTHESES, // evaluates the stack until the next left parentheses is encountered
    FULL, // evaluates the entire stack to a single result
}

/**
 * Manages the computation stack for the TI-36 calculator, including registers and operations.
 * It supports basic arithmetic, exponentiation, roots, and bitwise operations.
 */
class CalculatorComputation(
    private val state: CalculatorState,
) {

    val onResultChanged: ObserverSubject<CalculatorValue> = ObserverSubject()

    private val registerArray: Array<CalculatorValue> = Array(REGISTER_COUNT) { CalculatorValue() }
    private var registerIndex: Int = 0
    private val operationArray: Array<Operation> = Array(OPERATION_COUNT) { Operation.NONE }
    private var operationIndex: Int = 0

    private val parenthesesArray: Array<Int> = Array(OPERATION_COUNT) { 0 }


    /** Resets all registers and operations to their initial state. */
    fun reset() {
        for (register in registerArray)
            register.clear()

        registerIndex = 0

        operationArray.fill(Operation.NONE)
        operationIndex = 0

        parenthesesArray.fill(0)
    }

    /**
     * Returns the last operation on the stack, or NONE if the stack is empty.
      * @return The last enqueued operation, or NONE if no operations are on the stack.
     */
    fun getLastOperation() : Operation {
        return if (operationIndex > 0) operationArray[operationIndex - 1] else Operation.NONE
    }

    /**
     * Returns the current top-of-stack register value.
     * @return The value stored in the current register slot.
     */
    fun getValue() : CalculatorValue {
        if (registerIndex < operationIndex) {
            check(operationIndex >= 1 && registerIndex == operationIndex - 1)

            registerIndex = operationIndex
            registerArray[registerIndex].copy(registerArray[registerIndex - 1])
        }

        return registerArray[registerIndex]
    }

    /**
     * Returns the current top-of-stack register value.
     * @return The value stored in the current register slot.
     */
    fun getDoubleValue() : Double {
        return getValue().getDouble()
    }

    /**
     * Returns the previous register value, which is the value stored in the register slot before the current one.
     * If the current register index is 0, it returns the value in register slot 0, as there is no previous slot.
      * @return The value stored in the previous register slot, or the current slot if at the bottom of the stack.
     */
    fun getPreviousValue() : Double {
        return if (registerIndex == 0) registerArray[0].getDouble() else registerArray[registerIndex - 1].getDouble()
    }

    private fun convertToIntValueIfHexOctBinMode(value: CalculatorValue) {
        when (state.calculatorNumberMode) {
            CalculatorNumberMode.HEXADECIMAL,
            CalculatorNumberMode.OCTAL,
            CalculatorNumberMode.BINARY -> value.convertToInt()
            else -> { }
        }
    }

    /**
     * Sets the current top-of-stack register to [newValue].
     * @param newValue The value to store in the current register.
     */
    fun setValue(newValue: CalculatorValue, updateDisplay: Boolean = true) {
        val newVal = newValue.clone()
        convertToIntValueIfHexOctBinMode(newVal)

        registerArray[registerIndex] = newVal

        if (updateDisplay)
            onResultChanged(newVal)
    }

    /**
     * Sets the current top-of-stack register to [newValue].
     * @param newValue The value to store in the current register.
     */
    fun setDoubleValue(newValue: Double, updateDisplay: Boolean = true) {
        setValue(CalculatorValue(newValue), updateDisplay)
    }

    /**
     * Returns the top two register values as a pair, with the current register as the first element
     * and the previous register as the second.
     */
    fun getTwoValues(): Pair<CalculatorValue, CalculatorValue> {

        return if (registerIndex == 0) {
            Pair(registerArray[0], registerArray[1])
        } else {
            Pair(registerArray[registerIndex], registerArray[registerIndex - 1])
        }
    }

    /**
     * Returns the top two register values as a pair, with the current register as the first element
     * and the previous register as the second.
     */
    fun getTwoDoubleValues(): Pair<Double, Double> {
        val (a, b) = getTwoValues()
        return Pair(a.getDouble(), b.getDouble())
    }

    /**
     * Sets the top two register values to [first] and [second], with [first] being the current register
     * and [second] being the previous register.
     */
    fun setTwoValues(first: CalculatorValue, second: CalculatorValue, updateDisplay: Boolean = true) {
        val firstVal = first.clone()
        val secondVal = second.clone()

        convertToIntValueIfHexOctBinMode(firstVal)
        convertToIntValueIfHexOctBinMode(secondVal)

        if (registerIndex == 0) {
            registerArray[0] = firstVal
            registerArray[1] = secondVal
        } else {
            registerArray[registerIndex] = firstVal
            registerArray[registerIndex - 1] = secondVal
        }

        if (updateDisplay)
            onResultChanged(firstVal)
    }

    /**
     * Sets the top two register values to [first] and [second], with [first] being the current register
     * and [second] being the previous register.
     */
    fun setTwoValues(first: Double, second: Double, updateDisplay: Boolean = true) {
        setTwoValues(CalculatorValue(first), CalculatorValue(second), updateDisplay)
    }

    /**
     * Returns [base] raised to the power of [exponent].
     * @param base The base value.
     * @param exponent The exponent value.
     * @return base ^ exponent.
     * @throws IllegalArgumentException if the result is NaN or infinite.
     */
    private fun yPowerX(result: CalculatorValue, base: CalculatorValue, exponent: CalculatorValue) {
        val res = base.getDouble().pow(exponent.getDouble())
        if (res.isNaN() || res.isInfinite())
            throw IllegalArgumentException("Invalid result for y ^ x: $res")

        result.setDouble(res)
    }

    /**
     * Returns the [exponent]-th root of [base].
     * @param base The radicand.
     * @param exponent The degree of the root.
     * @return The [exponent]-th root of [base].
     * @throws IllegalArgumentException if [exponent] is 0 or the result is invalid.
     */
    private fun yRootX(result: CalculatorValue, base: CalculatorValue, exponent: CalculatorValue) {
        val e = exponent.getDouble()
        if (e == 0.0)
            throw IllegalArgumentException("Cannot take root with exponent 0")

        val res = base.getDouble().pow(1.0 / e)
        if (res.isNaN() || res.isInfinite())
            throw IllegalArgumentException("Invalid result for y root x: $res")

        result.setDouble(res)
    }

    /**
     * Divides [left] by [right].
     * @param left The dividend.
     * @param right The divisor.
     * @return The quotient left / right.
     * @throws IllegalArgumentException if [right] is 0.
     */
    private fun divide(result: CalculatorValue, left: CalculatorValue, right: CalculatorValue) {

        result.copy(left)
        result.divide(right)

        when (state.calculatorNumberMode) {
            CalculatorNumberMode.DECIMAL -> { }
            CalculatorNumberMode.HEXADECIMAL,
            CalculatorNumberMode.OCTAL,
            CalculatorNumberMode.BINARY -> result.setDouble(result.getDouble().toLong().toDouble())
        }
    }

    /**
     * Applies [operation] to [left] and [right] and returns the result.
     * @param operation The operation to perform.
     * @param left The left operand.
     * @param right The right operand.
     * @return The computed result.
     * @throws IllegalArgumentException for unsupported operations.
     */
    private fun calculate(operation: Operation, left: CalculatorValue, right: CalculatorValue): CalculatorValue {
        val result = left.clone()

        when (operation) {
            Operation.ADDITION -> result.add(right)
            Operation.SUBTRACTION -> result.subtract(right)
            Operation.MULTIPLICATION -> result.multiply(right)
            Operation.DIVISION -> divide(result, left, right)
            Operation.Y_POW_X -> yPowerX(result, left, right)
            Operation.Y_ROOT_X -> yRootX(result, left, right)
            else -> {
                val l = round(left.getDouble()).toLong()
                val r = round(right.getDouble()).toLong()

                val res = when (operation) {
                    Operation.BITWISE_AND -> l and r
                    Operation.BITWISE_OR -> l or r
                    Operation.BITWISE_XOR -> l xor r
                    Operation.BITWISE_XNOR -> (l xor r).inv()

                    else -> throw IllegalArgumentException("Unsupported operation: $operation")
                }

                result.setDouble(res.toDouble())
            }
        }

        return result
    }

    /**
     * Evaluates the operation at [idx] using the values in the register array and updates the stack accordingly.
     * @param idx The index of the operation to evaluate.
     */
    private fun calculateStackAtIndex(idx : Int): CalculatorValue {

        val leftValue = registerArray[idx].clone()
        val rightValue = registerArray[idx + 1].clone()

        val result = calculate(operationArray[idx], leftValue, rightValue)

        if (operationIndex > 1) { // to keep the last operation in memory for repeated evaluation
            removeElementAt(operationArray, idx) { Operation.NONE }
            removeElementAt(registerArray, idx) { CalculatorValue() }
        }

        registerArray[idx].copy(result)

        operationIndex--
        registerIndex--
        check(operationIndex >= 0 && registerIndex >= 0)

        return leftValue
    }

    /**
     * Reduces the operation stack by evaluating all applicable operations.
     * @param mode The evaluation mode determining how far to evaluate the stack.
       - PARTIAL: Evaluates until the next operation with higher precedence is encountered.
       - PARENTHESES: Evaluates until the next left parentheses is encountered.
       - FULL: Evaluates the entire stack to a single result.
     */
    private fun evaluateStack(mode : StackEvaluationMode): CalculatorValue? {

        var forceEvaluation = (mode == StackEvaluationMode.PARENTHESES) || (mode == StackEvaluationMode.FULL)
        var lastLeftOperand: CalculatorValue? = null

        var done = false
        while (!done && (operationIndex > 0)) {
            done = true

            // find the last parentheses
            var startIdx = operationIndex - 1
            while (startIdx > 0 && parenthesesArray[startIdx] == 0)
                startIdx--

            // evaluate part of the stack after last parentheses
            for (idx in startIdx ..< operationIndex) {

                if ((operationArray[idx].order <= operationArray[idx + 1].order)
                    || (forceEvaluation && (idx == operationIndex - 1))) {

                    lastLeftOperand = calculateStackAtIndex(idx).clone()

                    done = false
                    break
                }

            }

            // if we evaluated the part after the last parentheses, we can remove the parentheses
            if (forceEvaluation && (parenthesesArray[startIdx] > 0))
                parenthesesArray[startIdx]--

            // closing parentheses only forces evaluation of the part after the last parentheses
            if (mode != StackEvaluationMode.FULL)
                forceEvaluation = false

        }

        onResultChanged(registerArray[registerIndex])
        return lastLeftOperand
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

        if (operationIndex > registerIndex)
            return

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
     * Prints the current state of the computation stack for debugging purposes.
     */
    fun printInfo() {
        var idx = 0

        println("~~~ Computation stack info ~~~")
        println("register index: $registerIndex, operation index: $operationIndex")
        println("Idx  ()    Op  Value")

        while (idx <= registerIndex || idx <= operationIndex) {
            val str = "%02d:  %02d  %4s  %g".format(idx,
                parenthesesArray[idx], operationArray[idx].caption, registerArray[idx].getDouble())

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
        if (parenthesesArray[operationIndex] > 0) {
            parenthesesArray[operationIndex]--
            clearRepeatOperationIfNoPendingExpression()
            onResultChanged(registerArray[registerIndex])
            return
        }

        val leftOperand = evaluateStack(StackEvaluationMode.PARENTHESES)
        clearRepeatOperationIfNoPendingExpression(leftOperand)
    }

    private fun clearRepeatOperationIfNoPendingExpression(leftOperand: CalculatorValue? = null) {
        if ((operationIndex == 0) && !hasParentheses()) {
            operationArray[0] = Operation.NONE
            if (leftOperand != null)
                registerArray[1].copy(leftOperand)
        }
    }

    /** Clears a stored repeat operation without modifying a pending expression. */
    fun clearRepeatOperation() {
        clearRepeatOperationIfNoPendingExpression()
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
 * @param defaultValue Factory that creates the value written to the last slot after shifting.
 */
private fun <T> removeElementAt(array: Array<T>, index: Int, defaultValue: () -> T) {
    for (idx in index until array.size - 1)
        array[idx] = array[idx + 1]

    array[array.size - 1] = defaultValue()
}
