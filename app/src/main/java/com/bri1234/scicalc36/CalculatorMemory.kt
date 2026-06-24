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

import com.bri1234.scicalc36.enums.MemoryOperation
import com.bri1234.scicalc36.utils.ObserverSubject

/**
 * Manages the calculator's ten independent memory cells.
 *
 * Memory operations exchange values with the current register of [computation]. After every
 * operation and reset, observers are informed whether at least one memory cell is non-zero.
 *
 * @property computation The computation stack used as the source or destination of memory values.
 */
class CalculatorMemory(val computation: CalculatorComputation) {

    /**
     * Notifies observers after memory content may have changed.
     *
     * The emitted value is `true` when at least one memory cell contains a non-zero value.
     */
    val onContentChanged: ObserverSubject<Boolean> = ObserverSubject()

    private val memoryArray = Array(10) { CalculatorValue() }

    /**
     * Performs [operation] on the selected [memoryCell].
     *
     * Supported operations store the current value, recall a stored value, exchange both values,
     * or add the current value to the stored value. [MemoryOperation.NONE] leaves the values
     * unchanged. Observers are notified after the operation.
     *
     * @param operation The memory operation to execute.
     * @param memoryCell The memory cell index in the range `0..9`.
     * @throws IllegalArgumentException If [memoryCell] is outside `0..9`.
     */
    fun performOperation(operation: MemoryOperation, memoryCell : Int) {
        require(memoryCell in 0..9)

        when (operation) {
            MemoryOperation.STORE -> memoryArray[memoryCell] = computation.getValue().clone()
            MemoryOperation.RECALL -> computation.setValue(memoryArray[memoryCell])
            MemoryOperation.EXCHANGE -> {
                val tmp = memoryArray[memoryCell]
                memoryArray[memoryCell] = computation.getValue().clone()
                computation.setValue(tmp)
            }
            MemoryOperation.SUM -> memoryArray[memoryCell].add(computation.getValue())
            MemoryOperation.NONE -> {}
        }

        onContentChanged(hasNonZeroMemory())
    }

    /**
     * Returns whether any memory cell contains a non-zero value.
     *
     * @return `true` if at least one cell is non-zero, otherwise `false`.
     */
    fun hasNonZeroMemory(): Boolean {
        return memoryArray.any { !it.isZero }
    }

    /** Clears all memory cells to zero and notifies observers of the empty memory state. */
    fun reset() {
        for (mem in memoryArray) {
            mem.clear()
        }

        onContentChanged(hasNonZeroMemory())
    }
}
