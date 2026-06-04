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

import com.bri1234.ti36calculator.enums.MemoryOperation
import com.bri1234.ti36calculator.utils.ObserverSubject

class CalculatorMemory(val computation: CalculatorComputation) {

    val onContentChanged: ObserverSubject<Boolean> = ObserverSubject()

    private val memoryArray = DoubleArray(10)

    fun performOperation(operation: MemoryOperation, memoryCell : Int) {
        require(memoryCell in 0..9)

        when (operation) {
            MemoryOperation.STORE -> memoryArray[memoryCell] = computation.getValue()
            MemoryOperation.RECALL -> computation.setValue(memoryArray[memoryCell])
            MemoryOperation.EXCHANGE -> {
                val tmp = memoryArray[memoryCell]
                memoryArray[memoryCell] = computation.getValue()
                computation.setValue(tmp)
            }
            MemoryOperation.SUM -> memoryArray[memoryCell] += computation.getValue()
            MemoryOperation.NONE -> {}
        }

        onContentChanged(hasNonZeroMemory())
    }

    fun hasNonZeroMemory(): Boolean {
        return memoryArray.any { it != 0.0 }
    }

    fun reset() {
        memoryArray.fill(0.0)
        onContentChanged(hasNonZeroMemory())
    }
}
